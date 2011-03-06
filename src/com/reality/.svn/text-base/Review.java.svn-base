package com.reality;

import com.reality.R;
import java.io.Serializable;

public class Review implements Serializable {

	public final String title;
	public final String content;
	public final String date;

	public Review(String title, String content, String date) {
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Review) {
			if(((Review)obj).hashCode() == hashCode()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if(title != null) {
			return title.hashCode();
		}
		return -1;
	}
	
}
