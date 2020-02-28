package com.nj.baijiayun.myapplication.attrtab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author chengang
 * @date 2020-02-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication.tab
 * @describe
 */
public interface ITab {
    //显示选中
    void select(boolean isSelect);

    //tab的ui
    View createTabView(Context context);

    //tab 是否可以重复选
    boolean isRepeat();

    View createContentView(Context context, ViewGroup parent);

    View getConetntView();

    void setSelectAttrCallBack(TabAttrCallBack tabAttrCallBack);


    int weight();



}
