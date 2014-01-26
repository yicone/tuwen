package com.lutours.tuwen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by xdzheng on 14-1-26.
 */
public class SquareSurfaceView extends SurfaceView {

	public SquareSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		int min = Math.min(width, height);
		setMeasuredDimension(min, min);
	}
}
