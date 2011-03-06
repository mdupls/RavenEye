package com.reality;

import java.util.ArrayList;
import java.util.Date;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ReviewListActivity extends ListActivity {

	private LinearLayout mLoadingPanel;
	private TextView mLoadingText;
	private EditText mReviewText;
	private Button mSubmitButton;

	private Place mPlace;
	private ArrayList<Review> mReviews;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.review_list_activity);

		mLoadingPanel = (LinearLayout) findViewById(R.id.hidden_message);
		mLoadingText = (TextView) findViewById(R.id.hidden_message_text);
		mReviewText = (EditText) findViewById(R.id.review_text);
		mSubmitButton = (Button) findViewById(R.id.submit);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try {
				mPlace = (Place) extras.getSerializable(Place.class.toString());
				if (mPlace != null) {
					mReviews = mPlace.getReviews(4);

					setTitle(getTitle() + " - " + mPlace.name);
				}
			} catch (ClassCastException e) {
				finish();
				return;
			}
		}

		if (mReviews == null) {
			mReviews = new ArrayList<Review>();
		}

		mAdapter.addSection("Recent", new ReviewsAdapter(this,
				R.layout.review_list_item, mReviews));

		setListAdapter(mAdapter);

		final ListView listView = getListView();
		listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

		registerForContextMenu(listView);

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

	private SectionedAdapter mAdapter = new SectionedAdapter() {
		protected View getHeaderView(String caption, int index,
				View convertView, ViewGroup parent) {
			TextView result = (TextView) convertView;

			if (convertView == null) {
				result = (TextView) getLayoutInflater().inflate(
						R.layout.list_header, null);
			}

			result.setText(caption);

			return (result);
		}
	};

	private class ReviewsAdapter extends ArrayAdapter<Review> {

		private LayoutInflater mInflater;

		public ReviewsAdapter(Context context, int textViewResourceId,
				ArrayList<Review> mItems) {
			super(context, textViewResourceId, mItems);

			mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public ArrayList<Review> getItems() {
			return mReviews;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			String name = null;
			String content = null;
			String date = null;

			Review review = null;

			synchronized (this) {
				if (position < mReviews.size()) {
					review = mReviews.get(position);
				}
			}

			if (review != null) {
				name = review.title;
				content = review.content;
				date = review.date;
			}

			if (v == null) {
				v = mInflater.inflate(R.layout.review_list_item, null);
			}

			TextView titleText = (TextView) v.findViewById(R.id.title);
			TextView contentText = (TextView) v.findViewById(R.id.content);
			TextView dateText = (TextView) v.findViewById(R.id.date);

			if (titleText != null)
				titleText.setText(name);
			if (contentText != null)
				contentText.setText(content);
			if (dateText != null)
				dateText.setText(date);

			return v;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId() == android.R.id.list) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

			Review r = (Review) mAdapter.getItem(info.position);

			menu.setHeaderTitle(r.title);
			super.onCreateContextMenu(menu, v, menuInfo);
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.review_context_menu, menu);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		Review review = (Review) mAdapter.getItem(info.position);
		switch (item.getItemId()) {
		case R.id.report_review:
			onReportClick(review);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	public void onReportClick(final Review review) {
		runOnUiThread(new Runnable() {

			public void run() {
				synchronized (this) {
					mReviews.remove(review);
				}

				mAdapter.notifyDataSetChanged();
			}

		});
	}

	public void onSubmitClick(final View v) {
		runOnUiThread(new Runnable() {

			public void run() {
				String reviewBody = mReviewText.getText().toString().trim();
				if (reviewBody.length() > 0) {
					Review review = new Review("Review X", reviewBody,
							new Date().toString());

					synchronized (this) {
						mReviews.add(review);
						mPlace.addReview(review);
					}

					mAdapter.notifyDataSetChanged();
					mReviewText.setText("");
					mReviewText.clearFocus();
					// mSubmitButton.setEnabled(false);
					// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(mReviewText.getWindowToken(), 0);


					// listView.scrollTo(0, listView..getBaseline());
				}
			}

		});
	}
}
