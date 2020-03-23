package com.zhpan.bannerview.holder;

import android.view.View;
import androidx.annotation.LayoutRes;

public interface ViewHolder<T> {
    @LayoutRes
    int getLayoutId();

    void onBind(View view, T t, int i, int i2);
}
