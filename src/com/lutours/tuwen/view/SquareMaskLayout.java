package com.lutours.tuwen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by apple on 14-2-2.
 */
public class SquareMaskLayout extends FrameLayout {
    private float mLeft;
    private float mTop;
    private float mRight;
    private float mBottom;

    public SquareMaskLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // »­±ß¿ò
        Paint paint = new Paint();
        paint.setARGB(8, 8, 8, 8);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
    }
}
