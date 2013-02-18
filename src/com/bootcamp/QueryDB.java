package com.bootcamp;

import com.bootcamp.providers.MyLocalContentProvider;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class QueryDB extends AsyncTask<String, Void, String> {

	private SearchableActivity searchableactivity; 
	private Cursor cursor;
	
	//Constructor
	public QueryDB(Context context) {
		searchableactivity = (SearchableActivity)context;
	}
	
	@Override
	protected void onPreExecute() {
		searchableactivity.dialog = ProgressDialog.show(searchableactivity,"","Loading... Please wait.");
	}
	
	
	@Override
	protected String doInBackground(String... params) {

		performQuery(params);
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		searchableactivity.dialog.dismiss();
		populateLayout();
	}	

	
	/*
	 * Purpose: Execute the query using the passed in query parameter.
	 */
	private void performQuery(String... params) {

		Log.d("", "Entering QueryDB.performQuery");
		
		//determine type of query (by zip or by city)
		Uri uriViaQueryParam = null;
		String queryParam = params[0];
		if (queryParam.matches("\\d+"))
			uriViaQueryParam = MyLocalContentProvider.CONTENT_URI_ZIP;
		else
			uriViaQueryParam = MyLocalContentProvider.CONTENT_URI_LOC;
		 				
		ContentResolver resolver = searchableactivity.getContentResolver();
		String[] projection = new String[]{"zipcodes.zipcodefull as _id","zipcodes.zipcode","locations.locationid", "locations.city","locations.state","zipcodes.lat","zipcodes.long"};
		cursor = resolver.query(uriViaQueryParam, projection, null, params, null);														
				
		Log.d("", "Exiting QueryDB.performQuery");
	}
	
	
	/*
	 * Purpose: Display cursor results in SearchableActivity
	 */
	private void populateLayout() {
		
		Log.d("", "Entering QueryDB.populateLayout");
		
		searchableactivity.startManagingCursor(cursor);
		String[] from = new String[] {"_id","city","state"};
		int[] to = new int[]{R.id.Zip,R.id.City,R.id.State};
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(searchableactivity, R.layout.search_results, cursor, from, to);
		searchableactivity.setListAdapter(cursorAdapter);	
		
		Log.d("", "Exiting QueryDB.populateLayout");
	}
	
}
