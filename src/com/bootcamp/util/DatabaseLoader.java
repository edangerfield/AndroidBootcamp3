package com.bootcamp.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class DatabaseLoader extends AsyncTask<String, Void, String> {

	private Context context;
	private ProgressDialog dialog;
	private String DB_NAME;
	private String DB_PATH;
	

	public DatabaseLoader(Context context, String DBNAME, String DBPATH) {
		this.context = context;
		this.DB_NAME = DBNAME;
		this.DB_PATH = DBPATH;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context,"","Setting up database... Please wait.");
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		try {
			copyDataBase();
		} catch (IOException e) {
			throw new Error("Error copying database");	
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		dialog.dismiss();
	}
	
	/*
	 * 
	 */
    private void copyDataBase() throws IOException{
        
	    //Open your local db as the input stream
	    InputStream myInput = context.getAssets().open(DB_NAME);
	     
	    //Path to the just created empty db
	    String outFileName = DB_PATH + DB_NAME;
	     
	    //Open the empty db as the output stream
	    OutputStream myOutput = new FileOutputStream(outFileName);
	     
	    //transfer bytes from the inputfile to the outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = myInput.read(buffer))>0){
	    	myOutput.write(buffer, 0, length);
	    }
	     
	    //Close the streams
	    myOutput.flush();
	    myOutput.close();
	    myInput.close();
     
    }
    
}
