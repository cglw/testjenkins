package com.zhpan.bannerview.transform;

import android.view.View;
import androidx.viewpager.widget.ViewPager.PageTransformer;

public class ScaleInTransformer implements PageTransformer {
    private static final float DEFAULT_CENTER = 0.5f;
    public static final float DEFAULT_MIN_SCALE = 0.85f;
    public static final float MAX_SCALE = 0.999f;
    private float mMinScale;

    public ScaleInTransformer(float minScale) {
        this.mMinScale = minScale;
    }

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        view.setPivotY((float) (view.getHeight() / 2));
        view.setPivotX((float) (pageWidth / 2));
        if (position < -1.0f) {
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
            view.setPivotX((float) pageWidth);
        } else if (position > 1.0f) {
            view.setPivotX(0.0f);
            view.setScaleX(this.mMinScale);
            view.setScaleY(this.mMinScale);
        } else if (position < 0.0f) {
            scaleFactor = ((1.0f + position) * (1.0f - this.mMinScale)) + this.mMinScale;
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setPivotX(((float) pageWidth) * (((-position) * DEFAULT_CENTER) + DEFAULT_CENTER));
        } else {
            scaleFactor = ((1.0f - position) * (1.0f - this.mMinScale)) + this.mMinScale;
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setPivotX(((float) pageWidth) * ((1.0f - position) * DEFAULT_CENTER));
        }
    }
}
