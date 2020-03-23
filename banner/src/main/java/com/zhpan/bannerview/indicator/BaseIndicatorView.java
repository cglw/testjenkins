package com.zhpan.bannerview.indicator;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.zhpan.bannerview.manager.IndicatorOptions;

public class BaseIndicatorView extends View implements IIndicator {
    private IndicatorOptions mIndicatorOptions;
    protected Paint mPaint;

    public BaseIndicatorView(Context context) {
        super(context);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIndicatorOptions = new IndicatorOptions();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
    }

    public void onPageSelected(int position) {
        if (getSlideMode() == 0) {
            setCurrentPosition(position);
            setSlideProgress(0.0f);
            invalidate();
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (getSlideMode() == 2 && getPageSize() > 1) {
            scrollSlider(position, positionOffset);
        }
    }

    private void scrollSlider(int position, float positionOffset) {
        for (int i = 0; i < getPageSize(); i++) {
            if (position % getPageSize() != getPageSize() - 1) {
                setCurrentPosition(position);
                setSlideProgress(positionOffset);
                invalidate();
            } else if (((double) positionOffset) < 0.5d) {
                setCurrentPosition(position);
                setSlideProgress(0.0f);
                invalidate();
            } else {
                setCurrentPosition(0);
                setSlideProgress(0.0f);
                invalidate();
            }
        }
    }

    private boolean isSlideToRight(int position, float positionOffset) {
        int prePosition = this.mIndicatorOptions.getPrePosition();
        if (prePosition == 0 && position == getPageSize() - 1) {
            return false;
        }
        if ((prePosition != getPageSize() - 1 || position != 0) && (((float) position) + positionOffset) - ((float) prePosition) <= 0.0f) {
            return false;
        }
        return true;
    }

    public void setPageSize(int pageSize) {
        this.mIndicatorOptions.setPageSize(pageSize);
        requestLayout();
    }

    public int getPageSize() {
        return this.mIndicatorOptions.getPageSize();
    }

    public int getNormalColor() {
        return this.mIndicatorOptions.getNormalColor();
    }

    public int getCheckedColor() {
        return this.mIndicatorOptions.getCheckedColor();
    }

    public float getIndicatorGap() {
        return this.mIndicatorOptions.getIndicatorGap();
    }

    public float getSlideProgress() {
        return this.mIndicatorOptions.getSlideProgress();
    }

    public int getCurrentPosition() {
        return this.mIndicatorOptions.getCurrentPosition();
    }

    public void setCurrentPosition(int currentPosition) {
        this.mIndicatorOptions.setCurrentPosition(currentPosition);
    }

    public void setIndicatorOptions(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
    }

    public boolean isSlideToRight() {
        return this.mIndicatorOptions.isSlideToRight();
    }

    public int getSlideMode() {
        return this.mIndicatorOptions.getSlideMode();
    }

    public float getNormalIndicatorWidth() {
        return this.mIndicatorOptions.getNormalIndicatorWidth();
    }

    public float getCheckedIndicatorWidth() {
        return this.mIndicatorOptions.getCheckedIndicatorWidth();
    }

    private void setSlideProgress(float slideProgress) {
        this.mIndicatorOptions.setSlideProgress(slideProgress);
    }

    private void setPrePosition(int prePosition) {
        this.mIndicatorOptions.setPrePosition(prePosition);
    }

    private void setSlideToRight(boolean slideToRight) {
        this.mIndicatorOptions.setSlideToRight(slideToRight);
    }

    public IndicatorOptions getIndicatorOptions() {
        return this.mIndicatorOptions;
    }

    public void onPageScrollStateChanged(int state) {
    }
}
