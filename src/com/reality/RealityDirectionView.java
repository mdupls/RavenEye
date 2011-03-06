package com.reality;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class RealityDirectionView extends View {

	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public RealityDirectionView(Context context) {
		super(context);

		mPaint.setColor(Color.WHITE);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawRect(300, 300, 400, 400, mPaint);
	}

}
