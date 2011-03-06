package com.reality;

import com.reality.R;
import java.util.ArrayList;
import java.util.List;

public class DataServer {

	private static DataServer mSingleton = null;

	private DataServer() {

	}

	/**
	 * Exposes a singleton instance of this class.
	 * 
	 * @return a singleton instance of this class.
	 */
	public static DataServer getInstance() {
		if (mSingleton == null) {
			mSingleton = new DataServer();
		}
		return mSingleton;
	}

	public synchronized List query(QueryType queryType, String parameters) {
		ArrayList list = null;

		switch (queryType) {
		case PLACE_ITEM:
			break;
		case REVIEW_ITEM:
			break;
		case PLACES_LIST:
			break;
		case REVIEW_LIST:
			break;
		}

		return list;
	}

	public synchronized boolean update(UpdateType updateType, String parameters) {
		return true;
	}

	public enum QueryType {
		PLACE_ITEM, REVIEW_ITEM, PLACES_LIST, REVIEW_LIST
	}

	public enum UpdateType {
		PLACE_ITEM, REVIEW_ITEM, PLACES_LIST, REVIEW_LIST
	}

}
