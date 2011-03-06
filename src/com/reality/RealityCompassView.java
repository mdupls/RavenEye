package com.reality;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import com.reality.R;

public class RealityCompassView extends SensorView {

	private Paint mPaint = new Paint();

	private float mOrientationValues[] = new float[3];

	private float mWidth;
	private float mHeight;

	final float RAD_TO_DEG = (float) (180.0f / Math.PI);
	final float DEG_TO_RAD = (float) (Math.PI / 180.0f);

	public RealityCompassView(Context context) {
		super(context);

		mPaint.setColor(Color.RED);
		mPaint.setAlpha(100);
		mPaint.setStrokeWidth(6);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;

		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		synchronized (this) {
			final Paint paint = mPaint;
			final float[] values = mOrientationValues;

			if (mWidth == 0) {
				mWidth = canvas.getWidth();
				mHeight = canvas.getHeight();
			}

			if (mWidth < mHeight) {
				float dir = values[0];

				int r = 200;

				int xStart = (int) (mWidth / 2);
				int yStart = (int) (mHeight / 2);
				int xEnd;
				int yEnd;

				if (dir <= 90) {
					double adj = r * Math.cos(dir * DEG_TO_RAD);
					yEnd = yStart - (int) adj;

					xEnd = (int) (Math.sqrt(Math.pow(r, 2)
							- Math.pow(yEnd - yStart, 2)) + xStart);
					xEnd = (int) (mWidth - xEnd);
				} else if (dir <= 180) {
					dir -= 90;
					double adj = r * Math.cos(dir * DEG_TO_RAD);
					xEnd = xStart - (int) adj;

					yEnd = (int) (Math.sqrt(Math.pow(r, 2)
							- Math.pow(xEnd - xStart, 2)) + yStart);
				} else if (dir <= 270) {
					dir -= 180;
					double adj = r * Math.cos(dir * DEG_TO_RAD);

					yEnd = yStart + (int) adj;

					xEnd = (int) (Math.sqrt(Math.pow(r, 2)
							- Math.pow(yEnd - yStart, 2)) + xStart);
				} else {
					dir -= 270;
					double adj = r * Math.cos(dir * DEG_TO_RAD);
					xEnd = xStart + (int) adj;

					yEnd = (int) (Math.sqrt(Math.pow(r, 2)
							- Math.pow(xEnd - xStart, 2)) + yStart);
					yEnd = (int) (mHeight - yEnd);
				}

				canvas.drawCircle(240, 400, 200, mPaint);
				canvas.drawLine(xStart, yStart, xEnd, yEnd, paint);
			}
		}
	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			for (int i = 0; i < 3; i++) {
				mOrientationValues[i] = event.values[i];
			}
		}
	}

}
