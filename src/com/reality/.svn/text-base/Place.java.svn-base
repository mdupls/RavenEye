package com.reality;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;

public class Place implements Comparable<Place>, Serializable {

	public final String name;
	public final String description;
	public final String buildingCode;
	public final Coordinate coordinate;

	public transient float bearing = -1;
	public transient float distance = -1;

	private String mLastKnownDistanceString = "";
	private float mLastKnownDistance = -1;

	private transient Drawable mImageResource = null;
	private int mImageResourceId = -1;

	private ArrayList<Review> mReviews = null;

	public Place(String name, String description, String buildingCode,
			Coordinate coordinate) {
		this.name = name;
		this.description = description;
		this.buildingCode = buildingCode;
		this.coordinate = coordinate;

		addReview(new Review("Review 1", "Lorem ipsum...", "4 Sep 2010"));
		addReview(new Review("Review 2", "Dolar sit...", "20 Sep 2010"));
		addReview(new Review("Review 3", "Dolar sit amor...", "1 Jan 2011"));
	}

	public void addReviews(List reviews) {
		if (mReviews == null) {
			mReviews = new ArrayList<Review>();
		}
		mReviews.addAll(reviews);
	}

	public void addReview(Review review) {
		if (mReviews == null) {
			mReviews = new ArrayList<Review>();
		}
		mReviews.add(review);
	}

	/**
	 * Return the first @number of reviews.
	 * 
	 * @param number
	 * @return
	 */
	public ArrayList<Review> getReviews(int number) {
		if (mReviews != null && number > 0) {
			int size = mReviews.size();
			if (number > size) {
				number = size;
			}
			try {
				// Return a copy of the reviews.
				return new ArrayList<Review>(mReviews.subList(0, number));
			} catch (IndexOutOfBoundsException e) {

			}
		}
		return null;
	}

	public Place setImageResource(Drawable resource) {
		mImageResource = resource;
		return this;
	}

	public Drawable getImageResource() {
		return mImageResource;
	}

	public Place setImageResourceId(int resourceId) {
		mImageResourceId = resourceId;
		return this;
	}

	public int getImageResourceId() {
		return mImageResourceId;
	}

	public String getLastKnownDistanceString() {
		return mLastKnownDistanceString;
	}

	public float getLastKnownDistance() {
		return mLastKnownDistance;
	}

	public void updateWithLocation(Location location) {
		if (coordinate == null) {
			return;
		}

		float[] actualDistance = new float[1];
		Location.distanceBetween(location.getLatitude(),
				location.getLongitude(), coordinate.latitude,
				coordinate.longitude, actualDistance);

		String units;

		float distance = Math.round(actualDistance[0]);

		if (distance < 1000) {
			units = (int) distance + "m";
		} else {
			units = ((int) (distance / 100) / 10) + "km";
		}

		synchronized (this) {
			mLastKnownDistance = distance;
			mLastKnownDistanceString = units;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Place) {
			if (name.equals(((Place) object).name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public int compareTo(Place place) {
		boolean hasDistance = mLastKnownDistance >= 0;
		boolean otherHasDistance = place.mLastKnownDistance >= 0;
		if (!hasDistance) {
			if (otherHasDistance) {
				return 1;
			}
		} else if (!otherHasDistance
				|| mLastKnownDistance < place.mLastKnownDistance) {
			return -1;
		} else if (mLastKnownDistance > place.mLastKnownDistance) {
			return 1;
		}

		return name.compareTo(place.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
