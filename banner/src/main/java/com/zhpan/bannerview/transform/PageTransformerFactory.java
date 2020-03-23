package com.zhpan.bannerview.transform;

import androidx.viewpager.widget.ViewPager.PageTransformer;
import com.zhpan.bannerview.constants.TransformerStyle;

public class PageTransformerFactory {
    public PageTransformer createPageTransformer(int transformerStyle) {
        switch (transformerStyle) {
            case 2:
                return new DepthPageTransformer();
            case 4:
                return new StackTransformer();
            case 8:
                return new AccordionTransformer();
            case TransformerStyle.ROTATE /*16*/:
                return new RotateUpTransformer();
            case TransformerStyle.SCALE_IN /*32*/:
                return new ScaleInTransformer(0.85f);
            default:
                return null;
        }
    }
}
