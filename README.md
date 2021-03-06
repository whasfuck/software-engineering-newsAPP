# Wink开发文档

## 1. 开发平台及环境配置

本应用是一个基于Java语言，在Android Studio上开发的移动应用。环境配置具体如下：

compileSdkVersion为27，buildToolsVersion为28.0.3，targetSdkVersion为27，minSdkVersion为16，Android Gradle Plugin Version为3.5.2，Gradle Version为5.4.1，引用外部库如下：

```java
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    androidTestImplementation('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    //noinspection GradleCompatible
    implementation 'com.android.support:appcompat-v7:27.1.1'
    testImplementation 'junit:junit:4.12'
    implementation 'com.android.support:design:27.1.1'
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.android.support:cardview-v7:27.1.1'
    implementation 'com.squareup.okhttp3:okhttp:4.2.2'
    implementation 'com.github.chrisbanes:PhotoView:2.0.0'
    implementation project(':library:irecyclerview')
    implementation project(':channelmanager')
    implementation 'com.tencent.bugly:crashreport:3.1.0'
    implementation 'com.contrarywind:Android-PickerView:4.1.9'
    debugImplementation 'com.squareup.leakcanary:leakcanary-android:1.6.1'
    releaseImplementation 'com.squareup.leakcanary:leakcanary-android-no-op:1.6.1'
    // Optional, if you use support library fragments:
    debugImplementation 'com.squareup.leakcanary:leakcanary-support-fragment:1.6.1'
}

```

## 2. 应用简介

Wink是一款可以看新闻，添加好友，发表动态的移动应用。用户可以在此查看各种新闻，并对与自己感兴趣的新闻发表评论。同时，用户还可以查看好友发表的动态，并对其追加评论。用户的动态只能由关注了自己的好友进行评论。这样，很多志趣相近的人便走到一起，并有聊不完的新鲜话题。

## 3. 应用GUI展示

<img src=".\image\channelmanagement.jpg"  /><img src=".\image\mine.jpg" alt="mine"  /><img src=".\image\news.jpg" alt="news"  /><img src=".\image\newsdetail.jpg" alt="newsdetail"  /><img src=".\image\search.jpg" alt="search"  /><img src=".\image\trend.jpg" alt="trend"  /><img src=".\image\trenddetail.jpg" alt="trenddetail"  />

## 4. 应用框架与整体架构

本次应用开发使用MVC框架，有低耦合，可维护性高，层次清晰的优点。具体应用架构分为前后端两层。前端为两层嵌套的Fragment：外层为FragmentTabHost+Fragment；内层为TabLayout+ViewPager，层次分明，逻辑严谨。引用外部库在1中已经给出。后端采用Springboot框架，将服务端与mysql数据库部署在云端。后端从外部api调取新闻存在数据库中，前端用接口访问并将之显现 。

## 5. 数据库设计

后端数据库采用MySQL，具体数据库ER图如下。
<img src='.\image\database.jpg'>

## 6. 后端开发与部署

后端项目采用Springboot框架进行快速开发。使用JDBC创建mysql数据库连接。使用controller控制http请求访问。通过全局Corsconfig配置允许跨域请求访问。
采用云端数据库进行开发。租用阿里云服务器，服务器配置为1核CPU+2G内存+40G SSD。在服务器配置MySQL环境并创建数据库。
服务器配置java运行环境。服务端在本地开发测试完成后打包上传至服务器后部署运行。

## 7. 用例实现描述

### 7.1 查看新闻/动态

用户可以查看不同种类的新闻/动态。

### 7.2 切换频道

用户可以切换或者管理频道。

### 7.3 评论新闻

用户可以评论一条新闻，这个评论将被自动转换为一条动态发表出去。

### 7.4 收藏新闻

用户可以收藏或者取消收藏一条新闻。

### 7.5 发表动态

用户的新闻评论会成为动态，同时用户也可以发表一些文字动态。

### 7.6 评论动态

用户关注了一名好友后，可以对其动态进行评论。

### 7.7 关注好友/取消关注好友

用户可以关注一名好友，之后可以查看他的动态，并对他的动态进行评论；当然，你也可以取消对他的关注。

### 7.8 拉黑粉丝

当用户发现某粉丝有不好言论，用户可以拉黑他，随之消失的还有他的那些评论。

### 7.9 登录/注册

登录（注册并登录）之后才能正常进行动态、评论、好友的功能。

### 7.10 摇一摇

摇一摇手机可以随机产生一位用户，看看你与他是否有缘吧。

### 7.11 搜索

你可以按照关键词、时间、类型（好友）搜索并查看对应的新闻（动态）。

## 8. 测试

### 8.1 Sonarqube

本项目在中期应用sonarqube进行代码检测，并酌情修改。

展示：

<img src=".\image\sonarqube.png"  />

### 8.2 Junit

本项目采用junit单元测试，针对程序模块进行测试。

### 8.3 LeakCanary

本项目引用LeakCanary进行内存泄漏监控，当有内存泄漏风险时，LeakCanary会给与提醒。

展示：

<img src=".\image\leakcanary.jpg" alt="leakcanary"  />

### 8.4 Postman

后端接口在部署前采用Postman进行测试，确保接口运行正常。

展示：
<img src=".\image\postman.jpg">

## 9. 库与分工

我们的github库为：

https://github.com/whasfuck/software-engineering-newsAPP

我们参考的git库有：

https://github.com/jaydenxiao2016/AndroidFire

https://github.com/Aspsine/IRecyclerView

分工：1752026 李航  100%

​            1754067 徐海琪  100%