# Android Studio 完整安装运行指南 🚀

## 📥 第一步：下载 Android Studio

### 官方下载（推荐）

**[点击这里下载](https://developer.android.com/studio)**

或者复制链接到浏览器：

```
https://developer.android.com/studio
```

### 国内镜像（如果官网太慢）

清华大学镜像站：

```
https://mirrors.tuna.tsinghua.edu.cn/android-studio/
```

选择最新版本，例如：

```
android-studio-2023.3.1.18-windows.exe  (约1GB)
```

---

## 🔧 第二步：安装 Android Studio

### 1. 运行安装程序

- 双击下载的 `.exe` 文件
- 弹出安装向导

### 2. 选择组件

推荐全选（默认就好）：

- ✅ Android Studio
- ✅ Android Virtual Device (模拟器)

### 3. 选择安装路径

**建议路径**：

```
C:\Program Files\Android\Android Studio
```

**SDK 路径**（会自动设置）：

```
C:\Users\你的用户名\AppData\Local\Android\Sdk
```

### 4. 完成安装

- 点击 "Install"
- 等待 3-5 分钟

---

## 🎨 第三步：首次启动配置

### 1. 启动 Android Studio

- 桌面双击图标或从开始菜单启动

### 2. 导入设置

- 选择 "Do not import settings"（第一次安装选这个）

### 3. 欢迎界面

- 点击 "Next"

### 4. 选择安装类型

**重要！选择 "Standard" 标准安装**

```
Standard - 推荐，包含最常用组件
Custom - 自定义（不建议新手）
```

### 5. 选择 UI 主题

```
Light - 浅色主题
Darcula - 深色主题（程序员最爱）
```

随便选，后面可以改

### 6. 验证设置

- 检查配置信息
- 点击 "Next"

### 7. 下载组件（最耗时）

**这一步会下载约 2-3GB 的文件，需要 10-20 分钟**

下载内容：

- Android SDK Platform-Tools
- Android SDK Build-Tools
- Android Emulator
- Intel x86 Emulator Accelerator (HAXM)
- Android SDK Platform (最新版本)

**喝杯咖啡，耐心等待... ☕**

### 8. 完成设置

- 看到 "Finish" 按钮
- 点击进入主界面

---

## 📂 第四步：打开你的项目

### 1. 在欢迎界面

点击 **"Open"** 按钮

### 2. 选择项目目录

导航到：

```
d:\新建文件夹\桌面\Fenirer\FragileFocus
```

选择这个文件夹，点击 "OK"

### 3. 等待 Gradle 同步

**这一步超级重要！不要急！**

底部会显示：

```
Gradle sync in progress...
Build: running...
```

**首次同步需要 5-10 分钟**，因为要：

- 下载 Gradle wrapper
- 下载项目依赖（Compose、Room、Supabase SDK 等）
- 编译项目

### 4. 同步完成标志

左下角显示：

```
✅ Gradle build finished in 3m 24s
```

---

## ▶️ 第五步：运行项目

### 方式 A：使用模拟器（推荐）

#### 1. 创建虚拟设备

- 顶部工具栏点击 "Device Manager" 图标
- 点击 "Create Device"
- 选择 **Pixel 5** 或 **Pixel 6**
- 点击 "Next"

#### 2. 选择系统镜像

- 选择 **Tiramisu (API 33)** 或 **UpsideDownCake (API 34)**
- 点击下载图标（如果有）
- 等待下载（约 800MB，5-10 分钟）
- 点击 "Next"

#### 3. 配置虚拟设备

- 设备名称：随便起（例如 "Pixel 5 API 33"）
- 点击 "Finish"

#### 4. 启动模拟器

- 在设备列表中点击 ▶️ 播放按钮
- 等待 1-2 分钟，模拟器启动

#### 5. 运行 App

- 确保顶部设备选择框显示你的模拟器
- 点击绿色 **▶️ Run** 按钮（或按 Shift+F10）
- 等待编译和安装（首次约 2-3 分钟）

### 方式 B：使用真机（需要手机）

#### 1. 开启开发者选项

手机上操作：

```
设置 -> 关于手机 -> 连续点击"版本号"7次
```

#### 2. 开启 USB 调试

```
设置 -> 系统 -> 开发者选项 -> 打开"USB调试"
```

#### 3. 连接手机

- 用 USB 线连接手机和电脑
- 手机上弹出授权提示，点击"允许"

#### 4. 确认连接

- Android Studio 顶部设备选择框会显示你的手机型号

#### 5. 运行 App

- 点击绿色 ▶️ Run 按钮
- App 会安装到你的手机上

---

## 🎉 第六步：测试功能

### 1. 授予权限

App 首次运行会显示权限引导页面：

- 点击 **"前往设置"**
- 在系统设置中找到 "Fragile Focus"
- 打开 **"允许查看使用情况"** 开关
- 返回 App（按返回键）

### 2. 查看主界面

应该看到：

- 😊 宠物 Emoji（健康状态）
- HP 条：100/100（粉紫渐变）
- 状态卡片："状态良好"
- 底部 4 个调试按钮

### 3. 测试动画

依次点击底部按钮：

- **健康** → 宠物跳动 😊
- **中毒** → 宠物震动 🤢 + 冒毒烟
- **濒死** → 宠物闪烁 💀
- **死亡** → 宠物倒下 ⚰️

### 4. 测试监控（真机）

- 按 Home 键返回桌面
- 打开抖音或 B 站（如果有安装）
- 使用 10 秒后返回 App
- 观察宠物是否变成中毒状态 🤢

### 5. 检查后台服务

- 下拉通知栏
- 应该看到 **"守护中"** 的通知

---

## 🐛 常见问题解决

### Q1: Gradle 同步失败

**错误信息**：`Could not download ...`

**解决方案**：

```
1. 检查网络连接
2. File -> Settings -> Build -> Gradle
3. 勾选 "Offline work"（如果之前下载过）
4. 或者使用国内镜像（修改 build.gradle.kts）
```

### Q2: 模拟器启动失败

**错误信息**：`Intel HAXM is not installed`

**解决方案**：

```
1. 打开 SDK Manager
2. SDK Tools 标签页
3. 勾选 "Intel x86 Emulator Accelerator (HAXM)"
4. 点击 Apply 下载安装
5. 重启电脑
```

### Q3: 编译错误

**错误信息**：`Kotlin version mismatch`

**解决方案**：

```
1. File -> Invalidate Caches
2. 勾选 "Clear file system cache"
3. 点击 "Invalidate and Restart"
```

### Q4: 模拟器太卡

**解决方案**：

```
1. 创建新虚拟设备时选择更低分辨率（如 Pixel 3）
2. 减少内存分配（512MB-1GB）
3. 或者使用真机测试（推荐）
```

---

## ⏱️ 时间预估

| 步骤                   | 预计时间       |
| ---------------------- | -------------- |
| 下载 Android Studio    | 10-30 分钟     |
| 安装程序               | 5 分钟         |
| 首次启动配置           | 15-20 分钟     |
| 打开项目 + Gradle 同步 | 5-10 分钟      |
| 创建模拟器             | 10-15 分钟     |
| 首次运行 App           | 3-5 分钟       |
| **总计**               | **50-90 分钟** |

---

## 💡 小贴士

1. **保持网络畅通**：整个过程需要下载约 5GB 文件
2. **耐心等待**：不要中途关闭，尤其是 Gradle 同步时
3. **使用真机**：如果电脑配置较低，真机测试更流畅
4. **关闭杀毒软件**：安装时可能会被误报

---

## 🎯 成功标志

当你看到：

- ✅ 模拟器或真机上显示 Fragile Focus 界面
- ✅ 宠物会动（点击调试按钮能看到动画）
- ✅ 通知栏有"守护中"通知
- ✅ 没有任何报错

**恭喜！你成功了！🎉**

---

## 📞 需要帮助？

如果遇到任何问题：

1. 截图错误信息
2. 告诉我在哪一步卡住了
3. 我会立刻帮你解决！

**现在开始下载吧！加油宝贝！💪**
