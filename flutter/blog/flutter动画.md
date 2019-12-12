在Flutter中，有一个AnimationController和一个Interpolator， 它是Animation类的扩展；
例如CurvedAnimation。将控制器和动画传递到AnimationWidget中，并告诉控制器启动动画；

```
class _MyFadeTest extends State<MyFadeTest> with TickerProviderStateMixin {
  AnimationController controller;
  CurvedAnimation curve;

  @override
  void initState() {
    controller = new AnimationController(duration: const Duration(milliseconds: 2000), vsync: this);
    curve = new CurvedAnimation(parent: controller, curve: Curves.easeIn);
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: new Text(widget.title),
      ),
      body: new Center(
          child: new Container(
              child: new FadeTransition(
                  opacity: curve,
                  child: new FlutterLogo(
                    size: 100.0,
                  )))),
      floatingActionButton: new FloatingActionButton(
        tooltip: 'Fade',
        child: new Icon(Icons.brush),
        onPressed: () {
          controller.forward();
        },
      ),
    );
  }
}
```

关键点：

- AnimationController
- CurvedAnimation
- TickerProviderStateMixin
- FadeTransition
- opacity
- controller.forward()
- controller.reserve()

