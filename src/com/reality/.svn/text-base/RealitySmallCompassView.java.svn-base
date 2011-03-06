package com.reality;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;

public class RealitySmallCompassView extends SensorView {

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private ArrayList<Place> mPlaceOverlays;
	private float mOrientationValues[] = new float[3];

	private static final int COMPASS_RADIUS = 50;
	private int mCompassX;
	private int mCompassY;
	private int mRadius;

	final float RAD_TO_DEG = (float) (180.0f / Math.PI);
	final float DEG_TO_RAD = (float) (Math.PI / 180.0f);

	private RectF oval;
	private Canvas mCanvas = null;
	private Bitmap bitmap = null;

	private boolean mFirstDraw = true;

	public RealitySmallCompassView(Context context, AttributeSet attr) {
		super(context, attr);

		mPaint.setColor(Color.BLACK);
		mPaint.setAlpha(100);
		mPaint.setStrokeWidth(20);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		int radius = COMPASS_RADIUS;
		int diameter = radius * 2;
		int center = radius;

		mRadius = radius;

		bitmap = Bitmap.createBitmap(diameter, diameter,
				Bitmap.Config.ARGB_4444);
		final Canvas c = new Canvas(bitmap);
		mCanvas = c;

		final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setStyle(Style.FILL);

		radius = COMPASS_RADIUS - 1;
		int actualWidth = (int) (radius * 0.25);
		int strokeWidth = actualWidth;
		int adjustWidth = (int) (strokeWidth / 2);

		int w1 = (int) (strokeWidth) + 2;
		int x = center - w1;
		int y = center + w1;
		RectF oval3 = new RectF(x, x, y, y);

		p.setARGB(200, 200, 200, 200);
		c.drawOval(oval3, p);

		p.setStyle(Style.STROKE);

		p.setStrokeWidth(2);
		p.setARGB(255, 50, 50, 50);
		c.drawCircle(center, center, (int) radius, p);
		// OUTER BLACK LINE

		p.setStrokeWidth(strokeWidth + 1);

		radius -= adjustWidth;
		p.setARGB(200, 255, 255, 255);
		c.drawCircle(center, center, (int) radius, p);
		// INNER WHITE

		radius -= strokeWidth;
		p.setARGB(200, 200, 200, 200);
		c.drawCircle(center, center, (int) radius, p);
		// INNER GRAY

		radius -= strokeWidth;
		p.setARGB(200, 255, 255, 255);
		c.drawCircle(center, center, (int) radius, p);
		// INNER WHITE

		p.setStrokeWidth(0);
		p.setStyle(Style.FILL);
		p.setARGB(220, 100, 100, 100);
		c.drawCircle(center, center, 3, p);

		mCompassX = 11;
		mCompassY = 11;
		y = diameter + mCompassX - 1;
		oval = new RectF(mCompassX, mCompassY, y, y);
		RectF oval2 = new RectF(0, 0, diameter, diameter);

		p.setARGB(100, 61, 89, 171);
		c.drawArc(oval2, -105, 30, true, p);

		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (bitmap != null) {
			final float[] values = mOrientationValues;

			// Rotate the canvas according to the device roll.
			canvas.rotate(360 - values[0], mCompassX + mRadius, mCompassY
					+ mRadius);

			Log.d(RealityActivity.TAG, values[0] + "");

			canvas.drawBitmap(bitmap, mCompassX, mCompassY, null);
			canvas.drawArc(oval, values[0] - 105, 30, true, mPaint);
		}
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			for (int i = 0; i < 3; i++) {
				mOrientationValues[i] = event.values[i];
			}
		}
	}

	@Override
	public void onPlacesChanged(List<Place> places) {
		if (mCanvas != null) {
			synchronized (this) {
				for (Place place : places) {
					/*
					 * Calculate the positioning of the place on the compass
					 * view.
					 */
					// place.bearing
					// place.distance

					// mCanvas.drawCircle(10, 10, 2, mPaint);
				}
			}
		}
	}

}
