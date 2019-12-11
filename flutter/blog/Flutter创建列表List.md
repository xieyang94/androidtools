List生成大量数据

```
final items = new List<String>.generate(10000, (i) => "Item $i");
```

创建基本ListView

```
new ListView(
  scrollDirection: Axis.horizontal,//可以指定横纵
  children: <Widget>[
    new ListTile(
      leading: new Icon(Icons.map),
      title: new Text('Maps'),
    ),
    new ListTile(
      leading: new Icon(Icons.photo_album),
      title: new Text('Album'),
    ),
    new ListTile(
      leading: new Icon(Icons.phone),
      title: new Text('Phone'),
    ),
  ],
);
```


创建单类型ListView
```
new ListView.builder(
  itemCount: items.length,
  itemBuilder: (context, index) {
    return new ListTile(
      title: new Text('${items[index]}'),
    );
  },
);
```

创建多类型ListView

```
new ListView.builder(
  // Let the ListView know how many items it needs to build
  itemCount: items.length,
  // Provide a builder function. This is where the magic happens! We'll
  // convert each item into a Widget based on the type of item it is.
  itemBuilder: (context, index) {
    final item = items[index];

    //共同的父类 用is来判断子类
    if (item is HeadingItem) {
      return new ListTile(
        title: new Text(
          item.heading,
          style: Theme.of(context).textTheme.headline,
        ),
      );
    } else if (item is MessageItem) {
      return new ListTile(
        title: new Text(item.sender),
        subtitle: new Text(item.body),
      );
    }
  },
);
```

创建GridView

```
new GridView.count(
  // Create a grid with 2 columns. If you change the scrollDirection to
  // horizontal, this would produce 2 rows.
  crossAxisCount: 2,
  // Generate 100 Widgets that display their index in the List
  children: new List.generate(100, (index) {
    return new Center(
      child: new Text(
        'Item $index',
        style: Theme.of(context).textTheme.headline,
      ),
    );
  }),
);
```
