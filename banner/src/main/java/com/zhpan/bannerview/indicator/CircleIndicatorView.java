package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

@Deprecated
public class CircleIndicatorView extends BaseIndicatorView {
    private int height;
    private float mCheckedRadius;
    private float mNormalRadius;
    private float maxRadius;

    public CircleIndicatorView(Context context) {
        this(context, null);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPaint.setColor(getNormalColor());
        this.mNormalRadius = getNormalIndicatorWidth() / 2.0f;
        this.mCheckedRadius = getCheckedIndicatorWidth() / 2.0f;
        getIndicatorOptions().setIndicatorGap(this.mNormalRadius * 2.0f);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.height = getHeight();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mNormalRadius = getNormalIndicatorWidth() / 2.0f;
        this.mCheckedRadius = getCheckedIndicatorWidth() / 2.0f;
        this.maxRadius = Math.max(this.mCheckedRadius, this.mNormalRadius);
        setMeasuredDimension((int) ((((float) (getPageSize() - 1)) * getIndicatorGap()) + ((this.maxRadius + (this.mNormalRadius * ((float) (getPageSize() - 1)))) * 2.0f)), (int) (this.maxRadius * 2.0f));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {
            for (int i = 0; i < getPageSize(); i++) {
                this.mPaint.setColor(getNormalColor());
                canvas.drawCircle(this.maxRadius + (((this.mNormalRadius * 2.0f) + getIndicatorGap()) * ((float) i)), ((float) this.height) / 2.0f, this.mNormalRadius, this.mPaint);
            }
            drawSliderStyle(canvas);
        }
    }

    private void drawSliderStyle(Canvas canvas) {
        this.mPaint.setColor(getCheckedColor());
        canvas.drawCircle((this.maxRadius + (((this.mNormalRadius * 2.0f) + getIndicatorGap()) * ((float) getCurrentPosition()))) + (((this.mNormalRadius * 2.0f) + getIndicatorGap()) * getSlideProgress()), ((float) this.height) / 2.0f, this.mCheckedRadius, this.mPaint);
    }
}
