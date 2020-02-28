package com.nj.baijiayun.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nj.baijiayun.myapplication.attrtab.ITab;
import com.nj.baijiayun.myapplication.attrtab.TabAttrCallBack;
import com.nj.baijiayun.myapplication.attrtab.TriangleView;

/**
 * @author chengang
 * @date 2020-02-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication.tab
 * @describe
 */
public class RepeatSelectTab implements ITab {

    private TabAttrCallBack tabAttrCallBack;
    private TextView mTv;
    private TriangleView mTlv1;
    private TriangleView mTlv2;

    private boolean isSelectBottom = true;

    public RepeatSelectTab(TabAttrCallBack tabAttrCallBack) {
        this.tabAttrCallBack = tabAttrCallBack;
    }

    public RepeatSelectTab() {

    }

    @Override
    public void select(boolean isSelect) {

        if (isSelect) {
            mTlv2.setColorRes(isSelectBottom ? R.color.colorSelect : R.color.colorUnSelect);
            mTlv1.setColorRes(isSelectBottom ? R.color.colorUnSelect : R.color.colorSelect);
            isSelectBottom = !isSelectBottom;
        } else {
            mTlv1.setColorRes(R.color.colorUnSelect);
            mTlv2.setColorRes(R.color.colorUnSelect);
        }
        if (tabAttrCallBack != null) {
            tabAttrCallBack.call(isSelectBottom ? "1" : "0");
        }
    }


    @Override
    public View createTabView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_tab_repeat, null);
        mTv = inflate.findViewById(R.id.tv);
        mTlv1 = inflate.findViewById(R.id.tlv_1);
        mTlv2 = inflate.findViewById(R.id.tlv_2);
        mTlv1.setColorRes(R.color.colorUnSelect);
        mTlv2.setColorRes(R.color.colorUnSelect);
        return inflate;
    }

    @Override
    public boolean isRepeat() {
        return true;
    }

    @Override
    public View createContentView(Context context, ViewGroup parent) {
        return null;
    }


    @Override
    public View getConetntView() {
        return null;
    }

    @Override
    public void setSelectAttrCallBack(TabAttrCallBack tabAttrCallBack) {
        this.tabAttrCallBack = tabAttrCallBack;
    }

    @Override
    public int weight() {
        return 1;
    }
}
