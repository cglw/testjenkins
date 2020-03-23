package com.zhpan.bannerview.provider;

import android.view.View;
import androidx.annotation.RequiresApi;

public class ViewStyleSetter {
    private View mView;

    public ViewStyleSetter(View view) {
        this.mView = view;
    }

    @RequiresApi(api = 21)
    public void setRoundRect(float radius) {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(new RoundViewOutlineProvider(radius));
    }

    @RequiresApi(api = 21)
    public void setOvalView() {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(new OvalViewOutlineProvider());
    }

    @RequiresApi(api = 21)
    public void clearShapeStyle() {
        this.mView.setClipToOutline(false);
    }
}
