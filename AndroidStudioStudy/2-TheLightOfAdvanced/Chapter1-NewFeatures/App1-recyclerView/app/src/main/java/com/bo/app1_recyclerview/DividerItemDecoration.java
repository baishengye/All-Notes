package com.bo.app1_recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

/**
 * 自定义风格线
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    public static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL= LinearLayout.HORIZONTAL;
    public static final int VERTICAL= LinearLayout.VERTICAL;

    private final Context mContext;

    private Drawable mDrawable;
    private int mOrientation;

    /**
     * 构造函数
     * @param context 上下文
     * @param orientation Recyclerview中的item排列方向 h:水平排列，v：竖直排列
     */
    public DividerItemDecoration(Context context, int orientation) {
        this.mContext=context;
        final TypedArray typedArray = mContext.obtainStyledAttributes(ATTRS);

        //mDrawable=context.getResources().getDrawable(R.drawable.divider);
        mDrawable=typedArray.getDrawable(0);
        typedArray.recycle();

        setOrientation(orientation);
    }

    /**
     * 设置Item如何排列
     * @param orientation
     */
    private void setOrientation(int orientation) {
        if(orientation!=HORIZONTAL&&orientation!=VERTICAL){
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mOrientation=orientation;
    }

    /**
     * 绘制分割线
     * @param c 画布
     * @param parent 所属的recyclerView
     * @param state 状态
     */
    @Override
    public void onDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        if(mOrientation==VERTICAL){
            drawVertical(c,parent);
        }else{
            drawHorizontal(c,parent);
        }
    }

    /**
     * 绘制水平布局的分割线
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight()-parent.getPaddingBottom();
        int childCount = parent.getChildCount();//RecyclerView中有多少个item
        for (int i = 0; i < childCount; i++) {
            if(i%4==0) continue;
            View child = parent.getChildAt(i);//获取到每一个item
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();//获取到RecyclerView布局上Item中的各种属性
            int left = child.getLeft() + layoutParams.rightMargin;
            int right = left + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left,top,right,bottom);//在这个矩形框里绘制图形
            mDrawable.draw(c);//在画布上绘制
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();//线的左边界
        int right = parent.getWidth()-parent.getPaddingRight();//线的有边界
        int childCount = parent.getChildCount();//RecyclerView中有多少个item
        for (int i = 4; i < childCount; i++) {
            View child = parent.getChildAt(i);//获取到每一个item
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();//获取到RecyclerView布局上Item中的各种属性
            int top = child.getTop() + layoutParams.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left,top,right,bottom);//在这个矩形框里绘制图形
            mDrawable.draw(c);//在画布上绘制
        }
    }

    @Override
    public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
        if (mOrientation==VERTICAL){
            outRect.set(0,0,0,mDrawable.getIntrinsicHeight());
        }else{
            outRect.set(0,0,0,mDrawable.getIntrinsicWidth());
        }
    }
}
