package com.zhpan.bannerview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.zhpan.bannerview.provider.BannerScroller;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class CatchViewPager extends ViewPager {
    private boolean disableTouchScroll;
    private boolean firstLayout;
    private ArrayList<Integer> mArrayList;
    private BannerScroller mBannerScroller;
    private boolean mOverlapStyle;
    private SparseIntArray mSparseIntArray;

    public CatchViewPager(Context context) {
        this(context, null);
    }

    public CatchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mArrayList = new ArrayList();
        this.mSparseIntArray = new SparseIntArray();
        this.mOverlapStyle = false;
        this.firstLayout = true;
        hookScroller();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean z = false;
        try {
            if (!this.disableTouchScroll) {
                z = super.onInterceptTouchEvent(ev);
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return z;
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        if (!this.mOverlapStyle) {
            return super.getChildDrawingOrder(childCount, i);
        }
        if (i == 0 || this.mSparseIntArray.size() != childCount) {
            this.mArrayList.clear();
            this.mSparseIntArray.clear();
            int viewCenterX = getViewCenterX(this);
            for (int index = 0; index < childCount; index++) {
                int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(index))) + 1;
                this.mArrayList.add(Integer.valueOf(indexAbs));
                this.mSparseIntArray.append(indexAbs, index);
            }
            Collections.sort(this.mArrayList);
        }
        return this.mSparseIntArray.get(((Integer) this.mArrayList.get((childCount - 1) - i)).intValue());
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + (view.getWidth() / 2);
    }

    public void setOverlapStyle(boolean overlapStyle) {
        this.mOverlapStyle = overlapStyle;
    }

    public void setScrollDuration(int scrollDuration) {
        this.mBannerScroller.setDuration(scrollDuration);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.disableTouchScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void disableTouchScroll(boolean disableTouchScroll) {
        this.disableTouchScroll = disableTouchScroll;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        hookFirstLayout();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.firstLayout = false;
    }

    private void hookScroller() {
        try {
            this.mBannerScroller = new BannerScroller(getContext());
            this.mBannerScroller.setDuration(500);
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mField.set(this, this.mBannerScroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    private void hookFirstLayout() {
        try {
            Field mFirstLayout = ViewPager.class.getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(this, Boolean.valueOf(this.firstLayout));
            setCurrentItem(getCurrentItem());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public void setFirstLayout(boolean firstLayout) {
        this.firstLayout = firstLayout;
    }
}
