package com.bootcamp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.bootcamp.util.DatabaseHelper;

public class MyLocalContentProvider extends ContentProvider {

	private DatabaseHelper databaseHelper;

	public static final String AUTHORITY = "com.bootcamp.providers.MyLocalContentProvider"; 
	public static final Uri CONTENT_URI_ZIP = Uri.parse("content://" + AUTHORITY + "/zip" );
	public static final Uri CONTENT_URI_LOC = Uri.parse("content://" + AUTHORITY + "/loc" );
	
	@Override
	public boolean onCreate() {
		
		//instantiate the DB Helper
		databaseHelper = new DatabaseHelper(getContext());
		
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		// open the database.
	    try {
	      databaseHelper.openDataBase();
	    } catch (SQLiteException ex) {
	      throw ex;
	    }
		
	    //fetch the db
	    SQLiteDatabase db = databaseHelper.getMyDataBase();
	    
	    //create the query object
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    
	    //build query
	    queryBuilder.setTables("zipcodes, xrefziploc, locations");
	    queryBuilder.appendWhere("zipcodes.zipcode = xrefziploc.zipcode AND "+
	    						 "xrefziploc.locationid = locations.locationid AND "+
	    						 getLastCondition(uri, selectionArgs));	    
	    	    
	    // execute the query.
	    Cursor cursor = queryBuilder.query(db, projection, null, null, null, null, sortOrder);
	    	    
		return cursor;
	}
	
	
	/*
	 * Purpose: establish last conditional statement for query
	 */
	private String getLastCondition(Uri uri, String[] selectionArgs) {
		
		//TODO: update to support wildcards at later date	    
	    if (uri.equals(CONTENT_URI_LOC)) {
	    	return "upper(locations.city) like upper('" + selectionArgs[0] + "%')";
	    } else {
	    	return "zipcodes.zipcodefull like '" + selectionArgs[0] + "%'";
	    }
	}
	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}	
	
}
