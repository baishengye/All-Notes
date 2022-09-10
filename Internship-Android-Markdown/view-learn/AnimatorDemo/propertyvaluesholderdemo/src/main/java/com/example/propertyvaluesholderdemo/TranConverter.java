package com.example.propertyvaluesholderdemo;

import android.animation.TypeConverter;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TranConverter extends TypeConverter<Tran, float[]> {

    public TranConverter(@NotNull Class<Tran> fromClass, @NotNull Class<float[]> toClass) {
        super(fromClass, toClass);
    }

    @Override
    public float[] convert(Tran value) {
        float[] f=new float[2];
        f[0]=value.getX();
        f[1]=value.getY();
        return f;
    }
}
