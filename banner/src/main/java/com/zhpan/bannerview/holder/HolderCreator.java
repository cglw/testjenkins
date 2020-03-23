package com.zhpan.bannerview.holder;

public interface HolderCreator<VH extends ViewHolder> {
    VH createViewHolder();
}
