package com.zhpan.bannerview.indicator.drawer;

import android.graphics.Paint;
import com.zhpan.bannerview.manager.IndicatorOptions;

public abstract class BaseDrawer implements IDrawer {
    IndicatorOptions mIndicatorOptions;
    MeasureResult mMeasureResult;
    Paint mPaint = new Paint();

    public class MeasureResult {
        int measureHeight;
        int measureWidth;

        void setMeasureResult(int measureWidth, int measureHeight) {
            this.measureWidth = measureWidth;
            this.measureHeight = measureHeight;
        }

        public int getMeasureWidth() {
            return this.measureWidth;
        }

        public int getMeasureHeight() {
            return this.measureHeight;
        }
    }

    BaseDrawer(IndicatorOptions indicatorOptions) {
        this.mIndicatorOptions = indicatorOptions;
        this.mPaint.setAntiAlias(true);
        this.mMeasureResult = new MeasureResult();
    }

    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }
}
