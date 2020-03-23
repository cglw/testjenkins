package com.zhpan.bannerview.manager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.zhpan.bannerview.R;
import com.zhpan.bannerview.utils.BannerUtils;

public class AttributeController {
    private BannerOptions mBannerOptions;

    public AttributeController(BannerOptions bannerOptions) {
        this.mBannerOptions = bannerOptions;
    }

    public void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
            initBannerAttrs(typedArray);
            initIndicatorAttrs(typedArray);
            typedArray.recycle();
        }
    }

    private void initIndicatorAttrs(TypedArray typedArray) {
        int indicatorCheckedColor = typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_checked_color, Color.parseColor("#8C18171C"));
        int indicatorNormalColor = typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_normal_color, Color.parseColor("#8C6C6D72"));
        int normalIndicatorWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_indicator_radius, (float) BannerUtils.dp2px(8.0f));
        int indicatorGravity = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_gravity, 0);
        int indicatorStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_style, 0);
        int indicatorSlideMode = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_slide_mode, 0);
        int indicatorVisibility = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_visibility, 0);
        this.mBannerOptions.setIndicatorCheckedColor(indicatorCheckedColor);
        this.mBannerOptions.setIndicatorNormalColor(indicatorNormalColor);
        this.mBannerOptions.setNormalIndicatorWidth(normalIndicatorWidth);
        this.mBannerOptions.setIndicatorGravity(indicatorGravity);
        this.mBannerOptions.setIndicatorStyle(indicatorStyle);
        this.mBannerOptions.setIndicatorSlideMode(indicatorSlideMode);
        this.mBannerOptions.setIndicatorVisibility(indicatorVisibility);
        this.mBannerOptions.setIndicatorGap((float) normalIndicatorWidth);
        this.mBannerOptions.setIndicatorHeight(normalIndicatorWidth / 2);
        this.mBannerOptions.setCheckedIndicatorWidth(normalIndicatorWidth);
    }

    private void initBannerAttrs(TypedArray typedArray) {
        int interval = typedArray.getInteger(R.styleable.BannerViewPager_bvp_interval, 3000);
        boolean isAutoPlay = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_auto_play, true);
        boolean isCanLoop = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_can_loop, true);
        int pageMargin = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_page_margin, 0.0f);
        int roundCorner = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_round_corner, 0.0f);
        int revealWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_reveal_width, 0.0f);
        int pageStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_page_style, 0);
        int scrollDuration = typedArray.getInt(R.styleable.BannerViewPager_bvp_scroll_duration, 500);
        this.mBannerOptions.setInterval(interval);
        this.mBannerOptions.setAutoPlay(isAutoPlay);
        this.mBannerOptions.setCanLoop(isCanLoop);
        this.mBannerOptions.setPageMargin(pageMargin);
        this.mBannerOptions.setRoundRectRadius(roundCorner);
        this.mBannerOptions.setRevealWidth(revealWidth);
        this.mBannerOptions.setPageStyle(pageStyle);
        this.mBannerOptions.setScrollDuration(scrollDuration);
    }
}
