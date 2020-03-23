package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.zhpan.bannerview.indicator.drawer.BaseDrawer.MeasureResult;
import com.zhpan.bannerview.manager.IndicatorOptions;

public class RoundRectDrawer extends BaseDrawer {
    private float maxWidth;
    private float minWidth;

    RoundRectDrawer(IndicatorOptions indicatorOptions) {
        super(indicatorOptions);
    }

    public MeasureResult onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.maxWidth = Math.max(this.mIndicatorOptions.getNormalIndicatorWidth(), this.mIndicatorOptions.getCheckedIndicatorWidth());
        this.minWidth = Math.min(this.mIndicatorOptions.getNormalIndicatorWidth(), this.mIndicatorOptions.getCheckedIndicatorWidth());
        this.mMeasureResult.setMeasureResult(getMeasureWidth(), (int) this.mIndicatorOptions.getSliderHeight());
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
        int pageSize = this.mIndicatorOptions.getPageSize();
        if (pageSize > 1) {
            for (int i = 0; i < pageSize; i++) {
                if (this.mIndicatorOptions.getSlideMode() == 2) {
                    smoothSlide(canvas, i);
                } else {
                    normalSlide(canvas, i);
                }
            }
        }
    }

    private void normalSlide(Canvas canvas, int i) {
        float normalIndicatorWidth = this.mIndicatorOptions.getNormalIndicatorWidth();
        int normalColor = this.mIndicatorOptions.getNormalColor();
        float indicatorGap = this.mIndicatorOptions.getIndicatorGap();
        float sliderHeight = this.mIndicatorOptions.getSliderHeight();
        int currentPosition = this.mIndicatorOptions.getCurrentPosition();
        float left;
        if (normalIndicatorWidth == this.mIndicatorOptions.getCheckedIndicatorWidth()) {
            this.mPaint.setColor(normalColor);
            left = (((float) i) * normalIndicatorWidth) + (((float) i) * indicatorGap);
            canvas.drawRoundRect(new RectF(left, 0.0f, left + normalIndicatorWidth, sliderHeight), sliderHeight, sliderHeight, this.mPaint);
            drawSliderStyle(canvas);
        } else if (i < currentPosition) {
            this.mPaint.setColor(normalColor);
            left = (((float) i) * this.minWidth) + (((float) i) * indicatorGap);
            canvas.drawRoundRect(new RectF(left, 0.0f, this.minWidth + left, sliderHeight), sliderHeight, sliderHeight, this.mPaint);
        } else if (i == currentPosition) {
            this.mPaint.setColor(this.mIndicatorOptions.getCheckedColor());
            left = (((float) i) * this.minWidth) + (((float) i) * indicatorGap);
            canvas.drawRoundRect(new RectF(left, 0.0f, (this.minWidth + left) + (this.maxWidth - this.minWidth), sliderHeight), sliderHeight, sliderHeight, this.mPaint);
        } else {
            this.mPaint.setColor(normalColor);
            left = ((((float) i) * this.minWidth) + (((float) i) * indicatorGap)) + (this.maxWidth - this.minWidth);
            canvas.drawRoundRect(new RectF(left, 0.0f, this.minWidth + left, sliderHeight), sliderHeight, sliderHeight, this.mPaint);
        }
    }

    private void smoothSlide(Canvas canvas, int i) {
        this.mPaint.setColor(this.mIndicatorOptions.getNormalColor());
        float sliderHeight = this.mIndicatorOptions.getSliderHeight();
        float left = ((((float) i) * this.maxWidth) + (((float) i) * this.mIndicatorOptions.getIndicatorGap())) + (this.maxWidth - this.minWidth);
        canvas.drawRoundRect(new RectF(left, 0.0f, this.minWidth + left, sliderHeight), sliderHeight, sliderHeight, this.mPaint);
        drawSliderStyle(canvas);
    }

    private void drawSliderStyle(Canvas canvas) {
        this.mPaint.setColor(this.mIndicatorOptions.getCheckedColor());
        int currentPosition = this.mIndicatorOptions.getCurrentPosition();
        float indicatorGap = this.mIndicatorOptions.getIndicatorGap();
        float sliderHeight = this.mIndicatorOptions.getSliderHeight();
        float left = ((((float) currentPosition) * this.maxWidth) + (((float) currentPosition) * indicatorGap)) + ((this.maxWidth + indicatorGap) * this.mIndicatorOptions.getSlideProgress());
        canvas.drawRoundRect(new RectF(left, 0.0f, this.minWidth + left, sliderHeight), sliderHeight, sliderHeight, this.mPaint);
    }
}
