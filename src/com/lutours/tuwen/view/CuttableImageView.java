package com.lutours.tuwen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class CuttableImageView extends ImageView {
	private int mFrameX, mFrameY;
	private int mFrameWidth, mFrameHeight;
	private Paint mFramePaint, mMaskRect;

	private void init() {
		mFrameWidth = DeviceUtil.getScreenSize(getResources().getDisplayMetrics())[0]
				- DeviceUtil.getPixelFromDip(getContext(), 10f);
		mFrameHeight = Math.round(mFrameWidth * 0.618f);
		
		mFramePaint = new Paint();
		mFramePaint.setColor(Color.WHITE);
		mFramePaint.setStyle(Style.STROKE);
		mFramePaint.setStrokeWidth(2);

		mMaskRect = new Paint();
		mMaskRect.setColor(Color.BLACK);
		mMaskRect.setAlpha(100);

		setOnTouchListener(new TounchListener());
	}

	public Bitmap getCutBitmap() {
		Bitmap bm = Bitmap.createBitmap(mFrameWidth, mFrameHeight,
				Config.RGB_565);
		Canvas canvas = new Canvas(bm);
		canvas.translate(-1 * mFrameX, -1 * mFrameY);
		this.draw(canvas);
		return bm;
	}

	public CuttableImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CuttableImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		init();
	}

	public CuttableImageView(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();

		mFrameX = width / 2 - mFrameWidth / 2;
		mFrameY = height / 2 - mFrameHeight / 2;

		// frame
		canvas.drawRect(width / 2 - mFrameWidth / 2, height / 2 - mFrameHeight
				/ 2, width / 2 + mFrameWidth / 2,
				height / 2 + mFrameHeight / 2, mFramePaint);

		// mask
		canvas.drawRect(0, 0, width / 2 - mFrameWidth / 2, height, mMaskRect);
		canvas.drawRect(width / 2 + mFrameWidth / 2, 0, width, height,
				mMaskRect);
		canvas.drawRect(width / 2 - mFrameWidth / 2, height / 2 + mFrameHeight
				/ 2, width / 2 + mFrameWidth / 2, height, mMaskRect);
		canvas.drawRect(width / 2 - mFrameWidth / 2, 0, width / 2 + mFrameWidth
				/ 2, height / 2 - mFrameHeight / 2, mMaskRect);
	}

	private class TounchListener implements OnTouchListener {

		private PointF startPoint = new PointF();
		private Matrix matrix = new Matrix();
		private Matrix currentMaritx = new Matrix();

		private int mode = 0;// 用于标记模式
		private static final int DRAG = 1;// 拖动
		private static final int ZOOM = 2;// 放大
		private float startDis = 0;
		private PointF midPoint;// 中心点

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				mode = DRAG;
				currentMaritx.set(((ImageView) v).getImageMatrix());// 记录ImageView当期的移动位置
				startPoint.set(event.getX(), event.getY());// 开始点
				break;

			case MotionEvent.ACTION_MOVE:// 移动事件
				if (mode == DRAG) {// 图片拖动事件
					float dx = event.getX() - startPoint.x;// x轴移动距离
					float dy = event.getY() - startPoint.y;
					matrix.set(currentMaritx);// 在当前的位置基础上移动
					matrix.postTranslate(dx, dy);
				} else if (mode == ZOOM) {// 图片放大事件
					float endDis = distance(event);// 结束距离
					if (endDis > 10f) {
						float scale = endDis / startDis;// 放大倍数
						matrix.set(currentMaritx);
						matrix.postScale(scale, scale, midPoint.x, midPoint.y);
					}
				}
				break;

			case MotionEvent.ACTION_UP:
				mode = 0;
				break;
			// 有手指离开屏幕，但屏幕还有触点(手指)
			case MotionEvent.ACTION_POINTER_UP:
				mode = 0;
				break;
			// 当屏幕上已经有触点（手指）,再有一个手指压下屏幕
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = ZOOM;
				startDis = distance(event);

				if (startDis > 10f) {// 避免手指上有两个茧
					midPoint = mid(event);
					currentMaritx.set(((ImageView) v).getImageMatrix());// 记录当前的缩放倍数
				}
				break;
			}
			((ImageView) v).setImageMatrix(matrix);
			return true;
		}
	}

	/**
	 * 两点之间的距离
	 * 
	 * @param event
	 * @return
	 */
	private static float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * 计算两点之间中心点的距离
	 * 
	 * @param event
	 */
	private static PointF mid(MotionEvent event) {
		float midx = event.getX(1) + event.getX(0);
		float midy = event.getY(1) - event.getY(0);
		return new PointF(midx / 2, midy / 2);
	}
}
