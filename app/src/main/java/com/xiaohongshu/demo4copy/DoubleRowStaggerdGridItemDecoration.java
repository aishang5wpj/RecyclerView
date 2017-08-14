package com.xiaohongshu.demo4copy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by wupengjian on 17/8/14.
 * <p>
 * 双列瀑布流分割线的ItemDecoration
 * <p>
 * 为了避免重复计算分割线宽度：
 * 1、如果child在左边，则只画左边和上边的分割线，
 * 2、如果child在左边，但是child是在布局的最后一行，则画child的上、下、左三条分割线
 * 3、其他child，画上、下、左、右四条分割线
 * <p>
 * ****** 已知bug ********
 * <p>
 * 1、如果是最后一行，则画底部分割线（由于瀑布流的特殊性，可能不准：比如右边一个item非常长，左边几个非常短，
 * 比如a在左边，b在右边，b非常长，然后接下来c、d、e都在左边，这样算出来c、d直接的top、bottom会有双倍的
 * 分割线高度）
 * 2、item被回收高度重新计算重排列时，由于item的相对位置发生变化，item哪边的分割线该化哪边的分割线不该画也应该
 * 重新计算，不然也会有双倍间距
 */
public class DoubleRowStaggerdGridItemDecoration extends RecyclerView.ItemDecoration {

    private int mHeight;
    private Paint mPaint;

    public DoubleRowStaggerdGridItemDecoration() {
        mHeight = 10;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    public DoubleRowStaggerdGridItemDecoration setColor(int color) {
        mPaint.setColor(color);
        return this;
    }

    public DoubleRowStaggerdGridItemDecoration setHeight(int height) {
        mHeight = height;
        return this;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) parent.getLayoutManager();
        //画左边
        outRect.left = mHeight;
        //画上边
        outRect.top = mHeight;
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        //如果view不是左边的，则画右边分割线
        if (params.getSpanIndex() != 0) {
            outRect.right = mHeight;
        } else {
            outRect.right = 0;
        }
        //如果是最后一行，则画底部分割线（由于瀑布流的特殊性，可能不准：比如右边一个item非常长，左边几个非常短，
        // 比如a在左边，b在右边，b非常长，然后接下来c、d、e都在左边，这样算出来c、d直接的top、bottom会有双倍的
        // 分割线高度）
        if (parent.indexOfChild(view) + manager.getSpanCount() > manager.getItemCount()) {
            outRect.bottom = mHeight;
        } else {
            outRect.bottom = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) parent.getLayoutManager();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {

            View view = parent.getChildAt(i);

            //左边分割线的 起 位置
            int left = manager.getDecoratedLeft(view);
            //顶部分割线的 起 位置
            int top = manager.getDecoratedTop(view);
            //右边分割线的 止 位置
            int right = manager.getDecoratedRight(view);
            //底部分割线的 止 位置
            int bottom = manager.getDecoratedBottom(view);

            //画左边
            c.drawRect(left, top, view.getLeft(), bottom, mPaint);
            //画上边
            c.drawRect(left, top, right, view.getTop(), mPaint);
            //画右边
            c.drawRect(view.getRight(), top, right, bottom, mPaint);
            //画底部
            c.drawRect(left, view.getBottom(), right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
