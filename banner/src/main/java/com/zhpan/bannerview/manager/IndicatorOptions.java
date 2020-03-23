package com.zhpan.bannerview.manager;

import android.graphics.Color;
import com.zhpan.bannerview.utils.BannerUtils;

public class IndicatorOptions {
    private int checkedColor = Color.parseColor("#8C6C6D72");
    private float checkedIndicatorWidth = this.normalIndicatorWidth;
    private int currentPosition;
    private float indicatorGap = this.normalIndicatorWidth;
    private int mIndicatorStyle;
    private int normalColor = Color.parseColor("#8C18171C");
    private float normalIndicatorWidth = ((float) BannerUtils.dp2px(8.0f));
    private int pageSize;
    private int prePosition;
    private int slideMode = 0;
    private float slideProgress;
    private boolean slideToRight;
    private float sliderHeight;

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNormalColor() {
        return this.normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getCheckedColor() {
        return this.checkedColor;
    }

    public void setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
    }

    public float getIndicatorGap() {
        return this.indicatorGap;
    }

    public void setIndicatorGap(float indicatorGap) {
        this.indicatorGap = indicatorGap;
    }

    public float getSlideProgress() {
        return this.slideProgress;
    }

    public void setSlideProgress(float slideProgress) {
        this.slideProgress = slideProgress;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getPrePosition() {
        return this.prePosition;
    }

    public void setPrePosition(int prePosition) {
        this.prePosition = prePosition;
    }

    public boolean isSlideToRight() {
        return this.slideToRight;
    }

    public void setSlideToRight(boolean slideToRight) {
        this.slideToRight = slideToRight;
    }

    public int getSlideMode() {
        return this.slideMode;
    }

    public void setSlideMode(int slideMode) {
        this.slideMode = slideMode;
    }

    public float getNormalIndicatorWidth() {
        return this.normalIndicatorWidth;
    }

    public void setNormalIndicatorWidth(float normalIndicatorWidth) {
        this.normalIndicatorWidth = normalIndicatorWidth;
    }

    public float getCheckedIndicatorWidth() {
        return this.checkedIndicatorWidth;
    }

    public void setCheckedIndicatorWidth(float checkedIndicatorWidth) {
        this.checkedIndicatorWidth = checkedIndicatorWidth;
    }

    public float getSliderHeight() {
        return this.sliderHeight > 0.0f ? this.sliderHeight : this.normalIndicatorWidth / 2.0f;
    }

    public void setSliderHeight(float sliderHeight) {
        this.sliderHeight = sliderHeight;
    }

    public int getIndicatorStyle() {
        return this.mIndicatorStyle;
    }

    public void setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorStyle = indicatorStyle;
    }
}
