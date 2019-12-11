Navigator.push导航到第二个页面；Navigator.pop返回到第一个界面，有返回值；

假如有对话框未进行关闭的话，这个方法作用的就是当前的对话框；

```
// Within the `FirstScreen` Widget
onPressed: () {
  Navigator.push(
    context,
    new MaterialPageRoute(builder: (context) => new SecondScreen()),
  );
}
```


```

// Within the SecondScreen Widget
onPressed: () {
  Navigator.pop(context);
}
```

可以传值过去，写出第二个页面widget的构造函数，同时写出对应的参数；

```
class Todo {
  final String title;
  final String description;

  Todo(this.title, this.description);
}
```


可以携带返回值

```
final result = await Navigator.push(context,new MaterialPageRoute(builder: (context) => new SelectionScreen()),);
```

```
Navigator.pop(context, 'Yep!');
```