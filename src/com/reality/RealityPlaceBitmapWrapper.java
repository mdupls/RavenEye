package com.reality;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import com.reality.R;

public class RealityPlaceBitmapWrapper {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;

	private Paint mPaint;

	public static Bitmap getBitmap(Place place) {
		return getBitmap(place, null);
	}

	public static Bitmap getBitmap(Place place, Location location) {
		Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT,
				Bitmap.Config.ARGB_4444);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);

		Canvas canvas = new Canvas(bitmap);

		canvas.drawOval(new RectF(0, 0, WIDTH, HEIGHT), paint);

		Coordinate placeCoord = place.coordinate;

		if (placeCoord != null && location != null) {
			float[] distance = new float[1];
			Location.distanceBetween(location.getLatitude(), location.getLongitude(), placeCoord.latitude, placeCoord.longitude, distance);
			
			double distanceAway = (int) (distance[0] / 100) / 10;

			paint.setColor(Color.BLACK);
			paint.setTextSize(14);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(distanceAway + "", (WIDTH / 2), (HEIGHT / 2), paint);
		}

		return bitmap;
	}

	public static void drawPlace(Place place, Canvas canvas, int x, int y,
			int halfWidth, int halfHeight, int bearing) {

		
	}
}
