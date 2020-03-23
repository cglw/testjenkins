package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

@Deprecated
public class DashIndicatorView extends BaseIndicatorView {
    private float maxWidth;
    private float minWidth;
    private float sliderHeight;

    public DashIndicatorView(Context context) {
        this(context, null);
    }

    public DashIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint.setColor(getNormalColor());
        this.sliderHeight = getNormalIndicatorWidth() / 2.0f;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.maxWidth = Math.max(getNormalIndicatorWidth(), getCheckedIndicatorWidth());
        this.minWidth = Math.min(getNormalIndicatorWidth(), getCheckedIndicatorWidth());
        setMeasuredDimension((int) (((((float) (getPageSize() - 1)) * getIndicatorGap()) + this.maxWidth) + (((float) (getPageSize() - 1)) * this.minWidth)), (int) getSliderHeight());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {
            for (int i = 0; i < getPageSize(); i++) {
                if (getSlideMode() == 2) {
                    smoothSlide(canvas, i);
                } else {
                    normalSlide(canvas, i);
                }
            }
        }
    }

    private void normalSlide(Canvas canvas, int i) {
        float left;
        if (getNormalIndicatorWidth() == getCheckedIndicatorWidth()) {
            this.mPaint.setColor(getNormalColor());
            left = (((float) i) * getNormalIndicatorWidth()) + (((float) i) * getIndicatorGap());
            canvas.drawRect(left, 0.0f, left + getNormalIndicatorWidth(), getSliderHeight(), this.mPaint);
            drawSliderStyle(canvas);
        } else if (i < getCurrentPosition()) {
            this.mPaint.setColor(getNormalColor());
            left = (((float) i) * this.minWidth) + (((float) i) * getIndicatorGap());
            canvas.drawRect(left, 0.0f, left + this.minWidth, getSliderHeight(), this.mPaint);
        } else if (i == getCurrentPosition()) {
            this.mPaint.setColor(getCheckedColor());
            left = (((float) i) * this.minWidth) + (((float) i) * getIndicatorGap());
            Canvas canvas2 = canvas;
            canvas2.drawRect(left, 0.0f, (this.maxWidth - this.minWidth) + (this.minWidth + left), getSliderHeight(), this.mPaint);
        } else {
            this.mPaint.setColor(getNormalColor());
            left = ((((float) i) * this.minWidth) + (((float) i) * getIndicatorGap())) + (this.maxWidth - this.minWidth);
            canvas.drawRect(left, 0.0f, left + this.minWidth, getSliderHeight(), this.mPaint);
        }
    }

    private void smoothSlide(Canvas canvas, int i) {
        this.mPaint.setColor(getNormalColor());
        float left = ((((float) i) * this.maxWidth) + (((float) i) * getIndicatorGap())) + (this.maxWidth - this.minWidth);
        canvas.drawRect(left, 0.0f, left + this.minWidth, getSliderHeight(), this.mPaint);
        drawSliderStyle(canvas);
    }

    private void drawSliderStyle(Canvas canvas) {
        this.mPaint.setColor(getCheckedColor());
        float left = ((((float) getCurrentPosition()) * this.maxWidth) + (((float) getCurrentPosition()) * getIndicatorGap())) + ((this.maxWidth + getIndicatorGap()) * getSlideProgress());
        canvas.drawRect(left, 0.0f, left + this.maxWidth, getSliderHeight(), this.mPaint);
    }

    public float getSliderHeight() {
        if (getIndicatorOptions().getSliderHeight() > 0.0f) {
            return getIndicatorOptions().getSliderHeight();
        }
        return this.sliderHeight;
    }
}
