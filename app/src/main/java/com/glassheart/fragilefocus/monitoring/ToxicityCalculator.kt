package com.glassheart.fragilefocus.monitoring

import com.glassheart.fragilefocus.domain.ToxicityRule
import com.glassheart.fragilefocus.domain.ToxicityRules
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 毒性计算引擎
 * 根据App包名计算其对宠物的毒性影响
 */
@Singleton
class ToxicityCalculator @Inject constructor() {
    
    private val rulesMap: Map<String, ToxicityRule> = 
        ToxicityRules.DEFAULT_RULES.associateBy { it.packageName }
    
    /**
     * 计算指定App的毒性规则
     * @param packageName App包名
     * @return 对应的毒性规则
     */
    fun calculateToxicity(packageName: String): ToxicityRule {
        return rulesMap[packageName] ?: ToxicityRules.UNKNOWN_APP_RULE
    }
    
    /**
     * 判断App是否为黑名单
     */
    fun isBlackListed(packageName: String): Boolean {
        val rule = calculateToxicity(packageName)
        return rule.toxicity >= 50
    }
    
    /**
     * 判断App是否为白名单
     */
    fun isWhiteListed(packageName: String): Boolean {
        val rule = calculateToxicity(packageName)
        return rule.hpDrainRate > 0 // 正值代表回血
    }
}
