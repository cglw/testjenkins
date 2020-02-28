package com.nj.baijiayun.myapplication.attrtab;

/**
 * @author chengang
 * @date 2020-02-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication
 * @describe
 */
public abstract class AbstractTab implements ITab {
    protected TabAttrCallBack tabAttrCallBack;

    @Override
    public int weight() {
        return 1;
    }

    @Override
    public void setSelectAttrCallBack(TabAttrCallBack tabAttrCallBack) {
       this.tabAttrCallBack=tabAttrCallBack;
    }

    public TabAttrCallBack getTabAttrCallBack() {
        return tabAttrCallBack;
    }
}
