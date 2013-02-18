package com.bootcamp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MapHelper implements OnItemClickListener{

	private SearchableActivity searchActivity;
	
	public MapHelper(Context context) {
		searchActivity = (SearchableActivity)context;
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Adapter adapter = parent.getAdapter();
		SimpleCursorAdapter simpleCursorAdapter = (SimpleCursorAdapter)adapter;
		Cursor cursor = (Cursor)simpleCursorAdapter.getItem(position);
		
		String latitude = cursor.getString(cursor.getColumnIndex("lat"));
		String longitude = cursor.getString(cursor.getColumnIndex("long"));
		String city = cursor.getString(cursor.getColumnIndex("city"));
		String zipcodefull = cursor.getString(cursor.getColumnIndex("_id"));
		String zipcode = cursor.getString(cursor.getColumnIndex("zipcode"));
		String locationid = cursor.getString(cursor.getColumnIndex("locationid"));
		
		//Toast.makeText(searchActivity.getApplicationContext(), combo, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(searchActivity, SearchMapActivity.class);
		intent.putExtra("latitude",latitude);
		intent.putExtra("longitude",longitude);
		intent.putExtra("city",city);
		intent.putExtra("zipcodefull",zipcodefull);
		intent.putExtra("zipcode",zipcode);
		intent.putExtra("locationid",locationid);
		searchActivity.startActivity(intent);
	}

}
