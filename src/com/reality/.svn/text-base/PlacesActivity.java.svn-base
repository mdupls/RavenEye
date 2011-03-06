package com.reality;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class PlacesActivity extends Activity {

	private ViewGroup mLoadingPanel;
	private TextView mLoadingText;

	private Place mPlace = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.places_activity);

		mLoadingPanel = (ViewGroup) findViewById(R.id.hidden_message);
		mLoadingText = (TextView) findViewById(R.id.hidden_message_text);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Place place = null;
			try {
				place = (Place) extras.getSerializable(Place.class.toString());

				mPlace = place;
			} catch (ClassCastException e) {
				finish();
				return;
			}

			if (place != null) {
				TextView v;
				ImageView iv;

				v = (TextView) findViewById(R.id.name);
				v.setText(place.name);
				v = (TextView) findViewById(R.id.description);
				v.setText(place.description);
				v = (TextView) findViewById(R.id.building_code);
				v.setText(place.buildingCode);
				v = (TextView) findViewById(R.id.direction);
				v.setText("unknown");
				v = (TextView) findViewById(R.id.reviews);
				RatingBar rating = (RatingBar) findViewById(R.id.rating);

				Drawable picture = place.getImageResource();
				if (picture == null) {
					try {
						picture = getResources().getDrawable(
								place.getImageResourceId());
					} catch (Resources.NotFoundException e) {
						picture = getResources().getDrawable(
								R.drawable.ic_menu_gallery);
					}
				}

				iv = (ImageView) findViewById(R.id.picture);
				iv.setImageDrawable(picture);
			}
		}

		showLoadingPanel(null);
	}

	private void showLoadingPanel(CharSequence text) {
		int animResource, visibility;
		if (text != null) {
			animResource = R.anim.top_slide_down;
			visibility = View.VISIBLE;

			mLoadingText.setText(text);
		} else {
			animResource = R.anim.top_slide_up;
			visibility = View.GONE;
		}

		// If the panel's current visibility is the same as the desired
		// visibility, return and do not show the translate animation.
		if (mLoadingPanel.getVisibility() == visibility) {
			return;
		}

		// Start the translate animation.
		// Animation anim = AnimationUtils.loadAnimation(this, animResource);
		// mLoadingPanel.startAnimation(anim);
		mLoadingPanel.setVisibility(visibility); // force persistence on the
													// translation
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.places, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.directory:
			onDirectoryClick(null);
			return true;
		case R.id.gallery:
			onPictureClick(null);
			return true;
		case R.id.rate:
			onRateClick(null);
			return true;
		case R.id.review:
			onWriteReviewClick(null);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onDirectoryClick(View v) {
		this.startActivity(new Intent(this, PlacesListActivity.class));
	}

	public void onViewRealityClick(View v) {
		Intent intent = new Intent(this, RealityActivity.class);
		intent.putExtra(Place.class.toString(), new Object[] { mPlace });

		this.startActivity(intent);
	}

	public void onViewMapClick(View v) {
		final Place[] list = new Place[] { mPlace };

		Intent intent = new Intent(this, NavigationMapActivity.class);
		intent.putExtra(Place.class.toString(), list);

		this.startActivity(intent);
	}

	public void onGetDirectionsClick(View v) {

	}

	public void onAddToFavouritesClick(View v) {

	}

	public void onRateClick(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(LayoutInflater.from(this).inflate(R.layout.rate_dialog,
				null));
		builder.setTitle("Rate this place")
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						})
				.setPositiveButton("Rate",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void onWriteReviewClick(View v) {
		Intent intent = new Intent(this, ReviewListActivity.class);
		intent.putExtra(Place.class.toString(), mPlace);

		this.startActivity(intent);
	}

	public void onPictureClick(View v) {
		Intent intent = new Intent(this, PictureFrameActivity.class);
		intent.putExtra(Place.class.toString(), mPlace);

		this.startActivity(intent);
	}

}
