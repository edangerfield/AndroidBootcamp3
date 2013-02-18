package com.bootcamp;

import com.bootcamp.providers.MyDemographicContentProvider;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

public class DemographicActivity extends ListActivity {

	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		Log.d("", "Entering DemographicActivity.onCreate");
		
		//obtain zipcode
		Intent intent = getIntent();
		String zipcode = intent.getStringExtra("zipcode");
		String locationid = intent.getStringExtra("locationid");
		String[] params = {zipcode,locationid};
		
		//invoke query for demographics
		ContentResolver resolver = this.getContentResolver();
		Cursor cursor = resolver.query(MyDemographicContentProvider.CONTENT_URI, null, null, params, null);
		
		//bind cursor to activity
		this.startManagingCursor(cursor);
		String[] from = new String[] {"population","lat","long","housingunits","income","landarea","waterarea","militaryrestrictioncodes","city","state","county","type","preferred","worldregion","country","locationtext","location"};
		int[] to = new int[]{R.id.population,R.id.latitude, R.id.longitude, R.id.housing,R.id.income,R.id.landarea,R.id.waterarea,R.id.military,R.id.city,R.id.state,R.id.county,R.id.type,R.id.preferred,R.id.worldregion,R.id.country,R.id.locationtext,R.id.location} ;
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.demographics, cursor, from, to);
		this.setListAdapter(cursorAdapter);	
		
		Log.d("", "Exiting DemographicActivity.onCreate");
	}
}
