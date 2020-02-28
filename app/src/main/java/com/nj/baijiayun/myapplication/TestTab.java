package com.nj.baijiayun.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.nj.baijiayun.myapplication.attrtab.ITab;
import com.nj.baijiayun.myapplication.attrtab.TabAttrCallBack;
import com.nj.baijiayun.myapplication.attrtab.TriangleView;

/**
 * @author chengang
 * @date 2020-02-26
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication
 * @describe
 */
public class TestTab implements ITab {
    private TextView mTv;
    private TriangleView mTlv1;

    @Override
    public void select(boolean isSelect) {
        mTlv1.setColorRes(isSelect ? R.color.colorSelect : R.color.colorUnSelect);
        mTlv1.setDirection(isSelect ? TriangleView.DIR.BOTTOM : TriangleView.DIR.TOP);
        inflate.setVisibility(View.VISIBLE);
        if (isSelect) {
            inflate.startAnimation(occurAnimation);
        } else {
            inflate.startAnimation(dismissAnimation);
        }
    }


    @Override
    public View createTabView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_tab, null);
        mTv = inflate.findViewById(R.id.tv);
        mTlv1 = inflate.findViewById(R.id.tlv_1);
        mTlv1.setColorRes(R.color.colorUnSelect);
        return inflate;
    }

    @Override
    public boolean isRepeat() {
        return false;
    }

    View inflate;

    private Object call;

    public void closeCallBack() {
        if (tabAttrCallBack != null) {
            tabAttrCallBack.call(call);
        }
        inflate.startAnimation(dismissAnimation);
    }

    @Override
    public View createContentView(Context context, ViewGroup parent) {
        inflate = LayoutInflater.from(context).inflate(R.layout.layout_test_content, (ViewGroup) parent, false);
        inflate.setVisibility(View.GONE);
        parent.addView(inflate);
        View viewById = inflate.findViewById(R.id.btn);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = "999";
                closeCallBack();

            }
        });
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "8888", Toast.LENGTH_SHORT).show();
            }
        });
        occurAnimation = AnimationUtils.loadAnimation(context, R.anim.top_in);
        dismissAnimation = AnimationUtils.loadAnimation(context, R.anim.top_out);
        dismissAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                inflate.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return inflate;
    }

    @Override
    public View getConetntView() {
        return inflate;
    }

    @Override
    public void setSelectAttrCallBack(TabAttrCallBack tabAttrCallBack) {
        this.tabAttrCallBack = tabAttrCallBack;
    }

    @Override
    public int weight() {
        return 1;
    }

    public boolean isOpen() {
        return inflate.getVisibility() == View.VISIBLE;
    }

    public Animation getDismissAnimation() {
        return dismissAnimation;
    }

    private Animation dismissAnimation;
    private Animation occurAnimation;
    private TabAttrCallBack tabAttrCallBack;


    public TabAttrCallBack getTabAttrCallBack() {
        return tabAttrCallBack;
    }
}
