参考：https://blog.csdn.net/u012947056/article/details/81292621

## demo代码

```
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:orientation="vertical">


    <TextView
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:background="@color/gray"
        android:hint="haha"
        />
</ScrollView>
```
```
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ScrollViewEditTextActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_edit_text);
    }
}
```

## 截图

事情并不是你想像的那样，为什么我给定高度不好使，TextView的高度为什么只有一行的高度

![android_measure_UNSPECIFIED](/android/image/android_measure_UNSPECIFIED.png)


## 源码分析

### 先看ScrollView的measure源码：

ScrollView.java

```
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (!mFillViewport) {
        return;
    }
//...ignore code
```

这里mFillViewport为false，也就是说它走的是super.onMeasure，super是FrameLayout

FrameLayout.java

```
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int count = getChildCount();

    //...ignore code

    int maxHeight = 0;
    int maxWidth = 0;
    int childState = 0;

    for (int i = 0; i < count; i++) {
        final View child = getChildAt(i);
        if (mMeasureAllChildren || child.getVisibility() != GONE) {
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            //...ignore code
```

遍历子view，然后调用measureChildWithMargins,而这个方法被ScrllView覆盖了

ScrollView.java

```
@Override
protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed,int parentHeightMeasureSpec, int heightUsed) {
    final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
    final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin+ widthUsed, lp.width);
    final int childHeightMeasureSpec = MeasureSpec.makeSafeMeasureSpec(MeasureSpec.getSize(parentHeightMeasureSpec), MeasureSpec.UNSPECIFIED);
    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
}
```
看这行
```
final int childHeightMeasureSpec = MeasureSpec.makeSafeMeasureSpec(MeasureSpec.getSize(parentHeightMeasureSpec), MeasureSpec.UNSPECIFIED);
```
它强行把size和mode=**MeasureSpec.UNSPECIFIED**传给子View


### 再来看TextView源码

```
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //...ignore code
        if (heightMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            height = heightSize;
            mDesiredHeightAtMeasure = -1;
        } else {
            int desired = getDesiredHeight();

            height = desired;
            mDesiredHeightAtMeasure = desired;

            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desired, heightSize);
            }
        }

        int unpaddedHeight = height - getCompoundPaddingTop() - getCompoundPaddingBottom();
        if (mMaxMode == LINES && mLayout.getLineCount() > mMaximum) {
            unpaddedHeight = Math.min(unpaddedHeight, mLayout.getLineTop(mMaximum));
        }

        /*
         * We didn't let makeNewLayout() register to bring the cursor into view,
         * so do it here if there is any possibility that it is needed.
         */
        if (mMovement != null ||
            mLayout.getWidth() > unpaddedWidth ||
            mLayout.getHeight() > unpaddedHeight) {
            registerForPreDraw();
        } else {
            scrollTo(0, 0);
        }

        setMeasuredDimension(width, height);
}
```


- 如果父容器是ScrollView,那mode=UNSPECIFIED,就会走第8行
- 第9行是根据内容算出行数再确定高度，也就是TextView的实际内容高度
- 然后这个高度就传给了setMeasuredDimension
- 导致xml里配的高度不生效，这也是有些程序员会说我设了高度为啥没生效的原因
- 抛开ScrollView不说，如果父容器是atMost，它会根据父容器能支持的最大高度和desired对比，取最小的；因此at_most就是wrap_content,至于为什么at_most对应的是wrap_content，有兴趣可以看源码。


## 总结

- UNSPECIFIED会在ScrollView的measure方法里传给子View
- 子View收到UNSPECIFIED，会根据自己的实际内容大小来决定高度
- UNSPECIFIED与AT_MOST的区别就是，它没有最大size限定这也说明UNSPECIFIED在ScrollView里很实用，因为ScrllView不需要限定子View的大小，它可以滚动嘛

