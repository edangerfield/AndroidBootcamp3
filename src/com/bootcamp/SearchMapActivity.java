package com.bootcamp;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SearchMapActivity extends MapActivity{

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    
		//set the layout
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_view);
	    
	    //add zoom controls
	    MapView mapView = (MapView)findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    
	    MapController mc = mapView.getController();
	    mc.setZoom(12);
	    
	    //obtain params stored in intent
	    Intent intent = getIntent();
	    String latitude = intent.getStringExtra("latitude");
	    String longitude = intent.getStringExtra("longitude");
	    String city = intent.getStringExtra("city");
	    //String zipcodefull = intent.getStringExtra("zipcodefull");
	    String zipcode = intent.getStringExtra("zipcode");
	    String locationid = intent.getStringExtra("locationid");
	    
	    if (coordinatesAvailable(latitude,longitude)) {
	    	//lat & long coordinates available
		    Float latFloat = Float.parseFloat(latitude);
		    Float longFloat = Float.parseFloat(longitude);		    
		    
		    List<Overlay> mapOverlays = mapView.getOverlays();
		    Drawable drawable = this.getResources().getDrawable(R.drawable.location_place);
		    MapItemizedOverlay mapItemizedOverlay = new MapItemizedOverlay(drawable,this);
		    
		    GeoPoint point = new GeoPoint((int)(latFloat * 1E6), (int)(longFloat * 1E6));
		    mc.setCenter(point);
		    OverlayItem item = new OverlayItem(point,zipcode,locationid);
		    mapItemizedOverlay.addOverlayItem(item);
		    mapOverlays.add(mapItemizedOverlay);
	    } else {
	    	//lat/long coordinates not available for passed in locale
	    	String msg = "Latitude and Longitude coordinates are not available for selected local: " + city;
	    	Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	    }
	    	
	}
	
	
	private boolean coordinatesAvailable(String latitude, String longitude) {
		if (latitude != null && !latitude.equals("") && longitude !=null && !longitude.equals(""))
			return true;
		else
			return false;
	}
}
