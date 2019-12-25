flutter的statefulwidget中调用属性时是widget.属性；但是有时候赋值过的属性，用widget调用会发现为null；

- 可能是widget重新渲染过了，已经不是之前的那个widget了(setState方法，可能是别的原因)，这时候就需要在回调方法didUpdateWidget方法中去比对当前widget和之前的widget是否是同一个，不同则做赋值处理；


```
  @override
  void didUpdateWidget(BlogDetailPage oldWidget) {

    super.didUpdateWidget(oldWidget);
    
    if(oldWidget!=widget){
      //todo
      widget.属性=oldWidget.属性;
    }
    
  }
```