package com.nj.baijiayun.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.nj.baijiayun.myapplication.attrtab.ITab;
import com.nj.baijiayun.myapplication.attrtab.TabIndicatorView;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.view.CatchViewPager;

import java.text.MessageFormat;
import java.util.ArrayList;

//import com.youth.banner.loader.ImageLoader;
//import com.youth.banner.view.BannerViewPager;

//import com.youth.banner.adapter.BannerAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private TabIndicatorView mTabIndict;

    private BannerViewPager banner_vp;
    boolean toLeft = false;
    private Test2Transformer transformer;
    View viewById;
    int i = 0;
    private android.os.Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            i += 1;
            Log.d("TAG", "handler---->" + i);
            viewById.setRotationY(i);
            handler.sendEmptyMessageDelayed(1, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = findViewById(R.id.testV);

        viewById.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewById.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                viewById.setPivotX(viewById.getWidth() / 2);
                viewById.setPivotY(viewById.getHeight() / 2);

                handler.sendEmptyMessage(1);


            }
        });


        banner_vp = findViewById(R.id.banner_vp);
        final ArrayList<Object> imageUrls = new ArrayList<>();
        imageUrls.add("https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1035415831,1465727770&fm=26&gp=0.jpg");
        imageUrls.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3746149156,3846202622&fm=26&gp=0.jpg");
//        imageUrls.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1208538952,1443328523&fm=26&gp=0.jpg");

//        imageUrls.add("https://dss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1035415831,1465727770&fm=26&gp=0.jpg");
//        imageUrls.add("https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3746149156,3846202622&fm=26&gp=0.jpg");
//        imageUrls.add("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1208538952,1443328523&fm=26&gp=0.jpg");
//
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(outSize);
        System.out.println("Point" + outSize.x + "----" + outSize.y);
        banner_vp
//                .setOffScreenPageLimit(6)
                .setPageMargin(outSize.x / 4)
                .setPageStyle(PageStyle.MULTI_PAGE_OVERLAP)
                .setHolderCreator(() -> new ImageResourceViewHolder())
//                .setInterval(2000)
                .create(imageUrls);

        banner_vp.getViewPager().addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            Log.d("TAG", "addOnLayoutChangeListener" + banner_vp.getViewPager().getChildCount());
            if (transformer != null) {
                final int scrollX = banner_vp.getViewPager().getScrollX();
                final int childCount = banner_vp.getViewPager().getChildCount();
                for (int i = 0; i < childCount; i++) {
                    final View child = banner_vp.getViewPager().getChildAt(i);
                    final ViewPager.LayoutParams lp = (ViewPager.LayoutParams) child.getLayoutParams();
                    if (lp.isDecor) {
                        continue;
                    }
                    int clientWidth = banner_vp.getViewPager().getMeasuredWidth() - banner_vp.getViewPager().getPaddingLeft() - banner_vp.getViewPager().getPaddingRight();
                    final float transformPos = (float) (child.getLeft() - scrollX) / clientWidth;
                    transformer.transformPage(child, transformPos);
                }
            }

        });

        ((CatchViewPager) banner_vp.getViewPager()).setOverlapStyle(false);
        banner_vp.getViewPager().setPageMargin(0);
        transformer = new Test2Transformer((outSize.x / 4));
        banner_vp.setPageTransformer(transformer);
        banner_vp.setOnPageClickListener(position -> Toast.makeText(MainActivity.this, "----" + position, Toast.LENGTH_SHORT).show());

        banner_vp.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "onPageSelected" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        ((TextView) findViewById(R.id.tv)).setText(MessageFormat.format("{0}\n{1}\n{2}", BuildConfig.APPLICATION_ID
                , BuildConfig.BASE_H5_URL
                , BuildConfig.BASE_URL
                , getPackageName()

        ));
        mTv = findViewById(R.id.tv);
        mTabIndict = findViewById(R.id.tabIndict);

        mTabIndict.addTabs(
                new ITab[]{new RepeatSelectTab() {
                }, new TestTab() {
                    @Override
                    public View createContentView(Context context, ViewGroup parent) {
                        View viewById = findViewById(R.id.framlayout);
                        final View contentView = super.createContentView(context, (ViewGroup) findViewById(R.id.framlayout));
                        contentView.setClickable(true);
                        viewById.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isOpen()) {
                                    getTabAttrCallBack().close();
                                }
                            }
                        });


//                        View contentView = super.createContentView(context, (ViewGroup) findViewById(R.id.framlayout));
                        return contentView;
                    }
                }}
        );

    }


    public void testClick(View view) {
        Toast.makeText(this, "tttt", Toast.LENGTH_SHORT).show();

    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void dialog(View view) {

        new AlertDialog.Builder(this).setTitle("777").show();
    }


    public class ImageResourceViewHolder implements ViewHolder<String> {

        ImageResourceViewHolder() {
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_net;
        }

        @Override
        public void onBind(View itemView, String data, int position, int size) {
            ImageView imageView = itemView.findViewById(R.id.banner_image);
            Glide.with(getApplication()).load(data).into(imageView);

        }

    }


    public void press(View view) {
//        this.getSystemService()
//        this.getWindow().getCallback().
        WindowManager windowManager1 = this.getWindowManager();

        Dialog dialog = new Dialog(this);

        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        WindowManager.LayoutParams l = getWindow().getAttributes();
        WindowManager.LayoutParams x = new WindowManager.LayoutParams();
//        x.type = 3

//        WindowManager mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        x.alpha = 0.5f;
//        WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA_OVERLAY
        x.type =  WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        x.token=getCurrentFocus().getWindowToken();
        TextView view1 = new TextView(this);
        view1.setText("368er2739473294");
        LayoutInflater inflate = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate1 = inflate.inflate(R.layout.test, null);


        getWindowManager().addView(inflate1, x);

//        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        System.out.println("dialog----" + attributes.type);
        System.out.println("activity----" + l.type);


//        Toast.makeText()


//        getWindow().getWindowManager().
//        }
//
//        Dialog dialog;
        this.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

    }


}
