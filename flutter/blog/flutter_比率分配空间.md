Expanded

- 可以包容一个widget填充剩余空间；
- 可以设置分配比率flex属性

```
//1:2:1
body: new Center(
  child: new Row(
    crossAxisAlignment: CrossAxisAlignment.center,
    children: [
      new Expanded(
        child: new Image.asset('images/pic1.jpg'),
      ),
      new Expanded(
        flex: 2,
        child: new Image.asset('images/pic2.jpg'),
      ),
      new Expanded(
```