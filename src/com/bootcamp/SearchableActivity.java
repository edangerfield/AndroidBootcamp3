package com.bootcamp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class SearchableActivity extends ListActivity{
	
	protected ProgressDialog dialog;
    private boolean initialCall;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);	    	   
	    setContentView(R.layout.list);
	    initialCall = true;
	    
	    Log.d("", "Exiting SearchableActivity.onCreate");
	}

	protected void onStart() {
		super.onStart();
		
		Log.d("", "Entering SearchableActivity.onStart");
		
		if (initialCall) {
			//get the intent, verify the action and get the query parameter
		    Intent intent = getIntent();
		    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
		    	
		      //execute locale query on separate thread	
		      String queryParam = intent.getStringExtra(SearchManager.QUERY);	      
		      QueryDB queryDB = new QueryDB(this);
		      queryDB.execute(new String[] {queryParam});
		         
		      //set click event for list items
		      this.getListView().setOnItemClickListener(new MapHelper(this));

		    }
		    initialCall=false;
		}
	    Log.d("", "Exiting SearchableActivity.onStart");
	}
	
    protected void onRestart() {
    	super.onRestart();
    	Log.d("", "Exiting SearchableActivity.onRestart");
    }
    protected void onResume() {
    	super.onResume();
    	Log.d("", "Exiting SearchableActivity.onResume");
    }    
    protected void onPause() {
    	super.onPause();
    	Log.d("", "Exiting SearchableActivity.onPause");
    }   
    protected void onStop() {    	
    	super.onStop();
    	Log.d("", "Exiting SearchableActivity.onStop");
    }     
    protected void onDestroy() {
    	super.onDestroy();    	
    	Log.d("", "Exiting SearchableActivity.onDestroy");   
    }
    
}
