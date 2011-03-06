package com.reality;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ViewGroup;

public abstract class SensorViewGroup extends ViewGroup implements
		SensorEventListener, LocationListener {

	protected Location mDeviceLocation;

	/*
	 * Bearing from the device location to the location.
	 */
	protected float mBearingToLocation = -1;

	public SensorViewGroup(Context context) {
		super(context);
	}

	public SensorViewGroup(Context context, AttributeSet attr) {
		super(context, attr);
	}

	/**
	 * SensorEventListener methods.
	 */
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	/**
	 * LocationListener methods.
	 */

	public void onLocationChanged(Location location) {
		mDeviceLocation = location;
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
