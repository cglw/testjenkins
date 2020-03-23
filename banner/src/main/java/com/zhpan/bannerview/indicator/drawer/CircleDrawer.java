package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import com.zhpan.bannerview.indicator.drawer.BaseDrawer.MeasureResult;
import com.zhpan.bannerview.manager.IndicatorOptions;

public class CircleDrawer extends BaseDrawer {
    private float maxWidth;
    private float minWidth;

    CircleDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.maxWidth = Math.max(this.mIndicatorOptions.getNormalIndicatorWidth(), this.mIndicatorOptions.getCheckedIndicatorWidth());
        this.minWidth = Math.min(this.mIndicatorOptions.getNormalIndicatorWidth(), this.mIndicatorOptions.getCheckedIndicatorWidth());
        this.mMeasureResult.setMeasureResult(getMeasureWidth(), (int) this.maxWidth);
        return this.mMeasureResult;
    }

    private int getMeasureWidth() {
        int pageSize = this.mIndicatorOptions.getPageSize();
        return (int) (((((float) (pageSize - 1)) * this.mIndicatorOptions.getIndicatorGap()) + this.maxWidth) + (((float) (pageSize - 1)) * this.minWidth));
    }

    public void onDraw(Canvas canvas) {
        drawIndicator(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        if (this.mIndicatorOptions.getPageSize() > 1) {
            float normalIndicatorWidth = this.mIndicatorOptions.getNormalIndicatorWidth();
            for (int i = 0; i < this.mIndicatorOptions.getPageSize(); i++) {
                this.mPaint.setColor(this.mIndicatorOptions.getNormalColor());
                canvas.drawCircle((this.maxWidth / 2.0f) + ((this.mIndicatorOptions.getIndicatorGap() + normalIndicatorWidth) * ((float) i)), this.maxWidth / 2.0f, normalIndicatorWidth / 2.0f, this.mPaint);
            }
            drawSliderStyle(canvas);
        }
    }

    private void drawSliderStyle(Canvas canvas) {
        this.mPaint.setColor(this.mIndicatorOptions.getCheckedColor());
        float normalIndicatorWidth = this.mIndicatorOptions.getNormalIndicatorWidth();
        float indicatorGap = this.mIndicatorOptions.getIndicatorGap();
        switch (this.mIndicatorOptions.getSlideMode()) {
            case 0:
            case 2:
                drawSmoothSlide(canvas, normalIndicatorWidth, indicatorGap);
                return;
            default:
                return;
        }
    }

    private void drawSmoothSlide(Canvas canvas, float normalIndicatorWidth, float indicatorGap) {
        canvas.drawCircle(((this.maxWidth / 2.0f) + ((normalIndicatorWidth + indicatorGap) * ((float) this.mIndicatorOptions.getCurrentPosition()))) + ((normalIndicatorWidth + indicatorGap) * this.mIndicatorOptions.getSlideProgress()), this.maxWidth / 2.0f, this.mIndicatorOptions.getCheckedIndicatorWidth() / 2.0f, this.mPaint);
    }
}
