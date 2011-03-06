package com.reality;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

public abstract class SensorView extends View implements SensorEventListener,
		LocationListener {

	protected Location mLocation;

	public SensorView(Context context) {
		this(context, (Location) null);
	}

	public SensorView(Context context, Location location) {
		super(context);

		this.setWillNotDraw(true);

		this.mLocation = location;
	}

	public SensorView(Context context, AttributeSet attr) {
		this(context, attr, null);
	}

	public SensorView(Context context, AttributeSet attr, Location location) {
		super(context, attr);

		this.setWillNotDraw(true);

		this.mLocation = location;
	}
	
	public void onPlacesChanged(List<Place> places) {
		
	}

	/*
	 * Retrieve the location.
	 */

	public boolean hasLocation() {
		return mLocation != null;
	}

	public Location getLocation() {
		return mLocation;
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
		mLocation = location;
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
