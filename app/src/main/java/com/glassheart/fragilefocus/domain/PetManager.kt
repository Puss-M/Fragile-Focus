package com.glassheart.fragilefocus.domain

import com.glassheart.fragilefocus.monitoring.ToxicityCalculator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max
import kotlin.math.min

/**
 * 宠物管理器 - 核心状态机
 * 管理宠物的HP、状态转换、以及与外部事件的交互
 */
@Singleton
class PetManager @Inject constructor(
    private val toxicityCalculator: ToxicityCalculator
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    
    // 宠物属性
    private var currentHp: Float = MAX_HP
    private var birthTime: Long = System.currentTimeMillis()
    private var currentToxicApp: String? = null
    
    // 状态流
    private val _petState = MutableStateFlow<PetState>(PetState.Healthy)
    val petState: StateFlow<PetState> = _petState.asStateFlow()
    
    private val _hp = MutableStateFlow(MAX_HP)
    val hp: StateFlow<Float> = _hp.asStateFlow()
    
    // 死亡回调
    var onPetDeath: ((PetState.Dead) -> Unit)? = null
    
    init {
        startHpMonitoring()
    }
    
    /**
     * 响应用户切换App事件
     * @param packageName 当前前台App包名
     */
    fun onAppChanged(packageName: String) {
        if (_petState.value is PetState.Dead) return // 已死亡，不再响应
        
        val rule = toxicityCalculator.calculateToxicity(packageName)
        
        // 记录当前有毒App
        currentToxicApp = if (rule.toxicity >= 50) packageName else null
        
        // 立即更新状态（视觉反馈）
        updatePetState()
    }
    
    /**
     * HP监控协程 - 每秒更新HP
     */
    private fun startHpMonitoring() {
        scope.launch {
            while (isActive) {
                delay(1000) // 每秒更新
                
                if (_petState.value is PetState.Dead) continue
                
                // 计算当前HP变化
                val currentApp = currentToxicApp
                val hpChange = if (currentApp != null) {
                    val rule = toxicityCalculator.calculateToxicity(currentApp)
                    rule.hpDrainRate
                } else {
                    NATURAL_RECOVERY_RATE // 不在有毒App时自然恢复
                }
                
                // 更新HP
                currentHp = max(0f, min(MAX_HP, currentHp + hpChange))
                _hp.value = currentHp
                
                // 检查是否死亡
                if (currentHp <= 0f) {
                    handleDeath()
                } else {
                    updatePetState()
                }
            }
        }
    }
    
    /**
     * 更新宠物状态（不含死亡）
     */
    private fun updatePetState() {
        val newState = when {
            currentHp > 70f -> PetState.Healthy
            currentHp < 30f -> PetState.Critical
            currentToxicApp != null -> PetState.Poisoned(currentToxicApp!!)
            else -> PetState.Healthy
        }
        _petState.value = newState
    }
    
    /**
     * 处理宠物死亡
     */
    private fun handleDeath() {
        val lifespanSeconds = (System.currentTimeMillis() - birthTime) / 1000
        val killerApp = currentToxicApp ?: "未知原因"
        val causeOfDeath = "使用 $killerApp 过度"
        
        val deathState = PetState.Dead(
            causeOfDeath = causeOfDeath,
            killerApp = killerApp,
            lifespanSeconds = lifespanSeconds
        )
        
        _petState.value = deathState
        onPetDeath?.invoke(deathState)
    }
    
    /**
     * 复活宠物
     */
    fun revivePet() {
        currentHp = MAX_HP
        birthTime = System.currentTimeMillis()
        currentToxicApp = null
        _hp.value = MAX_HP
        _petState.value = PetState.Healthy
    }
    
    /**
     * 手动调整HP（用于调试）
     */
    fun adjustHp(delta: Float) {
        currentHp = max(0f, min(MAX_HP, currentHp + delta))
        _hp.value = currentHp
        
        if (currentHp <= 0f) {
            handleDeath()
        } else {
            updatePetState()
        }
    }
    
    companion object {
        const val MAX_HP = 100f
        const val NATURAL_RECOVERY_RATE = 0.5f // 每秒恢复0.5HP
    }
}
