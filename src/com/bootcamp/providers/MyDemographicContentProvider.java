package com.bootcamp.providers;

import com.bootcamp.util.DatabaseHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyDemographicContentProvider extends ContentProvider {

	private DatabaseHelper databaseHelper;

	public static final String AUTHORITY = "com.bootcamp.providers.MyDemographicContentProvider"; 
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/demo" );
	

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
	    	    
	    //build query	    
	    //String[] myProjection = {"zipcodefull as _id, population, housingunits, income, landarea, waterarea, militaryrestrictioncodes"};	    
	    String[] myProjection = {"zipcodefull as _id, " +
	    					     " CASE WHEN population='' THEN 'NA' ELSE population END AS population, " +
	    					     " CASE WHEN lat='' THEN 'NA' ELSE lat END AS lat, " +
	    					     " CASE WHEN long='' THEN 'NA' ELSE long END AS long, " +
	    		                 " CASE WHEN housingunits='' THEN 'NA' ELSE housingunits END AS housingunits, " +
	    					     " CASE WHEN income='' THEN 'NA' ELSE income END AS income, " + 
	    		                 " CASE WHEN landarea='' THEN 'NA' ELSE landarea END AS landarea, " +
	    					     " CASE WHEN waterarea='' THEN 'NA' ELSE waterarea END AS waterarea, " + 
	    		                 " CASE WHEN militaryrestrictioncodes='' THEN 'NA' ELSE militaryrestrictioncodes END AS militaryrestrictioncodes,"+
	    		                 " CASE WHEN city='' THEN 'NA' ELSE city END AS city, " +
	    		                 " CASE WHEN state='' THEN 'NA' ELSE state END AS state, " +
	    		                 " CASE WHEN county='' THEN 'NA' ELSE county END AS county, " +
	    		                 " CASE WHEN type='' THEN 'NA' ELSE type END AS type, " +
	    		                 " CASE WHEN preferred='' THEN 'NA' ELSE preferred END AS preferred, " +
	    		                 " CASE WHEN worldregion='' THEN 'NA' ELSE worldregion END AS worldregion, " +
	    		                 " CASE WHEN country='' THEN 'NA' ELSE country END AS country, " +
	    		                 " CASE WHEN locationtext='' THEN 'NA' ELSE locationtext END AS locationtext, " +
	    		                 " CASE WHEN location='' THEN 'NA' ELSE location END AS location " 
	    						};
	    
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
	    //queryBuilder.setTables("zipcodes");
	    //queryBuilder.appendWhere("zipcodefull = '" + selectionArgs[0] + "'"); 	    						 	    						 
	    queryBuilder.setTables("zipcodes, xrefziploc, locations");
	    queryBuilder.appendWhere("zipcodes.zipcode = xrefziploc.zipcode AND "+
	    						 "xrefziploc.locationid = locations.locationid AND "+	
	    						 "xrefziploc.zipcode = '" + selectionArgs[0] + "' AND " +
	    						 "xrefziploc.locationid = '" + selectionArgs[1] + "'" );
	    
	    // execute the query.
	    Cursor cursor = queryBuilder.query(db, myProjection, null, null, null, null, sortOrder);
	    	    
		return cursor;
	    
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}
}
