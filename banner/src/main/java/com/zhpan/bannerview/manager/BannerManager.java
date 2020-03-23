package com.zhpan.bannerview.manager;

import android.content.Context;
import android.util.AttributeSet;

public class BannerManager {
    private AttributeController mAttributeController = new AttributeController(this.mBannerOptions);
    private BannerOptions mBannerOptions = new BannerOptions();

    public BannerOptions bannerOptions() {
        if (this.mBannerOptions == null) {
            this.mBannerOptions = new BannerOptions();
        }
        return this.mBannerOptions;
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        this.mAttributeController.init(context, attrs);
    }
}
