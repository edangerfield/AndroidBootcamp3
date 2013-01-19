package com.bootcamp;

import java.io.IOException;
import java.util.List;

import com.bootcamp.util.DatabaseHelper;

import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.provider.Settings;

public class InitSearchActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d("", "Entering InitSearchActivity.onCreate");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
             
        if (!IsInAirplaneModeOn())  {
        	setupDB();
        } else {
        	new AlertDialog.Builder(this)
    		.setTitle("Alert!")
    		.setMessage("Network unavailable.  Please disable airplane mode.")
    		.setNeutralButton("Ok",
    				new DialogInterface.OnClickListener() {
    						public void onClick(DialogInterface dialog, int which) {}
    				})
    		.show(); 
        }
        
		Log.d("", "Exiting InitSearchActivity.onCreate");
    }
    
    protected void onRestart() {
    	super.onRestart();
    	Log.d("", "Exiting InitSearchActivity.onRestart");
    }
    protected void onResume() {
    	super.onResume();
    	Log.d("", "Exiting InitSearchActivity.onResume");
    }    
    protected void onPause() {
    	super.onPause();
    	Log.d("", "Exiting InitSearchActivity.onPause");
    }   
    protected void onStop() {    	
    	super.onStop();
    	Log.d("", "Exiting InitSearchActivity.onStop");
    }     
    protected void onDestroy() {
    	super.onDestroy();    	
    	Log.d("", "Exiting InitSearchActivity.onDestroy"); 
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	if (!IsInAirplaneModeOn())  {
	    	MenuInflater inflater = getMenuInflater();
	    	inflater.inflate(R.menu.search_menu, menu);
	    	
	    	//get the SearchManager and SearchView 
	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	        
	        //get searchable info (configuration)
	        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
	        if (info == null) {
	        	//unable to retrieve SearchableInfo by component name, try by suggested authority        	
	        	List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
	        	for (SearchableInfo infoItem : searchables) {
	        		if (infoItem.getSuggestAuthority() != null
	                        && infoItem.getSuggestAuthority().startsWith("applications")) {
	                    info = infoItem;
	                }
	        	}
	        }
	        
	        //set the searchable configuration in the SearchView
	        searchView.setSearchableInfo(info);
	        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
    	}
    	
    	return true;
    }
    
    /*
     * Purpose: Load the database
     */
    private void setupDB() {
		//instantiate the DB Helper
    	DatabaseHelper databaseHelper = new DatabaseHelper(this);

		//copy in DB (if not present)	
		try {
			databaseHelper.createDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create database");
		}	
    }
    
    /*
     * Purpose: Return true if airplane mode is enabled
     */
    private boolean IsInAirplaneModeOn() {
    	return Settings.System.getInt(
			    this.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
    }
}