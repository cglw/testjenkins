package com.nj.baijiayun.myapplication;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * @author chengang
 * @date 2020-03-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication
 * @describe
 */
public class Test2Transformer implements ViewPager.PageTransformer  {

    private static final float DEFAULT_MAX_ROTATE = 15f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;
    private float MINALPHA = 0.5f;

    private int pPix;

    public Test2Transformer(int mPx) {
        this.pPix = mPx;
        Log.d("pWidth","    "+pPix);
    }

    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {
        view.setPivotY(view.getHeight() / 2);

        Log.d("transformPage",position+"===="+view.getX()+"------x("+view.getPivotX()+","+view.getPivotY()+")"+"---"+view.getHeight()+"---"+view.getWidth());

//        view.setTranslationX(-view.getWidth()*position);

        if (position < -1) {
            // [-Infinity,-1)
            view.setPivotX(0);
            view.setRotationY(1 * mMaxRotate);

        } else if (position <= 1) {

            // [-1,1]
            if (position < 0)//[0,-1]
            {
                view.setPivotX(0);
            } else//[1,0]
            {
                view.setPivotX(view.getWidth());
            }
            view.setRotationY(-position * mMaxRotate);


        } else { // (1,+Infinity]
            view.setPivotX(view.getWidth());
            view.setRotationY(-1 * mMaxRotate);
        }
        view.setTranslationX(-(view.getWidth() - pPix)*position);


        View maskView = view.findViewById(R.id.cover);
        if (position < -1 || position > 1) {
            maskView.setAlpha(1-MINALPHA);
        } else {
            //不透明->半透
            if (position < 0) {//[0,-1]
                maskView.setAlpha(1-(MINALPHA + (1 + position) * (1 - MINALPHA)));
            } else {//[1,0]
                //半透明->不透明
                maskView.setAlpha(1-(MINALPHA + (1 - position) * (1 - MINALPHA)));
            }
        }

    }


}
