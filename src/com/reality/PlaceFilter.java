package com.reality;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.util.Log;
import com.reality.R;

public class PlaceFilter {

	/**
	 * Find all places that are within the viewpoint and within a certain
	 * distance.
	 * 
	 * @param places
	 * @param location
	 * @param orientation
	 * @param radius
	 * @return
	 */
	public static List<Place> findPlacesInSight(List<Place> places,
			Location location, float orientation, int radius) {
		ArrayList<Place> foundPlaces = new ArrayList<Place>();

		for (Place p : places) {
			if (isInSight(p, location, orientation)) {
				foundPlaces.add(p);
			}
		}

		return foundPlaces;
	}

	public static boolean isInSight(Place place, Location location,
			float orientation) {
//		float bearing = place.location.bearingTo(location);
//		float distanceTo = place.location.distanceTo(location);
//
//		if ((bearing - orientation) < 22.5) {
//			return true;
//		}

		return false;
	}

}
