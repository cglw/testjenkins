package com.nj.baijiayun.myapplication;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

/**
 * @author chengang
 * @date 2020-03-02
 * @email chenganghonor@gmail.com
 * @QQ 1410488687
 * @package_name com.nj.baijiayun.myapplication
 * @describe
 */
public class TestTransformer implements ViewPager2.PageTransformer  {

    private static final float DEFAULT_MAX_ROTATE = 45f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    private float MINALPHA = 0.1f;


    private int pPix;

    public TestTransformer(int mPx) {
        this.pPix = mPx;
//        this.mMaxRotate=dip2px(46);
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
        if (position < -1) { // [-Infinity,-1)
            view.setPivotX(0);
            view.setRotationY(1 * mMaxRotate);

        } else if (position <= 1) { // [-1,1]
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

        Log.d("pageTransform","    "+position+"   "+(-(view.getWidth() - pPix)*position));

        view.setTranslationX(-(view.getWidth() - pPix)*position);






    }
}
