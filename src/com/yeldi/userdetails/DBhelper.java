package com.yeldi.userdetails;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

public class DBhelper extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/com.yeldi.userdetails/databases/";

	private String dbname;
	private String tblname;
	private SQLiteDatabase db;
	private Context myContext;

	public DBhelper(Context ctx, String dbname) {
		super(ctx, dbname, null, 2);
		this.myContext = ctx;
		this.dbname = dbname;

	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getTblname() {
		return tblname;
	}

	public void setTblname(String tblname) {
		this.tblname = tblname;
	}

	public void insertSQL(String tbl, String columns, String content) {
		String sql = "insert into " + tbl + "(" + columns + ") values("
				+ content + ")";

		db.execSQL(sql);
	}

	public void updateSQL(String sql) {

		db.execSQL(sql);
	}

	public void deleteSQL(String tbl, String where, boolean all) {
		String sql;
		if (all)
			sql = "delete from " + tbl;
		else
			sql = "delete from " + tbl + " where " + where;
		db.execSQL(sql);

	}

	public Cursor selectSQL(String sql) {

		Cursor cs = db.rawQuery(sql, null);

		return cs;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	public void closeDB() {
		this.db.close();
	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + dbname;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}
	public ArrayList<User_help> getRowbyQty() {
		ArrayList<User_help> vBo = new ArrayList<User_help>();
		User_help data = null;

		Cursor c = db.query(true, "userdetails", null, null, null, null, null,
				null, null);
		if (c.moveToFirst()) {
			do {
				data = new User_help();
				data.setUsername(c.getString(c.getColumnIndex("UserName")));
				data.setPwd(c.getString(c.getColumnIndex("Password")));
				
				Log.v("=getRowbyQty=", "===" + data.getUsername());
				Log.v("=getRowbyQty=", "===" + data.getPwd());
				vBo.add(data);
			} while (c.moveToNext());
		}
		return vBo;
	}
	
	public void createDataBase() {
		boolean dbExist = checkDataBase();
		if (dbExist) {
			// do nothing - database already exist
		} else {
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.

			getWritableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + dbname;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(dbname);
		// Path to the just created empty db
		String outFileName = DB_PATH + dbname;
		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

}
