package com.xiaoyingbo.valueanimatordemo.util

import android.content.Context

object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Float {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f)
    }
}