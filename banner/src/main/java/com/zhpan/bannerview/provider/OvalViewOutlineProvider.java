package com.zhpan.bannerview.provider;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

@TargetApi(21)
public class OvalViewOutlineProvider extends ViewOutlineProvider {
    public void getOutline(View view, Outline outline) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        outline.setOval(getOvalRect(rect));
    }

    private Rect getOvalRect(Rect rect) {
        int left;
        int top;
        int right;
        int bottom;
        int width = rect.right - rect.left;
        int height = rect.bottom - rect.top;
        int dW = width / 2;
        int dH = height / 2;
        if (width > height) {
            left = dW - dH;
            top = 0;
            right = dW + dH;
            bottom = dH * 2;
        } else {
            left = dH - dW;
            top = 0;
            right = dH + dW;
            bottom = dW * 2;
        }
        return new Rect(left, top, right, bottom);
    }
}
