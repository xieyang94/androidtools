Column中子widget是从上往下;Row中子widget是从左往右；

方向都是默认的，当然可以指定方向direction


MainAxisAlignment 和 CrossAxisAlignment

MainAxisAlignment是主轴方向，CrossAxisAlignment是垂直主轴的方向

Row:

![flutter_row](/flutter/image/flutter_row.png)

Column:

![flutter_column](/flutter/image/flutter_column.png)


flex：比率权重

```
//使子widget居中
mainAxisSize: MainAxisSize.min,
```

