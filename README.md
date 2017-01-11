# 点播云安卓SDK 。
Android SDK 只包含了最终用户使用场景中的必要功能。相比服务端 SDK 而言，客户端 SDK 不会包含对云存储服务的管理和配置功能。**千万注意，portal中的access key 和 secret key不要存放在客户端！** 更多安全机制的问题，参考七牛developer文档 [安全机制](http://developer.qiniu.com/article/developer/security/index.html)。

## 安装
### 运行环境

| Qiniu SDK 版本 | 最低 Android版本   |       依赖库版本           |
|------------ |-----------------|------------------------|
|  7.2.x        |  Android 2.3+     |        okhttp 3+         |
|  7.1.x        |  Android 2.3+     |        okhttp 2.6+       |
| 7.0.8,7.0.9   |  Android 2.2+     | android-async-http 1.4.9 |
|  7.0.7        |  Android 2.2+     | android-async-http 1.4.8 |

### 直接安装
将存储sdk的jar文件复制到项目中去
[下载地址](http://search.maven.org/remotecontent?filepath=com/qiniu/qiniu-android-sdk/) 

如果需要 happy-dns
[下载地址](https://repo1.maven.org/maven2/com/qiniu/happy-dns/)

### 通过gradle
* 7.3.x
```
compile 'com.qiniu:qiniu-android-sdk:7.3.+'
```
* 7.2.x
```
compile 'com.qiniu:qiniu-android-sdk:7.2.+'
```
* 7.1.x
```
compile 'com.qiniu:qiniu-android-sdk:7.1.+'
```
* 7.0.x
```
compile 'com.qiniu:qiniu-android-sdk:7.0.+'
```
### 通过maven
直接从存储[maven仓库](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.qiniu%22%20AND%20a%3A%22qiniu-android-sdk%22)中下载。


***
### 使用
* 登陆 https://portal.qiniu.com 可以查看自己的 Access Key 和 Secret Key。 将 Access Key 和 Secret Key 放入服务端，不要放在客户端，客户端的需要鉴权的时候，通过往服务端发请求，获取已经鉴权好了的信息。

* SDK主要包含文件上传，需要用相应的方法，只需要实例化相应的类即可。

* 上传资源，调用UploadRes类来上传,private 方法不要修改和调用，直接调用相应的public方法即可。该public方法属于直传文件方法。

* 点播云的 SDK 只依赖于七牛的存储SDK（存储SDK会自动下载它依赖的第三方包，包括okhttp,gson，如果没有下载到需要的包，就单独自己下载），除了存储的jar包，不依赖于其他外部jar包。


##其他
* 如果在使用过程中，遇到bug或者不人性化的地方，欢迎指出，并在 [点播云安卓SDK](https://github.com/qiniu-jedi/jedi-android-sdk/issues) 中提出issue，七牛开发者会持续跟进该sdk，并改进。




