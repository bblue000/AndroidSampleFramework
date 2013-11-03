package com.example.androiddatabase_simple;

import android.net.Uri;

public class DBTable {

	private DBTable() { }
	
	public static final String AUTHORITY = DBTable.class.getPackage().getName();
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	
	// database name--base
	public static final String DATABASE_NAME = "test.db";
	
	// database version manager
	public static final int VERSION_1_0 = 1;
	public static final int VERSION_1_1 = 2;
	public static final int VERSION_1_2 = 3;
	public static final int VERSION_1_3 = 4;
	public static final int VERSION_1_4 = 5;
	public static final int VERSION_1_5 = 6;
	public static final int VERSION_1_6 = 7;
	public static final int VERSION_1_7 = 8;
	public static final int VERSION_1_8 = 9;
	public static final int VERSION_1_9 = 10;
	public static final int VERSION_2_0 = 11;
	
	/**
	 * get current <code>database version</code>;
	 * updating along with application's version
	 */
	public static int getCurrentVersion() {
		return VERSION_1_2;
	}
}
