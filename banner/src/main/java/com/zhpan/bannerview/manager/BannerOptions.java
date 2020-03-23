package com.zhpan.bannerview.manager;

import com.zhpan.bannerview.utils.BannerUtils;

public class BannerOptions {
    public static final int DEFAULT_SCROLL_DURATION = 500;
    private int currentPosition;
    private boolean disableTouchScroll;
    private int indicatorGravity;
    private int interval;
    private boolean isAutoPlay = false;
    private boolean isCanLoop;
    private boolean isLooping;
    private IndicatorMargin mIndicatorMargin;
    private IndicatorOptions mIndicatorOptions = new IndicatorOptions();
    private int mIndicatorVisibility;
    private int mPageMargin = BannerUtils.dp2px(20.0f);
    private int mPageStyle = 0;
    private int mRevealWidth = BannerUtils.dp2px(20.0f);
    private int mRoundRadius;
    private int mScrollDuration;
    private int offScreenPageLimit;

    public static class IndicatorMargin {
        private int bottom;
        private int left;
        private int right;
        private int top;

        public IndicatorMargin(int left, int top, int right, int bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public int getLeft() {
            return this.left;
        }

        public int getRight() {
            return this.right;
        }

        public int getTop() {
            return this.top;
        }

        public int getBottom() {
            return this.bottom;
        }
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean isLooping() {
        return this.isLooping;
    }

    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }

    public boolean isCanLoop() {
        return this.isCanLoop;
    }

    public void setCanLoop(boolean canLoop) {
        this.isCanLoop = canLoop;
    }

    public boolean isAutoPlay() {
        return this.isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.isAutoPlay = autoPlay;
    }

    public int getIndicatorGravity() {
        return this.indicatorGravity;
    }

    public void setIndicatorGravity(int indicatorGravity) {
        this.indicatorGravity = indicatorGravity;
    }

    public int getIndicatorNormalColor() {
        return this.mIndicatorOptions.getNormalColor();
    }

    public void setIndicatorNormalColor(int indicatorNormalColor) {
        this.mIndicatorOptions.setNormalColor(indicatorNormalColor);
    }

    public int getIndicatorCheckedColor() {
        return this.mIndicatorOptions.getCheckedColor();
    }

    public void setIndicatorCheckedColor(int indicatorCheckedColor) {
        this.mIndicatorOptions.setCheckedColor(indicatorCheckedColor);
    }

    public int getNormalIndicatorWidth() {
        return (int) this.mIndicatorOptions.getNormalIndicatorWidth();
    }

    public void setNormalIndicatorWidth(int normalIndicatorWidth) {
        this.mIndicatorOptions.setNormalIndicatorWidth((float) normalIndicatorWidth);
    }

    public int getCheckedIndicatorWidth() {
        return (int) this.mIndicatorOptions.getCheckedIndicatorWidth();
    }

    public void setCheckedIndicatorWidth(int checkedIndicatorWidth) {
        this.mIndicatorOptions.setCheckedIndicatorWidth((float) checkedIndicatorWidth);
    }

    public IndicatorOptions getIndicatorOptions() {
        return this.mIndicatorOptions;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public void setPageMargin(int pageMargin) {
        this.mPageMargin = pageMargin;
    }

    public int getRevealWidth() {
        return this.mRevealWidth;
    }

    public void setRevealWidth(int revealWidth) {
        this.mRevealWidth = revealWidth;
    }

    public int getIndicatorStyle() {
        return this.mIndicatorOptions.getIndicatorStyle();
    }

    public void setIndicatorStyle(int indicatorStyle) {
        this.mIndicatorOptions.setIndicatorStyle(indicatorStyle);
    }

    public int getIndicatorSlideMode() {
        return this.mIndicatorOptions.getSlideMode();
    }

    public void setIndicatorSlideMode(int indicatorSlideMode) {
        this.mIndicatorOptions.setSlideMode(indicatorSlideMode);
    }

    public float getIndicatorGap() {
        return this.mIndicatorOptions.getIndicatorGap();
    }

    public void setIndicatorGap(float indicatorGap) {
        this.mIndicatorOptions.setIndicatorGap(indicatorGap);
    }

    public float getIndicatorHeight() {
        return this.mIndicatorOptions.getSliderHeight();
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.mIndicatorOptions.setSliderHeight((float) indicatorHeight);
    }

    public int getPageStyle() {
        return this.mPageStyle;
    }

    public void setPageStyle(int pageStyle) {
        this.mPageStyle = pageStyle;
    }

    public IndicatorMargin getIndicatorMargin() {
        return this.mIndicatorMargin;
    }

    public void setIndicatorMargin(int left, int top, int right, int bottom) {
        this.mIndicatorMargin = new IndicatorMargin(left, top, right, bottom);
    }

    public int getRoundRectRadius() {
        return this.mRoundRadius;
    }

    public void setRoundRectRadius(int roundRadius) {
        this.mRoundRadius = roundRadius;
    }

    public int getScrollDuration() {
        return this.mScrollDuration;
    }

    public void setScrollDuration(int scrollDuration) {
        this.mScrollDuration = scrollDuration;
    }

    public int getIndicatorVisibility() {
        return this.mIndicatorVisibility;
    }

    public void setIndicatorVisibility(int indicatorVisibility) {
        this.mIndicatorVisibility = indicatorVisibility;
    }

    public boolean isDisableTouchScroll() {
        return this.disableTouchScroll;
    }

    public void setDisableTouchScroll(boolean disableTouchScroll) {
        this.disableTouchScroll = disableTouchScroll;
    }

    public void resetIndicatorOptions() {
        this.mIndicatorOptions.setCurrentPosition(0);
        this.mIndicatorOptions.setSlideProgress(0.0f);
    }

    public int getOffScreenPageLimit() {
        return this.offScreenPageLimit;
    }

    public void setOffScreenPageLimit(int offScreenPageLimit) {
        this.offScreenPageLimit = offScreenPageLimit;
    }
}
