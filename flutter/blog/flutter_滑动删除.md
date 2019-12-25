Dismissible包裹widget可实现滑动删除，还可定制滑动删除的背景色，提供了onDismissed方法：

```
new Dismissible(
  // Show a red background as the item is swiped away
  background: new Container(color: Colors.red),
  key: new Key(item),
  onDismissed: (direction) {
    items.removeAt(index);

    Scaffold.of(context).showSnackBar(
        new SnackBar(content: new Text("$item dismissed")));
  },
  child: new ListTile(title: new Text('$item')),
);
```