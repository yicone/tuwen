package com.lutours.tuwen.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by apple on 14-1-23.
 * http://stackoverflow.com/questions/1016896/how-to-get-screen-dimensions/1016941#1016941
 * http://stackoverflow.com/questions/7213771/how-to-get-screen-resolution-in-android-honeycomb/8367739#8367739
 */
public class Dimension {
    private int mWidth;
    private int mHeight;

    public static Dimension create(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return new Dimension(display);
    }

    public static Dimension create(Context ctx) {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return new Dimension(display);
    }

    public Dimension(Display display) {
        Point size = new Point();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
            mWidth = size.x;
            mHeight = size.y;
        } else {
            mWidth = display.getWidth();  // deprecated
            mHeight = display.getHeight();  // deprecated
        }
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    // http://stackoverflow.com/questions/8399184/convert-dip-to-px-in-android
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
