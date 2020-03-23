package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import com.zhpan.bannerview.indicator.drawer.BaseDrawer.MeasureResult;
import com.zhpan.bannerview.indicator.drawer.DrawerProxy;
import com.zhpan.bannerview.manager.IndicatorOptions;

public class IndicatorView extends BaseIndicatorView implements IIndicator {
    private DrawerProxy mDrawerProxy;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDrawerProxy = new DrawerProxy(getIndicatorOptions());
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mDrawerProxy.onLayout(changed, left, top, right, bottom);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MeasureResult measureResult = this.mDrawerProxy.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureResult.getMeasureWidth(), measureResult.getMeasureHeight());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mDrawerProxy.onDraw(canvas);
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        super.setIndicatorOptions(indicatorOptions);
        this.mDrawerProxy.setIndicatorOptions(indicatorOptions);
    }
}
