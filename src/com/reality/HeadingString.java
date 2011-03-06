package com.reality;

public class HeadingString {

	/*
	 * Use a slight optimization here to do a lookup of the corresponding
	 * heading label given a degree value. This lookup operation will now take
	 * O(1) and save a few cycles when used in the onSensorChanged() method of a
	 * SensorEventListener object.
	 */
	public static String[] getHeadingNames() {
		String[] names = new String[360];

		String north = "N";
		String northEast = "NE";
		String east = "E";
		String southEast = "SE";
		String south = "S";
		String southWest = "SW";
		String west = "W";
		String northWest = "NW";

		int len = names.length;
		for (int direction = 0; direction < len; ++direction) {
			if (direction > 337 || direction <= 22) {
				names[direction] = north;
			} else if (direction <= 77) {
				names[direction] = northEast;
			} else if (direction <= 112) {
				names[direction] = east;
			} else if (direction <= 157) {
				names[direction] = southEast;
			} else if (direction <= 202) {
				names[direction] = south;
			} else if (direction <= 247) {
				names[direction] = southWest;
			} else if (direction <= 292) {
				names[direction] = west;
			} else {
				names[direction] = northWest;
			}
		}

		return names;
	}
}
