package com.zhpan.bannerview.provider;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewOutlineProvider;

@TargetApi(21)
public class RoundViewOutlineProvider extends ViewOutlineProvider {
    private float mRadius;

    public RoundViewOutlineProvider(float radius) {
        this.mRadius = radius;
    }

    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(new Rect(0, 0, view.getWidth(), view.getHeight()), this.mRadius);
    }
}
