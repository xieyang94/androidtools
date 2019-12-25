```
            Glide.with(mContext).load(datas.get(position).cc)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource != null) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        if (width == 0) {
                            width = 1;
                        }
//                        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) holder.getView(R.id.img_pic).getLayoutParams();
                        ViewGroup.LayoutParams params2 =  holder.itemView.getLayoutParams();
                        params2.height = height * mShowWidth / width; //等比例设置高度
                        holder.itemView.setLayoutParams(params2);
                        ((ImageView) holder.getView(R.id.img_pic)).setImageBitmap(resource);

                    }
                }
            });
```
借助于Glide实现‘

##全部代码：

```
package com.wordoor.andr.org.activity.student.task.taskdetail.cloudhomework.workreview.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.wordoor.andr.corelib.utils.WDDensityUtil;
import com.wordoor.andr.corelib.utils.WDMeasureUtils;
import com.wordoor.andr.org.R;
import com.wordoor.andr.org.base.adapter.BaseAdapter;
import com.wordoor.andr.org.base.adapter.BaseViewHolder;
import com.wordoor.andr.org.entity.request.CreatTaskContentRequest;
import com.wordoor.andr.org.utils.NullUtil;

import java.util.List;

/**
 * Create by xiey on 2019/8/16.
 */
public class StuImageTextAdapter extends BaseAdapter<CreatTaskContentRequest.CcsInfo> {

    private int screenWidth;
    private int mShowWidth;
    private Context mActivity;

    public StuImageTextAdapter(Context context, List<CreatTaskContentRequest.CcsInfo> datas, boolean mLoadMore) {
        super(R.layout.item_stu_img, datas, mLoadMore);
        mActivity = context;
        int[] screens = WDMeasureUtils.measureScreenRealMetrics((Activity) mActivity);
        this.screenWidth = screens[0];
        //WDDensityUtil见   dip_sp_px工具
        mShowWidth = screenWidth - WDDensityUtil.getInstance(mActivity).dip2px(40);
    }

    @Override
    protected void bindData(Context context, BaseViewHolder holder, int position) {
        if (NullUtil.notEmpty(datas) && position < datas.size() && NullUtil.notNull(datas.get(position))) {
            holder.getView(R.id.rl_image).setVisibility(View.VISIBLE);
            //img_pic
//            WDImageLoaderManager.getInstance().showImage(WDImageLoaderManager.getDefaultRoundOptions(
//                    holder.getView(R.id.img_pic), datas.get(position).cc));

            Glide.with(mContext).load(datas.get(position).cc)
                    .asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource != null) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        if (width == 0) {
                            width = 1;
                        }
//                        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) holder.getView(R.id.img_pic).getLayoutParams();
                        ViewGroup.LayoutParams params2 =  holder.itemView.getLayoutParams();
                        params2.height = height * mShowWidth / width; //等比例设置高度
                        holder.itemView.setLayoutParams(params2);
                        ((ImageView) holder.getView(R.id.img_pic)).setImageBitmap(resource);

                    }
                }
            });


        }

    }
}
```

布局文件：
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/rl_image"
    android:layout_width="match_parent"
    android:layout_height="@dimen/DIMEN_172DP"
    android:layout_marginTop="@dimen/DIMEN_7DP"
    android:layout_marginBottom="@dimen/DIMEN_8DP"
    android:visibility="gone">

    <ImageView
        android:id="@+id/img_pic"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"
        android:src="@drawable/default_empty" />

</RelativeLayout>
```

这样就可以完全展示，但是还会遇到一个问题：就是没有滑动感
我这边的组合就是Viewpager+Fragment+RecyclerView，其中因为业务需求，Viewpager的onMeasure方法被重写，对各自Fragment的高度做了处理；（见：viewpager_fragment_recyclerview高度不同可滑动）

这里的长列表就没有了滑动感，需要屏蔽掉RecyclerView的滑动

参考：https://blog.csdn.net/L_Sharon/article/details/81280286

## 没有滑动感处理

//自定义Manager，拦截处理canScrollVertically

```
public class CustomLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CustomLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
```

//使用
```
CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(mContext);
linearLayoutManager.setScrollEnabled(false);
mDevicesRV.setLayoutManager(linearLayoutManager);
```