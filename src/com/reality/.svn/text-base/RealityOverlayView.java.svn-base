package com.reality;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.hardware.SensorEvent;
import android.location.Location;
import android.location.LocationListener;
import android.util.AttributeSet;
import android.util.Log;

/**
 * This class handles the drawing of different overlay views on the canvas. It
 * is responsible for interpreting the orientation and current location of the
 * device and displaying different overlay points accordingly.
 * 
 * @author Michael Du Plessis
 */
public class RealityOverlayView extends SensorView {

	public static final int COMPASS_TEXT_SIZE = 18;

	private ArrayList<Place> mPlaceOverlays;
	private ArrayList<SensorView> mSensorViews;

	private int mWidth;
	private int mHeight;

	private int mX = 0;
	private int mY = 0;
	private float mRoll = 0;
	private float mFactorX = 0;
	private float mFactorY = 0;
	private int mHorizontalTranslationCorrection = 0;
	private int mVerticalTranslationCorrection = 0;

	private boolean mFirstDraw = true;
	private boolean mInitialized = false;

	private static final int HORIZONTAL_QUADRANTS = 8;
	private static final int HORIZONTAL_QUADRANT_ANGLE = 360 / HORIZONTAL_QUADRANTS;
	private static final int VERTICAL_QUADRANTS = 2;
	private static final int VERTICAL_QUADRANT_ANGLE = 360 / VERTICAL_QUADRANTS;

	private int mCanvasWidth;
	private Canvas mCanvas = null;
	private Bitmap mBitmap;
	private final Paint mPaint;
	private final Paint mPlaceTextPaint;
	private final Paint mPlacePaint;

	public RealityOverlayView(Context context, AttributeSet attr) {
		super(context, attr);

		mPlaceOverlays = new ArrayList<Place>();
		mSensorViews = new ArrayList<SensorView>(1);

		mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
		mPaint.setColor(Color.LTGRAY);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(COMPASS_TEXT_SIZE);

		mPlacePaint = new Paint(Paint.FILTER_BITMAP_FLAG);
		mPlacePaint.setColor(0xff3D59AB);
		mPlacePaint.setShadowLayer(10, 0, 0, Color.BLACK);

		mPlaceTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPlaceTextPaint.setColor(Color.WHITE);
		mPlaceTextPaint.setTextSize(20);
		mPlaceTextPaint.setTextAlign(Align.CENTER);
		mPlaceTextPaint.setFakeBoldText(true);
		mPlaceTextPaint.setShadowLayer(10, 0, 0, Color.BLACK);
	}

	public void unbindBitmaps() {
		synchronized (this) {
			/*
			 * The bitmap will need to be recreated next time.
			 */
			mInitialized = false;
			mFirstDraw = true;

			if (mBitmap != null) {
				mBitmap.recycle();
			}

			mBitmap = null;
			mCanvas = null;
		}
	}

	public int getPlacesCount() {
		return mPlaceOverlays.size();
	}

	public void addOverlay(Place place) {
		mPlaceOverlays.add(place);
	}

	public void addAllOverlays(List<Place> places) {
		mPlaceOverlays.addAll(places);
	}

	public void registerForUpdates(SensorView view) {
		mSensorViews.add(view);
	}

	private int xx = -1;
	private int yy = -1;

	/**
	 * Draw only the views that are in the device viewpoint. Scale the movement
	 * to ensure smooth transitions of the overlays.
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (mBitmap != null) {
			// Rotate the canvas according to the device roll.
			canvas.rotate(mRoll, mHorizontalTranslationCorrection,
					mVerticalTranslationCorrection);

			synchronized (this) {
				/*
				 * Draw the generated bitmap. Account for wrap-around at North.
				 */
				if (mX >= -mHorizontalTranslationCorrection) {
					canvas.drawBitmap(mBitmap, mX - mCanvasWidth, mY, null);
				} else if (mX <= ((mWidth + mHorizontalTranslationCorrection) - mCanvasWidth)) {
					canvas.drawBitmap(mBitmap, mX + mCanvasWidth, mY, null);
				}

				canvas.drawBitmap(mBitmap, mX, mY, null);
			}

			invalidate();
		} else {
			onSizeChanged(mWidth, mHeight, 0, 0);
		}
	}

	@Override
	protected void onSizeChanged(int width, int height, int oldw, int oldh) {
		mWidth = width;
		mHeight = height;

		if (!mInitialized) {
			mHorizontalTranslationCorrection = width / 2;
			mVerticalTranslationCorrection = height / 2;

			mCanvasWidth = width * HORIZONTAL_QUADRANTS;
			int canvasHeight = height * VERTICAL_QUADRANTS;

			mFactorX = (float) width / HORIZONTAL_QUADRANT_ANGLE;
			mFactorY = (float) height / VERTICAL_QUADRANT_ANGLE;

			unbindBitmaps();

			mBitmap = Bitmap.createBitmap(mCanvasWidth, canvasHeight,
					Bitmap.Config.ARGB_4444);

			mCanvas = new Canvas(mBitmap);

			mInitialized = true;

			// Display the overlay.
			remapOverlays(mLocation);
		}

		super.onSizeChanged(width, height, oldw, oldh);
	}

	public void onSensorChanged(SensorEvent event) {
		float[] values = event.values;

		float azimuth = values[0];
		float pitch = values[1];
		float roll = values[2];

		synchronized (this) {
			mX = (-(int) (azimuth * mFactorX))
					+ mHorizontalTranslationCorrection;
			mY = -(int) (pitch * mFactorY) - mVerticalTranslationCorrection;
			mRoll = -roll;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(RealityActivity.TAG, "onLocationChanged() " + mInitialized + " "
				+ location);

		/*
		 * Recreate the augmented reality bitmap.
		 */
		if (mInitialized) {
			remapOverlays(location);
		} else {
			// Store the location if we are not initialized yet.
			super.onLocationChanged(location);
		}

		for (SensorView view : mSensorViews) {
			view.onPlacesChanged(mPlaceOverlays);
		}
	}

	private void remapOverlays(Location location) {
		final Canvas canvas = mCanvas;
		final Paint placePaint = mPlacePaint;
		final Paint placeTextPanit = mPlaceTextPaint;
		final int canvasHeight = canvas.getHeight();
		final int canvasWidth = canvas.getWidth();
		final int horizon = canvasHeight / 2;

		synchronized (this) {
			if (mFirstDraw) {
				final Paint paint = mPaint;

				final int increment = mWidth;
				final int textHeight = horizon - 20;
				int i = 0;

				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				// Draw N on both sides of north.
				canvas.drawText("N", i, textHeight, paint);
				canvas.drawText("N", canvasWidth + i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("NE", i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("E", i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("SE", i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("S", i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("SW", i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("W", i, textHeight, paint);
				i += increment;
				canvas.drawLine(i, horizon, i, canvasHeight, paint);
				canvas.drawText("NW", i, textHeight, paint);

				for (i = canvasHeight; i >= horizon; i -= ((i - horizon) / 2) + 1) {
					canvas.drawLine(0, i, canvasWidth, i, paint);
				}

				mFirstDraw = false;
			} else {
				canvas.restore();
			}

			canvas.save();

			if (location != null) {
				Log.d(RealityActivity.TAG, "Start draw places. ("
						+ mPlaceOverlays.size() + ")");
				for (Place place : mPlaceOverlays) {
					drawPlace(canvas, placePaint, placeTextPanit, location,
							place, canvasWidth, canvasHeight, horizon);
				}
				Log.d(RealityActivity.TAG, "End draw places. " + mFirstDraw);
			}
		}
	}

	private void drawPlace(Canvas canvas, Paint paint, Paint textPaint,
			Location currentLocation, Place place, int canvasWidth,
			int canvasHeight, int horizon) {
		int x;
		int y;
		int width = RealityPlaceBitmapWrapper.WIDTH;
		int height = RealityPlaceBitmapWrapper.HEIGHT;

		// int alpha = 0xff;

		final Coordinate placeCoordinate = place.coordinate;
		final Location placeLocation = new Location(currentLocation);
		placeLocation.setLatitude(placeCoordinate.latitude);
		placeLocation.setLongitude(placeCoordinate.longitude);

		if (placeLocation != null) {
			float bearing = currentLocation.bearingTo(placeLocation);
			if (bearing < 0) {
				bearing += 360;
			}

			// Set the place's current bearing.
			place.bearing = bearing;

			String units;

			float distance = Math.round(currentLocation
					.distanceTo(placeLocation));

			// Set the place's current bearing.
			place.distance = distance;

			if (distance < 1000) {
				units = (int) distance + "m";
			} else {
				units = ((int) (distance / 100) / 10) + "km";
			}

			/*
			 * Do some scaling (size).
			 */

			double factor = 0;
			if (distance >= 15000) {
				factor = 0.1;
				// alpha = 0x55;
			} else {
				factor = 1 - (distance / 20000);
				// alpha = (int) ((0xAA * (1 - (distance / 15000))) + 0x55);
			}

			width *= factor;
			height *= factor;

			int halfWidth = (width / 2);
			int halfHeight = (height / 2);

			x = (int) (bearing * mFactorX);
			int depth = (canvasHeight / 5);
			y = (int) (horizon + (depth * factor));

			/*
			 * Draw on the bitmap.
			 */

			// paint.setColor(color); // + (alpha << 24)

			int left = x - halfWidth;
			int top = y - halfHeight;
			int right = x + halfWidth;
			int bottom = y + halfHeight;

			// TODO: The text is cutoff at North if the actual shape is not
			// crossing North. (long place names are therefore cut off at North.
			if (left < 0) {
				// Draw on the left side of the canvas with some overlap across
				// north. Draw the right side of the canvas as well to ensure
				// wrap-around.
				canvas.drawRect(canvasWidth + left, top, canvasWidth, bottom,
						paint);
				canvas.drawRect(0, top, right, bottom, paint);

				canvas.drawText(place.name, x, y, textPaint);
				canvas.drawText(units, x, y + 20, textPaint);

				x += canvasWidth;
				canvas.drawText(place.name, x, y, textPaint);
				canvas.drawText(units, x, y + 20, textPaint);
			} else if (right > canvasWidth) {
				// Draw on the right side of the canvas with some overlap across
				// north. Draw the left side of the canvas as well to ensure
				// wrap-around.
				canvas.drawRect(0, top, right - canvasWidth, bottom, paint);
				canvas.drawRect(left, top, canvasWidth, bottom, paint);

				canvas.drawText(place.name, x, y, textPaint);
				canvas.drawText(units, x, y + 20, textPaint);

				x -= canvasWidth;
				canvas.drawText(place.name, x, y, textPaint);
				canvas.drawText(units, x, y + 20, textPaint);
			} else {
				// Draw the canvas normally.
				canvas.drawRect(left, top, right, bottom, paint);

				canvas.drawText(place.name, x, y, textPaint);
				canvas.drawText(units, x, y + 20, textPaint);
			}

			Log.d(RealityActivity.TAG, "Draw place: " + place + " at (" + left
					+ "," + right + ")");
		}
	}

	public Place getPlaceAt(int x, int y) {
		Place place = null;

		synchronized (this) {
			//
			// x += mX;
			// y += mY;

			xx = x - mX;
			yy = y;
		}

		return place;
	}

}