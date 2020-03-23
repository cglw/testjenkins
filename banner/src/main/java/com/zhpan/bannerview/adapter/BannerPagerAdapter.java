package com.zhpan.bannerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;
import java.util.ArrayList;
import java.util.List;

public class BannerPagerAdapter<T, VH extends ViewHolder> extends PagerAdapter {
    public static final int MAX_VALUE = 500;
    private HolderCreator holderCreator;
    private boolean isCanLoop;
    private List<T> mList = new ArrayList();
    private PageClickListener mPageClickListener;

    public interface PageClickListener {
        void onPageClick(int i);
    }

    public BannerPagerAdapter(List<T> list, HolderCreator<VH> holderCreator) {
        this.mList.addAll(list);
        this.holderCreator = holderCreator;
    }

    public int getCount() {
        if (!this.isCanLoop || this.mList.size() <= 1) {
            return this.mList.size();
        }
        return 500;
    }

    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = getView(container, BannerUtils.getRealPosition(this.isCanLoop, position, this.mList.size()));
        container.addView(itemView);
        return itemView;
    }

    private View getView(ViewGroup container, int position) {
        ViewHolder<T> holder = this.holderCreator.createViewHolder();
        if (holder != null) {
            return createView(holder, position, container);
        }
        throw new NullPointerException("Can not return a null holder");
    }

    private View createView(ViewHolder<T> holder, int position, ViewGroup container) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(holder.getLayoutId(), container, false);
        if (this.mList != null && this.mList.size() > 0) {
            setViewListener(itemView, position);
            holder.onBind(itemView, this.mList.get(position), position, this.mList.size());
        }
        return itemView;
    }

    private void setViewListener(View view, final int position) {
        if (view != null) {
            view.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (BannerPagerAdapter.this.mPageClickListener != null) {
                        BannerPagerAdapter.this.mPageClickListener.onPageClick(position);
                    }
                }
            });
        }
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    public void setPageClickListener(PageClickListener pageClickListener) {
        this.mPageClickListener = pageClickListener;
    }

    public void setCanLoop(boolean canLoop) {
        this.isCanLoop = canLoop;
    }

    public List<T> getList() {
        return this.mList;
    }

    public int getListSize() {
        return this.mList.size();
    }
}
