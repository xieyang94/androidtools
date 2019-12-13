```
/**
 * 控制child是否显示
 *
    当offstage为true，控件隐藏； 当offstage为false，显示；
    当Offstage不可见的时候，如果child有动画等，需要手动停掉，Offstage并不会停掉动画等操作。

    const Offstage({ Key key, this.offstage = true, Widget child })
 */
 ```

 ```
Offstage(
    offstage: true/false [bool],
    child: Container(
        color: Color(0x00000000),
        child: Center(
            child: Container(
                width: MediaQuery.of(context).size.width / 3,
                height: MediaQuery.of(context).size.width / 3,
                decoration: BoxDecoration(
                    color: Color(0x88000000),
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                ),
                child: Center(
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: <Widget>[
                            CupertinoActivityIndicator(),
                            SizedBox(
                                height: 10.0,
                            ),
                            Text('努力加载中...',
                                style: TextStyle(color: Colors.white))
                        ],
                    ),
                ),
            ),
        ),
    ),
),
 ```
