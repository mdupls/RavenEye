package com.reality;

import java.util.HashSet;

import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.reality.R;

/**
 * This class is responsible for interpreting the orientation of the device and
 * ensuring smooth and jitter free sensor values. This is useful for situations
 * in which sensor values play some part in the visual representation and
 * transformation of UI elements on screen.
 * 
 * @author Michael Du Plessis
 */
public class RealityPhysicsSensorListener implements SensorEventListener,
		LocationListener {

	public static final String TAG = "RealityPhysicsSensorListener";

	/*
	 * The higher the alpha, the faster the scaling occurs.
	 */
	public static volatile float alpha = (float) 0.2;
	public static volatile float beta = 1 - alpha;

	public static volatile float slowAlpha = (float) 0.01;
	public static volatile float slowBeta = 1 - slowAlpha;

	public static final int DEAD_ZONE_MOVEMENT = 6;

	float[] mR = new float[16];
	float[] mOutR = new float[16];
	float[] mI = new float[16];
	private float[] mGravity = new float[] { 0, 0, 0 };
	private float[] mGeomagnetic = new float[] { 0, 0, 0 };
	private float[] mOldOrientation = new float[] { 0, 0, 0 };

	private float mDeclination = 0f;
	private boolean mHasDeclination = false;

	final float rad2deg = 180 / (float) Math.PI;

	private final HashSet<SensorEventListener> mObservers;

	public RealityPhysicsSensorListener() {
		mObservers = new HashSet<SensorEventListener>();
	}

	public void registerForUpdates(SensorEventListener observer) {
		mObservers.add(observer);
	}

	public void deregister(SensorEventListener observer) {
		mObservers.remove(observer);
	}

	/*
	 * SensorEventListener methods.
	 */

	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void onSensorChanged(SensorEvent event) {
		synchronized (this) {
			if (event.accuracy != SensorManager.SENSOR_STATUS_ACCURACY_HIGH) {
				return;
			}

			float[] values = event.values;
			float[] gravity = mGravity;
			float[] geomagnetic = mGeomagnetic;

			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				for (int i = 0; i < 3; ++i) {
					gravity[i] = values[i];
				}
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				for (int i = 0; i < 3; ++i) {
					geomagnetic[i] = values[i];
				}
				break;
			default:
				return;
			}

			if (!SensorManager.getRotationMatrix(mR, mI, gravity, geomagnetic)) {
				return;
			}

			// Re-map the coordinate system for augmented reality.
			SensorManager.remapCoordinateSystem(mR, SensorManager.AXIS_X,
					SensorManager.AXIS_Z, mOutR);

			SensorManager.getOrientation(mOutR, values);

			// float inclination = SensorManager.getInclination(mI) * rad2deg;

			int i;

			float[] oldOrientation = mOldOrientation;
			float oOrient = oldOrientation[0];
			float nOrient = values[0] * rad2deg; // azimuth
			values[1] *= rad2deg; // pitch
			values[2] *= rad2deg; // roll

			nOrient += mDeclination;
			if (nOrient < 0) {
				nOrient += 360;
			}

			/*
			 * Account for rotations around north.
			 */
			if (Math.abs(oOrient - nOrient) > 180) {
				i = 1;

				// We will want to rotate over north since it is the shorter
				// distance.

				float max = Math.max(oOrient, nOrient);

				if (max == oOrient) {
					// Clockwise rotation.
					if (Math.abs(values[0] - oOrient) < DEAD_ZONE_MOVEMENT) {
						nOrient = (float) (((oOrient - 360) * slowAlpha) + (nOrient * slowBeta));
					} else {
						nOrient = (float) (((oOrient - 360) * alpha) + (nOrient * beta));
					}
				} else {
					// Counter-clockwise rotation.
					if (Math.abs(values[0] - oOrient) < DEAD_ZONE_MOVEMENT) {
						nOrient = (float) ((oOrient * slowAlpha) + ((nOrient - 360) * slowBeta));
					} else {
						nOrient = (float) ((oOrient * alpha) + ((nOrient - 360) * beta));
					}
				}

				if (nOrient >= 360) {
					nOrient -= 360;
				} else if (nOrient < 0) {
					nOrient += 360;
				}

				oldOrientation[0] = nOrient;
				values[0] = nOrient;
			} else {
				values[0] = nOrient;
				i = 0;
			}

			float val, oldVal;
			for (; i < 3; ++i) {
				val = values[i];
				oldVal = oldOrientation[i];

				if (Math.abs(val - oldVal) < DEAD_ZONE_MOVEMENT) {
					val = (float) ((oldVal * slowBeta) + (val * slowAlpha));
				} else {
					val = (float) ((oldVal * beta) + (val * alpha));
				}

				values[i] = val;
				oldOrientation[i] = val;
			}

			/*
			 * Broadcast to the observers.
			 */
			for (SensorEventListener listener : mObservers) {
				listener.onSensorChanged(event);
			}
		}
	}

	/*
	 * LocationListener methods.
	 */

	public void onLocationChanged(Location location) {
		/*
		 * We can safely assume that we only need one declination angle for the
		 * duration of the application runtime. Most likely, the user will not
		 * travel such great distances in reality mode.
		 */
		if (!mHasDeclination) {
			float altimeter = 0;

			GeomagneticField field = new GeomagneticField(
					(float) location.getLatitude(),
					(float) location.getLongitude(), altimeter,
					System.currentTimeMillis());

			mDeclination = field.getDeclination();

			mHasDeclination = true;
		}
	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

}
