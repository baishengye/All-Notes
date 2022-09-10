package com.xiaoyingbo.iconfontdemo;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IconFontView extends androidx.appcompat.widget.AppCompatTextView {
    public IconFontView(@NonNull Context context) {
        this(context,null);
    }

    public IconFontView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconFontView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        iconFontInit(context);
    }

    private void iconFontInit(Context context) {
        Typeface icon = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
        this.setTypeface(icon);
    }

}
