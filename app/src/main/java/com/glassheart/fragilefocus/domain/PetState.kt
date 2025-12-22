package com.glassheart.fragilefocus.domain

/**
 * 宠物状态 - 封装式类
 * 代表宠物的健康状态机
 */
sealed class PetState {
    /**
     * 健康状态 - HP > 70
     * 视觉: 😊 缓慢跳动动画
     */
    data object Healthy : PetState()

    /**
     * 中毒/生病状态 - HP 30-70 且检测到黑名单App
     * 视觉: 🤢 震动动画
     */
    data class Poisoned(val toxicApp: String) : PetState()

    /**
     * 濒死状态 - HP < 30
     * 视觉: 💀 闪烁动画
     */
    data object Critical : PetState()

    /**
     * 死亡状态 - HP = 0
     * 视觉: ⚰️ 淡出动画
     */
    data class Dead(
        val causeOfDeath: String,
        val killerApp: String,
        val lifespanSeconds: Long
    ) : PetState()
}

/**
 * 获取状态对应的Emoji
 */
fun PetState.getEmoji(): String = when (this) {
    is PetState.Healthy -> "😊"
    is PetState.Poisoned -> "🤢"
    is PetState.Critical -> "💀"
    is PetState.Dead -> "⚰️"
}

/**
 * 获取状态描述文本
 */
fun PetState.getDescription(): String = when (this) {
    is PetState.Healthy -> "状态良好"
    is PetState.Poisoned -> "正在被 $toxicApp 毒害..."
    is PetState.Critical -> "生命垂危！"
    is PetState.Dead -> "死于 $killerApp"
}
