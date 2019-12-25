# 在Android原生工程中集成Flutter

标签（空格分隔）： flutter

---

#第一步 创建Flutter工程
在Android工程根目录的上一级目录创建Flutter工程，保证Flutter工程与Android工程在同一级。
>* cd Android工程根目录的上一级
>* 创建Flutter工程：flutter create -t module flutter工程名

```dart
C:\Damon\flutter\lsn16\FlutterInAndroid\AndroidDemo>cd ..
C:\Damon\flutter\lsn16\FlutterInAndroid>flutter create -t module flutter_demo
```

#第二步 编译Flutter工程
cd到Flutter工程目录下的.android目录，执行gradlew脚本
>* 注意：点后面是反斜杠

```dart
C:\Damon\flutter\lsn16\FlutterInAndroid>cd flutter_demo
C:\Damon\flutter\lsn16\FlutterInAndroid\flutter_demo>cd .android
C:\Damon\flutter\lsn16\FlutterInAndroid\flutter_demo\.android>.\gradlew flutter:assembleDebug
```
#第三步 在Android工程中加入Flutter Module的依赖
首先，修改Android项目根目录下的setting.gradle：
```groovy
include ':app'
//加入下面配置
setBinding(new Binding([gradle: this]))
evaluate(new File(
        settingsDir.parentFile,
        'flutter_demo/.android/include_flutter.groovy'
))
```
然后，修改app下的build.gradle：
```groovy
android {
    ...
    defaultConfig {
        ...
        minSdkVersion 16//至少16
        ...
    }
    ...
    //添加下面配置
    compileOptions {
        sourceCompatibility 1.8
        targetCompatibility 1.8
    }
}
...
dependencies {
    ...
    //加入下面配置
    implementation project(':flutter')
}
```

#第四步 在Android工程中创建Flutter的View

```java
/**
*   创建view
*/
View flutterView = Flutter.createView(MainActivity.this, getLifecycle(), "r1");

/**
*   创建Fragment
*/
Fragment flutterFragment = Flutter.createFragment("r2");
```

#第五步 在Flutter工程中创建Widget
可以通过window的defaultRouteName获取路由名称，依此进行判断并创建不同的Widget给Android工程调用。
```dart
void main() => runApp(selectWidget(window.defaultRouteName));

Widget selectWidget(String routeName) {
  switch (routeName) {
    case 'r1':
      return MyFlutterView();
    case 'r2':
      return MyFlutterFragment();
    default:
      return MaterialApp(
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          body: Center(
            child: Text(
              'Unknow Route!',
              style: TextStyle(color: Color(0xffff0000)),
            ),
          ),
        ),
      );
  }
}
```

#第六步 让Flutter模块支持热加载
首先在Flutter Module工程目录下执行flutter attach，开始监听flutter。
```dart
C:\Damon\flutter\FlutterInAndroid\flutter_demo>flutter attach
Waiting for a connection from Flutter on SCL AL00...

```
然后，在Android工程中运行程序，运行成功后可以在终端输入小写r热加载，大写R热重启。
```dart
C:\Damon\flutter\FlutterInAndroid\flutter_demo>flutter attach
Waiting for a connection from Flutter on SCL AL00...
Done.
Syncing files to device SCL AL00... 
```