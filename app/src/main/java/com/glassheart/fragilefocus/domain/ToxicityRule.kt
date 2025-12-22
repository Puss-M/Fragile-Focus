package com.glassheart.fragilefocus.domain

/**
 * App毒性规则
 */
data class ToxicityRule(
    val packageName: String,
    val toxicity: Int,  // 0-100
    val hpDrainRate: Float,  // HP扣除速率 (HP/second)
    val category: AppCategory
)

/**
 * App分类
 */
enum class AppCategory {
    WHITE,  // 白名单 - 有益的App (回血)
    GREY,   // 灰名单 - 中性工具 (不影响)
    BLACK   // 黑名单 - 有毒App (扣血)
}

/**
 * 预定义的毒性规则库
 */
object ToxicityRules {
    val DEFAULT_RULES = listOf(
        // 黑名单 - 娱乐/刷视频类
        ToxicityRule("com.ss.android.ugc.aweme", 100, -5f, AppCategory.BLACK), // 抖音
        ToxicityRule("tv.danmaku.bili", 80, -4f, AppCategory.BLACK), // B站
        ToxicityRule("com.sina.weibo", 90, -4.5f, AppCategory.BLACK), // 微博
        ToxicityRule("com.tencent.qqlive", 75, -3.5f, AppCategory.BLACK), // 腾讯视频
        ToxicityRule("com.smile.gifmaker", 95, -4.8f, AppCategory.BLACK), // 快手
        ToxicityRule("com.zhihu.android", 60, -2.5f, AppCategory.BLACK), // 知乎 (部分有毒)
        
        // 灰名单 - 工具类
        ToxicityRule("com.tencent.mm", 10, -0.5f, AppCategory.GREY), // 微信
        ToxicityRule("com.alibaba.android.rimet", 10, -0.5f, AppCategory.GREY), // 钉钉
        ToxicityRule("com.tencent.mobileqq", 15, -0.8f, AppCategory.GREY), // QQ
        
        // 白名单 - 学习类
        ToxicityRule("com.mxbc.mfqy", 0, 2f, AppCategory.WHITE), // 墨墨背单词
        ToxicityRule("com.duokan.reader", 0, 1.5f, AppCategory.WHITE), // 微信读书
        ToxicityRule("com.baicizhan.main", 0, 2f, AppCategory.WHITE), // 百词斩
        ToxicityRule("org.mozilla.firefox", 0, 0.5f, AppCategory.WHITE), // Firefox
        
        // 系统应用
        ToxicityRule("com.android.systemui", 0, 0f, AppCategory.GREY), // 系统界面
        ToxicityRule("com.android.launcher", 0, 0f, AppCategory.GREY), // 启动器
        ToxicityRule("com.miui.home", 0, 0f, AppCategory.GREY), // 小米桌面
        ToxicityRule("com.huawei.android.launcher", 0, 0f, AppCategory.GREY) // 华为桌面
    )
    
    /**
     * 默认规则 - 未知App当作低毒性灰名单
     */
    val UNKNOWN_APP_RULE = ToxicityRule("*", 5, -0.2f, AppCategory.GREY)
}
