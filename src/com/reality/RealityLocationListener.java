package com.reality;

import java.util.HashSet;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class RealityLocationListener implements LocationListener {

	public static final String TAG = "RealityLocationListener";

	private Location mDeviceLocation = null;
	private HashSet<LocationListener> mObservers;
	private HashSet<LocationListener> mStatusObservers;

	public RealityLocationListener() {
		mObservers = new HashSet<LocationListener>();
		mStatusObservers = new HashSet<LocationListener>();
	}

	public void registerForUpdates(LocationListener observer) {
		mObservers.add(observer);

		if (mDeviceLocation != null) {
			observer.onLocationChanged(mDeviceLocation);
		}
	}

	public void registerForStatusUpdates(LocationListener observer) {
		mStatusObservers.add(observer);
	}

	public void deregister(LocationListener observer) {
		mObservers.remove(observer);
	}

	/**
	 * Returns the last known current location.
	 * 
	 * @return location
	 */
	public Location getCurrentLocation() {
		return mDeviceLocation;
	}

	public void onLocationChanged(Location location) {
		mDeviceLocation = location;

		Log.d(RealityActivity.TAG, "onLocationChanged: " + location);

		/*
		 * Broadcast to the observers.
		 */
		for (LocationListener listener : mObservers) {
			listener.onLocationChanged(location);
		}
	}

	public void onProviderDisabled(String provider) {

	}

	public void onProviderEnabled(String provider) {

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(RealityActivity.TAG,
				"onStatusChanged() " + " - Using "
						+ extras.getInt("satellites") + " satellites");
		/*
		 * Broadcast to the observers.
		 */
		if (mStatusObservers.size() > 0) {
			for (LocationListener listener : mStatusObservers) {
				listener.onStatusChanged(provider, status, extras);
			}
		}
	}

}
