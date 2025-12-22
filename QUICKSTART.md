# Fragile Focus - 快速上手指南 🚀

## 📦 安装前准备

### 1. 安装 Android Studio

1. 下载 [Android Studio Hedgehog](https://developer.android.com/studio) 或更高版本
2. 安装时选择标准安装（Standard Installation）
3. 等待 SDK 和工具下载完成

### 2. 配置 Android SDK

在 Android Studio 中：

1. 打开 `File -> Settings -> Appearance & Behavior -> System Settings -> Android SDK`
2. 确保安装了以下组件：
   - ✅ Android SDK Platform 34
   - ✅ Android SDK Build-Tools 34.0.0
   - ✅ Android SDK Platform-Tools
   - ✅ Android Emulator

## 🔧 项目导入

### 方法一：从 Android Studio 打开

1. 启动 Android Studio
2. 选择 `File -> Open`
3. 导航到 `d:\新建文件夹\桌面\Fenirer\FragileFocus`
4. 点击 `OK`

### 方法二：命令行导入

```bash
cd "d:\新建文件夹\桌面\Fenirer\FragileFocus"
# Android Studio会自动识别并打开项目
```

## ⏳ Gradle 同步

项目打开后，Android Studio 会自动开始 Gradle 同步：

1. 底部会显示 "Gradle sync in progress..."
2. **等待 5-10 分钟**（首次同步需要下载依赖）
3. 如果遇到错误，点击 `File -> Sync Project with Gradle Files`

### 常见 Gradle 问题

**问题 1: 无法下载依赖**

```bash
# 解决方案：切换到国内镜像源
# 在 build.gradle.kts 中添加:
repositories {
    google()
    mavenCentral()
    maven { url = uri("https://maven.aliyun.com/repository/public") }
}
```

**问题 2: Kotlin 版本不匹配**

```
确保项目使用 Kotlin 1.9.20，检查 build.gradle.kts 中的版本号
```

## 📱 运行应用

### 使用 Android 模拟器（推荐）

1. **创建虚拟设备**

   - 点击工具栏的 `Device Manager`
   - 点击 `Create Device`
   - 选择 `Pixel 5` 或其他设备
   - 系统镜像选择 `Android 13 (API 33)` 或更高
   - 点击 `Finish`

2. **启动模拟器**

   - 在设备列表中点击 ▶️ 启动按钮
   - 等待模拟器启动完成（约 1-2 分钟）

3. **运行 App**
   - 确保顶部设备选择框显示你的模拟器
   - 点击绿色 ▶️ Run 按钮
   - 等待编译和安装（首次约 3-5 分钟）

### 使用真机（最佳体验）

1. **开启开发者选项**

   - 进入手机 `设置 -> 关于手机`
   - 连续点击 "版本号" 7 次
   - 返回设置，找到 `开发者选项`

2. **开启 USB 调试**

   - 在开发者选项中打开 `USB调试`
   - 连接手机到电脑
   - 手机上会弹出授权提示，点击 `允许`

3. **运行 App**
   - Android Studio 会自动识别设备
   - 点击绿色 ▶️ Run 按钮

## 🔑 授权 UsageStats 权限

首次运行 App 时会进入权限引导页面：

1. 点击 **"前往设置"** 按钮
2. 系统会跳转到 `设置 -> 安全 -> 有权查看使用情况的应用`
3. 在列表中找到 **Fragile Focus**
4. 打开开关 ✅
5. 返回 App（按返回键或切换任务）

> ⚠️ **重要**: 没有这个权限，监控功能将无法工作！

## 🎮 测试功能

### 1. 测试状态切换（调试按钮）

在主界面底部有 4 个调试按钮：

- 点击 **"健康"** → 宠物变成 😊
- 点击 **"中毒"** → 宠物变成 🤢（震动动画）
- 点击 **"濒死"** → 宠物变成 💀（闪烁动画）
- 点击 **"死亡"** → 宠物变成 ⚰️（倒下动画）

### 2. 测试真实监控

1. 确保 App 在后台运行（按 Home 键）
2. 打开"抖音"或"B 站"App（如果已安装）
3. 使用 10 秒后，切换回 Fragile Focus
4. 观察宠物是否进入中毒状态 🤢

### 3. 测试后台保活

1. 下拉通知栏
2. 检查是否有 **"守护中"** 的前台通知
3. 如果有，说明后台服务正常运行 ✅

## 🐛 常见问题

### Q1: 宠物状态不变化？

**A**:

1. 检查是否授予了 UsageStats 权限
2. 重启 App 试试
3. 查看 Logcat 日志（Android Studio 底部）

### Q2: 编译失败？

**A**:

1. 清理项目: `Build -> Clean Project`
2. 重新构建: `Build -> Rebuild Project`
3. 检查 Gradle 版本和网络连接

### Q3: 模拟器无法识别 App 切换？

**A**:

- UsageStatsManager 在模拟器上可能有限制
- 建议使用真机测试监控功能
- 或者只测试 UI 动画（使用调试按钮）

### Q4: Supabase 同步不工作？

**A**:

- MVP 版本暂未实现云端同步功能
- 该功能计划在后续版本中添加

## 📝 下一步

- 阅读 [README.md](README.md) 了解项目架构
- 查看 [实施计划](../../../Users/马鑫悦/.gemini/antigravity/brain/e4092f64-bb82-45cd-8a32-2e0ef95a0c85/implementation_plan.md) 了解技术细节
- 开始修改代码，添加自己的功能！

---

**需要帮助？** 欢迎提 Issue 或查看代码注释！💪
