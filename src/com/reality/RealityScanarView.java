package com.reality;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import com.reality.R;

public class RealityScanarView extends SensorView {

	private Bitmap mBitmap;
	private Paint mPaint = new Paint(Color.WHITE);
	// private Canvas mCanvas = new Canvas();
	private Path mPath = new Path();
	private RectF mRect = new RectF();

	private float[] mViewRectBounds = null;
	private float mLastValues[] = new float[3 * 2];

	private float mPreviousOrientationValues[] = new float[3];
	private float mOrientationValues[] = new float[3];

	private int mColors[] = new int[3 * 2];
	private float mLastX;
	private float mScale[] = new float[2];
	private float mYOffset;
	private float mMaxX;
	private float mSpeed = 1.0f;
	private float mWidth;
	private float mHeight;

	final float RAD_TO_DEG = (float) (180.0f / Math.PI);
	final float DEG_TO_RAD = (float) (Math.PI / 180.0f);

	private ShapeDrawable mDrawable;

	private int left, top, right, bottom, width, height;

	public RealityScanarView(Context context) {
		super(context);
		mColors[0] = Color.argb(192, 255, 64, 64);
		mColors[1] = Color.argb(192, 64, 128, 64);
		mColors[2] = Color.argb(192, 64, 64, 255);
		mColors[3] = Color.argb(192, 64, 255, 255);
		mColors[4] = Color.argb(192, 128, 64, 128);
		mColors[5] = Color.argb(192, 255, 255, 64);

		// mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.WHITE);
		mRect.set(-0.5f, -0.5f, 0.5f, 0.5f);
		mPath.arcTo(mRect, 0, 180);
		firstDraw = true;

		for (int i = 0; i < 3; ++i) {
			mPreviousOrientationValues[i] = 0;
		}

		int x = 200;
		int y = 200;
		width = 50;
		height = 50;

		left = x;
		top = y;
		right = left + width;
		bottom = top + height;

		mDrawable = new ShapeDrawable(new OvalShape());
		mDrawable.getPaint().setColor(0xff74AC23);
		mDrawable.setBounds(left, top, right, bottom);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
		// mCanvas.setBitmap(mBitmap);
		// mCanvas.drawColor(0xFFFFFFFF);
		// mYOffset = h * 0.5f;
		// mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY *
		// 2)));
		// mScale[1] = -(h * 0.5f * (1.0f /
		// (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
		// mWidth = w;
		// mHeight = h;
		// if (mWidth < mHeight) {
		// mMaxX = w;
		// } else {
		// mMaxX = w - 50;
		// }
		// mLastX = mMaxX;
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private boolean firstDraw;

	@Override
	protected void onDraw(Canvas canvas) {
		if (mViewRectBounds == null) {
			mWidth = canvas.getWidth();
			mHeight = canvas.getHeight();

			int left = (int) (mWidth / 8);
			int right = left * 7;
			int top = (int) (mHeight / 4);
			int bottom = top * 3;

			float[] rect = { left, top, left, bottom, right, top, right,
					bottom, left, top, right, top, left, bottom, right, bottom };
			mViewRectBounds = rect;
		}

		canvas.drawLines(mViewRectBounds, mPaint);
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
			synchronized (this) {
				for (int i = 0; i < 3; i++) {
					mOrientationValues[i] = event.values[i];
				}
			}
		}
	}

}
