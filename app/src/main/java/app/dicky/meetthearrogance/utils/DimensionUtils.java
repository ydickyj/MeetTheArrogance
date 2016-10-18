package app.dicky.meetthearrogance.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author dicky
 */
public class DimensionUtils {

    private DimensionUtils() {
        //No instances allowed
    }

    public static int getSizeInPixels(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float pixels = metrics.density * dp;
        return (int) (pixels + 0.5f);
    }

    public static float pixelsToSp(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px 转为 dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


}
