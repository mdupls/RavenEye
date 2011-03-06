package com.reality;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class RealityGraphView extends View {

	private Paint mPaint = new Paint();

	private float[] mViewRectBounds = null;

	public RealityGraphView(Context context) {
		super(context);

		mPaint.setARGB(100, 255, 255, 255);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mViewRectBounds == null) {
			int w = canvas.getWidth();
			int h = canvas.getHeight();

			int left = (int) (w / 8);
			int right = left * 7;
			int top = (int) (h / 4);
			int bottom = top * 3;

			float[] rect = { left, top, left, bottom, right, top, right,
					bottom, left, top, right, top, left, bottom, right, bottom };
			mViewRectBounds = rect;
		}

		canvas.drawLines(mViewRectBounds, mPaint);
	}

}
