https://www.jianshu.com/p/776a0636dcb2

### 1、给电脑安装证书

首先在charles的 Help选项 选择 SSL Proxyings 选项 再选择 Install Charles Root Certificate 选项

![android_charles抓包https](/android/image/android_charles_https.webp)

### 2、证书信任

![android_charles抓包https](/android/image/android_charles_https2.webp)

信任后点击证书出现上图为已信任状态（钥匙串中找）

### 3、配置SSL代理

在charles的 Proxy选项选择SSL Proxy Settings

![android_charles抓包https](/android/image/android_charles_https3.webp)

点add添加需要监视的域名，支持 *号通配符，端口一般都是443:

### 4、给手机设置代理

- 这个前提是需要手机和电脑处于同一个局域网内（简单的来说就是同一个wifi下）；
- 获取当前的IP地址和端口号，Charles默认端口号为8888， 这个可以根据需要自行修改；
- 手机手动配置wifi代理，输入已经获取到的IP地址和端口号


![android_charles抓包https](/android/image/android_charles_https4.webp)

以上步骤对于Android7.0以下的手机完全适用，但是对于Android7.0以上的手机获取https接口会出现unknown ，如下图：

![android_charles抓包https](/android/image/android_charles_https5.webp)


以下会解决这个问题。

### Android7.0以上unknown解决办法

给手机安装SSL证书

![android_charles抓包https](/android/image/android_charles_https6.webp)

会出现下面，在手机浏览器中输入红色标识的地址下载SSL证书并安装

![android_charles抓包https](/android/image/android_charles_https7.webp)

但是对于有一些机型以上方式还是不好使，比如我用的vivo x20就不行，这时候就需要在代码中添加相应代码支持了。 Charles官方文档摘要：

![android_charles抓包https](/android/image/android_charles_https8.webp)

### 主要步骤

#### 1、创建network_security_config.xml

在res文件夹下创建“xml”文件夹，在“xml”文件夹下创建network_security_config.xml

![android_charles抓包https](/android/image/android_charles_https9.webp)

#### 2、文件内容

```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <debug-overrides>
        <trust-anchors>
            <!-- Trust user added CAs while debuggable only -->
            <certificates src="user" />
        </trust-anchors>
    </debug-overrides>
</network-security-config>
```

#### 3、清单文件中配置

```
<manifest ... >
    <application android:networkSecurityConfig="@xml/network_security_config">
    ...
    </application>
</manifest>
```
配置完成后就可以用啦！


