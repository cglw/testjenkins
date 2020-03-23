package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import com.zhpan.bannerview.indicator.drawer.BaseDrawer.MeasureResult;
import com.zhpan.bannerview.manager.IndicatorOptions;

public class DrawerProxy implements IDrawer {
    private IDrawer mIDrawer;

    public DrawerProxy(IndicatorOptions indicatorOptions) {
        init(indicatorOptions);
    }

    private void init(IndicatorOptions indicatorOptions) {
        this.mIDrawer = DrawerFactory.createDrawer(indicatorOptions);
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        return this.mIDrawer.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void onDraw(Canvas canvas) {
        this.mIDrawer.onDraw(canvas);
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        init(indicatorOptions);
    }
}
