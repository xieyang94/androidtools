InkWell有水波纹效果，同时带有onTap事件，包裹widget就可以使用

```
// The InkWell Wraps our custom flat button Widget
new InkWell(
  // When the user taps the button, show a snackbar
  onTap: () {
    Scaffold.of(context).showSnackBar(new SnackBar(
      content: new Text('Tap'),
    ));
  },
  child: new Container(
    padding: new EdgeInsets.all(12.0),
    child: new Text('Flat Button'),
  ),
);
```