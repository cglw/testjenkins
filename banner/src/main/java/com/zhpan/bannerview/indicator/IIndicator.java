package com.zhpan.bannerview.indicator;

import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.zhpan.bannerview.manager.IndicatorOptions;

public interface IIndicator extends OnPageChangeListener {
    void setIndicatorOptions(IndicatorOptions indicatorOptions);

    void setPageSize(int i);
}
