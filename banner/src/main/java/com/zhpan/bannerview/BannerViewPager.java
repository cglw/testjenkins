package com.zhpan.bannerview;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import androidx.viewpager.widget.ViewPager.PageTransformer;
import com.zhpan.bannerview.adapter.BannerPagerAdapter;
import com.zhpan.bannerview.adapter.BannerPagerAdapter.PageClickListener;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.indicator.IndicatorView;
import com.zhpan.bannerview.manager.BannerManager;
import com.zhpan.bannerview.manager.BannerOptions;
import com.zhpan.bannerview.manager.BannerOptions.IndicatorMargin;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.transform.PageTransformerFactory;
import com.zhpan.bannerview.transform.ScaleInTransformer;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.bannerview.view.CatchViewPager;
import java.util.List;

public class BannerViewPager<T, VH extends ViewHolder> extends RelativeLayout implements OnPageChangeListener {
    private int currentPosition;
    private HolderCreator<VH> holderCreator;
    private boolean isCustomIndicator;
    private BannerManager mBannerManager;
    private BannerPagerAdapter<T, VH> mBannerPagerAdapter;
    private Handler mHandler;
    private RelativeLayout mIndicatorLayout;
    private IIndicator mIndicatorView;
    private OnPageChangeListener mOnPageChangeListener;
    private OnPageClickListener mOnPageClickListener;
    private Runnable mRunnable;
    private CatchViewPager mViewPager;

    public interface OnPageClickListener {
        void onPageClick(int i);
    }

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHandler = new Handler();
        this.mRunnable = new Runnable() {
            public void run() {
                BannerViewPager.this.handlePosition();
            }
        };
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mBannerManager = new BannerManager();
        this.mBannerManager.initAttrs(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.bvp_layout, this);
        this.mViewPager = (CatchViewPager) findViewById(R.id.vp_main);
        this.mIndicatorLayout = (RelativeLayout) findViewById(R.id.bvp_layout_indicator);
    }

    protected void onDetachedFromWindow() {
        stopLoop();
        super.onDetachedFromWindow();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLoop();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 1 || action == 3 || action == 4) {
            setLooping(false);
            startLoop();
        } else if (action == 0) {
            setLooping(true);
            stopLoop();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void onPageSelected(int position) {
        int size = this.mBannerPagerAdapter.getListSize();
        this.currentPosition = BannerUtils.getRealPosition(isCanLoop(), position, size);
        if ((size > 0 && isCanLoop() && position == 0) || position == 499) {
            setCurrentItem(this.currentPosition, false);
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(this.currentPosition);
        }
        if (this.mIndicatorView != null) {
            this.mIndicatorView.onPageSelected(this.currentPosition);
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (this.mIndicatorView != null) {
            this.mIndicatorView.onPageScrollStateChanged(state);
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int listSize = this.mBannerPagerAdapter.getListSize();
        int realPosition = BannerUtils.getRealPosition(isCanLoop(), position, listSize);
        if (listSize > 0) {
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
            if (this.mIndicatorView != null) {
                this.mIndicatorView.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
        }
    }

    private void handlePosition() {
        if (this.mBannerPagerAdapter.getListSize() > 1) {
            this.currentPosition = this.mViewPager.getCurrentItem() + 1;
            this.mViewPager.setCurrentItem(this.currentPosition);
            this.mHandler.postDelayed(this.mRunnable, (long) getInterval());
        }
    }

    private void initBannerData(List<T> list) {
        if (list != null) {
            setIndicatorValues(list);
            setupViewPager(list);
            initRoundCorner();
        }
    }

    private void setIndicatorValues(List<T> list) {
        BannerOptions bannerOptions = this.mBannerManager.bannerOptions();
        bannerOptions.resetIndicatorOptions();
        if (!this.isCustomIndicator || this.mIndicatorView == null) {
            initIndicator(new IndicatorView(getContext()));
        } else {
            initIndicator(this.mIndicatorView);
        }
        this.mIndicatorView.setIndicatorOptions(bannerOptions.getIndicatorOptions());
        this.mIndicatorView.setPageSize(list.size());
    }

    private void initIndicator(IIndicator indicatorView) {
        this.mIndicatorLayout.setVisibility(this.mBannerManager.bannerOptions().getIndicatorVisibility());
        this.mIndicatorView = indicatorView;
        if (((View) this.mIndicatorView).getParent() == null) {
            this.mIndicatorLayout.removeAllViews();
            this.mIndicatorLayout.addView((View) this.mIndicatorView);
            initIndicatorViewMargin();
            initIndicatorGravity();
        }
    }

    private void initIndicatorGravity() {
        LayoutParams layoutParams = (LayoutParams) ((View) this.mIndicatorView).getLayoutParams();
        switch (this.mBannerManager.bannerOptions().getIndicatorGravity()) {
            case 0:
                layoutParams.addRule(14);
                return;
            case 2:
                layoutParams.addRule(9);
                return;
            case 4:
                layoutParams.addRule(11);
                return;
            default:
                return;
        }
    }

    private void initIndicatorViewMargin() {
        MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) this.mIndicatorView).getLayoutParams();
        IndicatorMargin indicatorMargin = this.mBannerManager.bannerOptions().getIndicatorMargin();
        if (indicatorMargin == null) {
            int dp10 = BannerUtils.dp2px(10.0f);
            layoutParams.setMargins(dp10, dp10, dp10, dp10);
            return;
        }
        layoutParams.setMargins(indicatorMargin.getLeft(), indicatorMargin.getTop(), indicatorMargin.getRight(), indicatorMargin.getBottom());
    }

    private void initRoundCorner() {
        int roundCorner = this.mBannerManager.bannerOptions().getRoundRectRadius();
        if (roundCorner > 0 && VERSION.SDK_INT >= 21) {
            new ViewStyleSetter(this).setRoundRect((float) roundCorner);
        }
    }

    private void setupViewPager(List<T> list) {
        if (this.holderCreator == null) {
            throw new NullPointerException("You must set HolderCreator for BannerViewPager");
        }
        if (list.size() > 0 && isCanLoop()) {
            this.currentPosition = (250 - (250 % list.size())) + 1;
        }
        this.mViewPager.setAdapter(getPagerAdapter(list));
        this.mViewPager.setCurrentItem(this.currentPosition);
        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        BannerOptions bannerOptions = this.mBannerManager.bannerOptions();
        this.mViewPager.setScrollDuration(bannerOptions.getScrollDuration());
        this.mViewPager.disableTouchScroll(bannerOptions.isDisableTouchScroll());
        this.mViewPager.setFirstLayout(true);
        this.mViewPager.setOffscreenPageLimit(this.mBannerManager.bannerOptions().getOffScreenPageLimit());
        initPageStyle();
        startLoop();
    }

    private PagerAdapter getPagerAdapter(List<T> list) {
        this.mBannerPagerAdapter = new BannerPagerAdapter(list, this.holderCreator);
        this.mBannerPagerAdapter.setCanLoop(isCanLoop());
        this.mBannerPagerAdapter.setPageClickListener(new PageClickListener() {
            public void onPageClick(int position) {
                if (BannerViewPager.this.mOnPageClickListener != null) {
                    BannerViewPager.this.mOnPageClickListener.onPageClick(position);
                }
            }
        });
        return this.mBannerPagerAdapter;
    }

    private void initPageStyle() {
        switch (this.mBannerManager.bannerOptions().getPageStyle()) {
            case 2:
                setMultiPageStyle(false, 0.999f);
                return;
            case 4:
                setMultiPageStyle(true, 0.85f);
                return;
            case 8:
                setMultiPageStyle(false, 0.85f);
                return;
            default:
                return;
        }
    }

    private void setMultiPageStyle(boolean overlap, float scale) {
        setClipChildren(false);
        MarginLayoutParams params = (MarginLayoutParams) this.mViewPager.getLayoutParams();
        BannerOptions bannerOptions = this.mBannerManager.bannerOptions();
        params.leftMargin = bannerOptions.getPageMargin() + bannerOptions.getRevealWidth();
        params.rightMargin = params.leftMargin;
        this.mViewPager.setOverlapStyle(overlap);
        this.mViewPager.setPageMargin(overlap ? -bannerOptions.getPageMargin() : bannerOptions.getPageMargin());
        int offScreenPageLimit = bannerOptions.getOffScreenPageLimit();
        CatchViewPager catchViewPager = this.mViewPager;
        if (offScreenPageLimit <= 2) {
            offScreenPageLimit = 2;
        }
        catchViewPager.setOffscreenPageLimit(offScreenPageLimit);
        setPageTransformer(new ScaleInTransformer(scale));
    }

    private int getInterval() {
        return this.mBannerManager.bannerOptions().getInterval();
    }

    private boolean isAutoPlay() {
        return this.mBannerManager.bannerOptions().isAutoPlay();
    }

    private boolean isLooping() {
        return this.mBannerManager.bannerOptions().isLooping();
    }

    private void setLooping(boolean looping) {
        this.mBannerManager.bannerOptions().setLooping(looping);
    }

    private boolean isCanLoop() {
        return this.mBannerManager.bannerOptions().isCanLoop();
    }

    public List<T> getList() {
        return this.mBannerPagerAdapter.getList();
    }

    public void startLoop() {
        if (!isLooping() && isAutoPlay() && this.mBannerPagerAdapter != null && this.mBannerPagerAdapter.getListSize() > 1) {
            this.mHandler.postDelayed(this.mRunnable, (long) getInterval());
            setLooping(true);
        }
    }

    public void stopLoop() {
        if (isLooping()) {
            this.mHandler.removeCallbacks(this.mRunnable);
            setLooping(false);
        }
    }

    public BannerViewPager<T, VH> setHolderCreator(HolderCreator<VH> holderCreator) {
        this.holderCreator = holderCreator;
        return this;
    }

    public BannerViewPager<T, VH> setRoundCorner(int radius) {
        this.mBannerManager.bannerOptions().setRoundRectRadius(radius);
        return this;
    }

    public BannerViewPager<T, VH> setRoundRect(int radius) {
        setRoundCorner(radius);
        return this;
    }

    public BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) {
        this.mBannerManager.bannerOptions().setAutoPlay(autoPlay);
        if (isAutoPlay()) {
            this.mBannerManager.bannerOptions().setCanLoop(true);
        }
        return this;
    }

    public BannerViewPager<T, VH> setCanLoop(boolean canLoop) {
        this.mBannerManager.bannerOptions().setCanLoop(canLoop);
        if (!canLoop) {
            this.mBannerManager.bannerOptions().setAutoPlay(false);
        }
        return this;
    }

    public BannerViewPager<T, VH> setInterval(int interval) {
        this.mBannerManager.bannerOptions().setInterval(interval);
        return this;
    }

    public BannerViewPager<T, VH> setPageTransformerStyle(int style) {
        this.mViewPager.setPageTransformer(true, new PageTransformerFactory().createPageTransformer(style));
        return this;
    }

    public void setPageTransformer(@Nullable PageTransformer transformer) {
        this.mViewPager.setPageTransformer(true, transformer);
    }

    public BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
        return this;
    }

    public BannerViewPager<T, VH> setScrollDuration(int scrollDuration) {
        this.mBannerManager.bannerOptions().setScrollDuration(scrollDuration);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorColor(@ColorInt int normalColor, @ColorInt int checkedColor) {
        this.mBannerManager.bannerOptions().setIndicatorCheckedColor(checkedColor);
        this.mBannerManager.bannerOptions().setIndicatorNormalColor(normalColor);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorRadius(int radius) {
        setIndicatorRadius(radius, radius);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorRadius(int normalRadius, int checkedRadius) {
        this.mBannerManager.bannerOptions().setNormalIndicatorWidth(normalRadius * 2);
        this.mBannerManager.bannerOptions().setCheckedIndicatorWidth(checkedRadius * 2);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorWidth(int indicatorWidth) {
        setIndicatorWidth(indicatorWidth, indicatorWidth);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorWidth(int normalWidth, int checkWidth) {
        this.mBannerManager.bannerOptions().setNormalIndicatorWidth(normalWidth);
        this.mBannerManager.bannerOptions().setCheckedIndicatorWidth(checkWidth);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) {
        this.mBannerManager.bannerOptions().setIndicatorHeight(indicatorHeight);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorGap(int indicatorGap) {
        this.mBannerManager.bannerOptions().setIndicatorGap((float) indicatorGap);
        return this;
    }

    @Deprecated
    public BannerViewPager<T, VH> showIndicator(boolean showIndicator) {
        this.mIndicatorLayout.setVisibility(showIndicator ? 0 : 8);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorVisibility(int visibility) {
        this.mBannerManager.bannerOptions().setIndicatorVisibility(visibility);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorGravity(int gravity) {
        this.mBannerManager.bannerOptions().setIndicatorGravity(gravity);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorSlideMode(int slideMode) {
        this.mBannerManager.bannerOptions().setIndicatorSlideMode(slideMode);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorView(IIndicator customIndicator) {
        if (customIndicator instanceof View) {
            this.isCustomIndicator = true;
            this.mIndicatorView = customIndicator;
        }
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorStyle(int indicatorStyle) {
        this.mBannerManager.bannerOptions().setIndicatorStyle(indicatorStyle);
        return this;
    }

    public void create(List<T> list) {
        initBannerData(list);
    }

    public int getCurrentItem() {
        return this.currentPosition;
    }

    public void setCurrentItem(int item) {
        if (!isCanLoop() || this.mBannerPagerAdapter.getListSize() <= 1) {
            this.mViewPager.setCurrentItem(item);
        } else {
            this.mViewPager.setCurrentItem(((250 - (250 % this.mBannerPagerAdapter.getListSize())) + 1) + item);
        }
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (!isCanLoop() || this.mBannerPagerAdapter.getListSize() <= 1) {
            this.mViewPager.setCurrentItem(item, smoothScroll);
        } else {
            this.mViewPager.setCurrentItem(((250 - (250 % this.mBannerPagerAdapter.getListSize())) + 1) + item, smoothScroll);
        }
    }

    public BannerViewPager<T, VH> setPageStyle(int pageStyle) {
        this.mBannerManager.bannerOptions().setPageStyle(pageStyle);
        return this;
    }

    public BannerViewPager<T, VH> setPageMargin(int pageMargin) {
        this.mBannerManager.bannerOptions().setPageMargin(pageMargin);
        this.mViewPager.setPageMargin(pageMargin);
        return this;
    }

    public BannerViewPager<T, VH> setRevealWidth(int revealWidth) {
        this.mBannerManager.bannerOptions().setRevealWidth(revealWidth);
        return this;
    }

    @Deprecated
    public ViewPager getViewPager() {
        return this.mViewPager;
    }

    public BannerViewPager<T, VH> setOffScreenPageLimit(int offScreenPageLimit) {
        this.mBannerManager.bannerOptions().setOffScreenPageLimit(offScreenPageLimit);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) {
        this.mBannerManager.bannerOptions().setIndicatorMargin(left, top, right, bottom);
        return this;
    }

    public BannerViewPager<T, VH> disableTouchScroll(boolean disableTouchScroll) {
        this.mBannerManager.bannerOptions().setDisableTouchScroll(disableTouchScroll);
        return this;
    }

    public BannerViewPager<T, VH> setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
        return this;
    }
}
