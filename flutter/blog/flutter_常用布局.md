

- Container：就是一个容器，包罗万象，可以设置padding和margin、border；
- GridView：网格布局，可滚动；
- ListView：可滚动列表（神器）；
- Stack：就像FrameLayout，绝对布局（帧布局），直接覆盖的那种；
- Card：带卡片阴影效果（MD风格）；
- ListTile：封装好的行，将最多3行文字，以及可选的行前和和行尾的图标排成一行（ListView的好辅助）；


Container：

![flutter_container](/flutter/image/flutter_container.png)


```
child: new Container(
    decoration: new BoxDecoration(
    border: new Border.all(width: 10.0, color: Colors.black38),
    borderRadius: const BorderRadius.all(const Radius.circular(8.0)),),
    margin: const EdgeInsets.all(4.0),
    child: new Image.asset('images/pic2.jpg'),
),
```


GridView：

- 在网格中放置widget
- 检测列内容超过渲染框时自动提供滚动
- 构建您自己的自定义grid，或使用一下提供的grid之一:
- GridView.count 允许您指定列数
- GridView.extent 允许您指定项的最大像素宽度

```
List<Container> _buildGridTileList(int count) {

  return new List<Container>.generate(
      count,
      (int index) =>
          new Container(child: new Image.asset('images/pic${index+1}.jpg')));
}

Widget buildGrid() {
  return new GridView.extent(
      maxCrossAxisExtent: 150.0,
      padding: const EdgeInsets.all(4.0),
      mainAxisSpacing: 4.0,
      crossAxisSpacing: 4.0,
      children: _buildGridTileList(30));
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text(widget.title),
      ),
      body: new Center(
        child: buildGrid(),
      ),
    );
  }
}
```

[ListView](/flutter/blog/Flutter创建列表List.md)

Stack：

叠加效果

```
class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    var stack = new Stack(
      alignment: const Alignment(0.6, 0.6),
      children: [
        new CircleAvatar(
          backgroundImage: new AssetImage('images/pic.jpg'),
          radius: 100.0,
        ),
        new Container(
          decoration: new BoxDecoration(
            color: Colors.black45,
          ),
          child: new Text(
            'Mia B',
            style: new TextStyle(
              fontSize: 20.0,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),
        ),
      ],
    );
    // ...
  }
}
```

![flutter_stack](/flutter/image/flutter_stack.png)


Card：

直接包裹一个widget，可以设置角度和阴影等一些属性

ListTile：

专门的行级widget；

```
class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    var card = new SizedBox(
      height: 210.0,
      child: new Card(
        child: new Column(
          children: [
            new ListTile(
              title: new Text('1625 Main Street',
                  style: new TextStyle(fontWeight: FontWeight.w500)),
              subtitle: new Text('My City, CA 99984'),
              leading: new Icon(
                Icons.restaurant_menu,
                color: Colors.blue[500],
              ),
            ),
            new Divider(),
            new ListTile(
              title: new Text('(408) 555-1212',
                  style: new TextStyle(fontWeight: FontWeight.w500)),
              leading: new Icon(
                Icons.contact_phone,
                color: Colors.blue[500],
              ),
            ),
            new ListTile(
              title: new Text('costa@example.com'),
              leading: new Icon(
                Icons.contact_mail,
                color: Colors.blue[500],
              ),
            ),
          ],
        ),
      ),
    );
  //...
}
```



其他：

- Form
- FormField
- Checkbox
- DropdownButton
- FlatButton
- FloatingActionButton
- IconButton
- Radio
- RaisedButton
- Slider
- Switch
- TextField








