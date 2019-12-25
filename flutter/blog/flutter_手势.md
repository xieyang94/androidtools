直接用GestureDetector将Widget包裹，然后提供了一个点击事件onTap

```
// Our GestureDetector wraps our button
new GestureDetector(
  // When the child is tapped, show a snackbar
  onTap: () {
    final snackBar = new SnackBar(content: new Text("Tap"));

    Scaffold.of(context).showSnackBar(snackBar);
  },
  // Our Custom Button!
  child: new Container(
    padding: new EdgeInsets.all(12.0),
    decoration: new BoxDecoration(
      color: Theme.of(context).buttonColor,
      borderRadius: new BorderRadius.circular(8.0),
    ),
    child: new Text('My Button'),
  ),
);
```

使用GestureDetector，可以监听多种手势，例如

Tap

- onTapDown 指针已经在特定位置与屏幕接触
- onTapUp 指针停止在特定位置与屏幕接触
- onTap tap事件触发
- onTapCancel 先前指针触发的onTapDown不会在触发tap事件
- Double tap

onDoubleTap 用户快速连续两次在同一位置轻敲屏幕.

长按

- onLongPress 指针在相同位置长时间保持与屏幕接触

垂直拖动

- onVerticalDragStart 指针已经与屏幕接触并可能开始垂直移动
- onVerticalDragUpdate 指针与屏幕接触并已沿垂直方向移动
- onVerticalDragEnd 先前与屏幕接触并垂直移动的指针不再与屏幕接触，并且在停止接触屏幕时以特定速度移动

水平拖拽

- onHorizontalDragStart 指针已经接触到屏幕并可能开始水平移动
- onHorizontalDragUpdate 指针与屏幕接触并已沿水平方向移动
- onHorizontalDragEnd 先前与屏幕接触并水平移动的指针不再与屏幕接触，并在停止接触屏幕时以特定速度移动