使用http库


官方使用案例：

```
import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

Future<Post> fetchPost() async {
  final response =
      await http.get('https://jsonplaceholder.typicode.com/posts/1');
  final responseJson = json.decode(response.body);

  return new Post.fromJson(responseJson);
}

class Post {
  final int userId;
  final int id;
  final String title;
  final String body;

  Post({this.userId, this.id, this.title, this.body});

  factory Post.fromJson(Map<String, dynamic> json) {
    return new Post(
      userId: json['userId'],
      id: json['id'],
      title: json['title'],
      body: json['body'],
    );
  }
}

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Fetch Data Example',
      theme: new ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text('Fetch Data Example'),
        ),
        body: new Center(
          child: new FutureBuilder<Post>(
            future: fetchPost(),
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return new Text(snapshot.data.title);
              } else if (snapshot.hasError) {
                return new Text("${snapshot.error}");
              }

              // By default, show a loading spinner
              return new CircularProgressIndicator();
            },
          ),
        ),
      ),
    );
  }
}

```

- 将请求到的数据转化为map；然后赋值给对象，返回一个对象；
- 通过FutureBuilder可以区分返回的具体情况进行判断处理UI
- Future异步请求，相当于开启了一个特殊的异步，有返回值；将执行函数挂起插入到队列，排队执行；<挂起一般就是空闲就执行，有任务又挂起>  https://www.cnblogs.com/hygblog/p/9078608.html


视屏教学案例

```
import 'package:http/http.dart' as http;

class NetUtils {
  //get请求
  static Future<String> get(String url, Map<String, dynamic> params) async {
    if (url != null && params != null && params.isNotEmpty) {
      //拼装参数
      StringBuffer sb = StringBuffer('?');
      params.forEach((key, value) {
        sb.write('$key=$value&');
      });
      //去掉最后一个&
      String paramsStr = sb.toString().substring(0, sb.length - 1);
      url += paramsStr;
    }

    print('NetUtils : $url');
    http.Response response = await http.get(url);
    return response.body;
  }

  //post请求
  static Future<String> post(String url, Map<String, dynamic> params) async {
    print('NetUtils : $url');
    http.Response response = await http.post(url, body: params);
    return response.body;
  }
}

//使用

void _getBlogDetail(int index) {
    DataUtils.isLogin().then((isLogin) {
      if (isLogin) {
        DataUtils.getAccessToken().then((accessToken) {
          Map<String, dynamic> params = Map<String, dynamic>();

          params['id'] = blogList[index]['id'];
          params['access_token'] = accessToken;
          params['dataType'] = 'json';

          NetUtils.get(AppUrls.BLOG_DETAIL, params).then((data) {
            print('BLOG_DETAIL  $data');
            if (data != null && data.isNotEmpty) {
              Map<String, dynamic> map = json.decode(data);
              String _url = map['url'];
              String _title = map['title'];

              isLoadDetail = false;

              Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => CommonWebPage(
                        title: _title,
                        url: _url,
                      )));
            }
          });
        });
      }
    });
  }


```

官方推荐使用[dio](https://github.com/flutterchina/dio)；功能比较强大：强大易用的dart http请求库，支持Restful API、FormData、拦截器、请求取消、Cookie管理、文件上传/下载……


