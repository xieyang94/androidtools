参考：https://www.jianshu.com/p/79c9c70f6502

RecyclerView性能优化：setHasFixedSize

```
/**
  * RecyclerView can perform several optimizations if it can know in advance that RecyclerView's
  * size is not affected by the adapter contents. RecyclerView can still change its size based
  * on other factors (e.g. its parent's size) but this size calculation cannot depend on the
  * size of its children or contents of its adapter (except the number of items in the adapter).
  * <p>
  * If your use of RecyclerView falls into this category, set this to {@code true}. It will allow
  * RecyclerView to avoid invalidating the whole layout when its adapter contents change.
  *
  * @param hasFixedSize true if adapter changes cannot affect the size of the RecyclerView.
  */
  public void setHasFixedSize(boolean hasFixedSize) {
      mHasFixedSize = hasFixedSize;
  }
```

注释翻译：

当知道Adapter内Item的改变不会影响RecyclerView宽高的时候，可以设置为true让RecyclerView避免重新计算大小。

## 注意：

> 当setHasFixedSize为true时，再调用notifyDataSetChanged()，发现大小还是重新计算了，看来理解出现错误了。

## 解答

首先是onMeasure里用到，这个和自定义LayoutManager相关，先不管它。然后是triggerUpdateProcessor()方法用到了，请看源码：

```
void triggerUpdateProcessor() {
    if (POST_UPDATES_ON_ANIMATION && mHasFixedSize && mIsAttached) {
        ViewCompat.postOnAnimation(RecyclerView.this, mUpdateChildViewsRunnable);
    } else {
        mAdapterUpdateDuringMeasure = true;
        requestLayout();
    }
}
```

然后看一下triggerUpdateProcessor()方法被哪些调用法：

- onItemRangeChanged()
- onItemRangeInserted()
- onItemRangeRemoved()
- onItemRangeMoved()

这样看就很明白了，当调用Adapter的增删改插方法，最后就会根据mHasFixedSize这个值来判断需要不需要requestLayout()；所以这4个方法不会重新绘制。
那我们再来看一下notifyDataSetChanged()执行的代码，最后是调用了onChanged，调用了requestLayout()，会去重新测量宽高，所以这也是为什么我们设置为true时，大小还是重新计算了的原因。

```
@Override
public void onChanged() {
    assertNotInLayoutOrScroll(null);
    mState.mStructureChanged = true;

    setDataSetChangedAfterLayout();
    if (!mAdapterHelper.hasPendingUpdates()) {
        requestLayout();
    }
}
```

## 总结

当我们确定Item的改变不会影响RecyclerView的宽高的时候可以设置setHasFixedSize(true)，并通过Adapter的增删改插方法去刷新RecyclerView，而不是通过notifyDataSetChanged()。（其实可以直接设置为true，当需要改变宽高的时候就用notifyDataSetChanged()去整体刷新一下）


