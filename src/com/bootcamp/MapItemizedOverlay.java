package com.bootcamp;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay {

	private ArrayList<OverlayItem> overlayList = new ArrayList<OverlayItem>();
	private Context myContext; 
	
	//constructor
	public MapItemizedOverlay(Drawable marker) {
		super(boundCenterBottom(marker));
	}

	//constructor
	public MapItemizedOverlay(Drawable marker, Context context) {
		super(boundCenterBottom(marker));
		myContext = context;
	}
	
	@Override
	protected OverlayItem createItem(int index) {
		return overlayList.get(index);
	}

	@Override
	public int size() {
		return overlayList.size();
	}

	@Override
	protected boolean onTap(int index) {
		
		if (myContext !=null) {
			
			//obtain zipcode and locationid of tapped item
			OverlayItem item = overlayList.get(index);
			String zipcode = item.getTitle();
			String locationid = item.getSnippet();
			
			//start Demographic activity, passing in zipcode
			SearchMapActivity mapActivity = (SearchMapActivity)myContext;			
			Intent intent = new Intent(mapActivity, DemographicActivity.class);
			intent.putExtra("zipcode",zipcode);
			intent.putExtra("locationid",locationid);
			mapActivity.startActivity(intent);
			
		}
		return true;
	}
	
	/*
	 * Purpose: Add the overlay item to the overlay list 
	 */
	public void addOverlayItem(OverlayItem overlayItem) {
		overlayList.add(overlayItem);
		populate();
	}		
	
}
