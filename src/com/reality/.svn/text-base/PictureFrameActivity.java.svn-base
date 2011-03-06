package com.reality;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import com.reality.R;


public class PictureFrameActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.picture_frame_activity);
	    
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Place place = null;
			try {
				place = (Place) extras.getSerializable(Place.class.toString());
				
				setTitle(place.name);
			} catch (ClassCastException e) {
				finish();
				return;
			}

			if (place != null) {
				int picture = place.getImageResourceId();
				
				Gallery g = (Gallery) findViewById(R.id.gallery);
			    g.setAdapter(new ImageAdapter(this, picture));

//			    g.setOnItemClickListener(new OnItemClickListener() {
//			        public void onItemClick(AdapterView parent, View v, int position, long id) {
//
//			        }
//			    });
			}
		}
	}
	
	private class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    private ArrayList<Integer> mImageIds = new ArrayList<Integer>();

	    public ImageAdapter(Context c, int resourceId) {
	        mContext = c;
	        mImageIds.add(resourceId);
	        TypedArray a = obtainStyledAttributes(R.styleable.PictureGallery);
	        mGalleryItemBackground = a.getResourceId(
	                R.styleable.PictureGallery_android_galleryItemBackground, 0);
	        a.recycle();
	    }

	    public int getCount() {
	        return mImageIds.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView i = new ImageView(mContext);

	        i.setImageResource(mImageIds.get(position));
	        i.setScaleType(ImageView.ScaleType.FIT_CENTER);
	        i.setBackgroundResource(mGalleryItemBackground);

	        return i;
	    }
	}

}
