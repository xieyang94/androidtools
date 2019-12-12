TextField：有一个decoration划线装饰


TextFormField：包裹一个TextField 并将其集成在Form中。
要提供一个验证函数来检查用户的输入是否满足一定的约束（例如，一个电话号码）
或想将TextField与其他FormField集成时，使用TextFormField


```
/// State for [ExampleWidget] widgets.
class _ExampleWidgetState extends State<ExampleWidget> {
  final TextEditingController _controller = new TextEditingController();

  @override
  Widget build(BuildContext context) {
    return new Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        new TextField(
          controller: _controller,
          decoration: new InputDecoration(
            hintText: 'Type something',
          ),
        ),
        new RaisedButton(
          onPressed: () {
            showDialog(
              context: context,
              child: new AlertDialog(
                title: new Text('What you typed'),
                content: new Text(_controller.text),
              ),
            );
          },
          child: new Text('DONE'),
        ),
      ],
    );
  }
}
```

