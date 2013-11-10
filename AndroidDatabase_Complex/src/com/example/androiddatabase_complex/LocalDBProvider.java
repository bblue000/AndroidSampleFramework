package com.example.androiddatabase_complex;

import org.ixming.android.model.provider.BaseDBProvider;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDBProvider extends BaseDBProvider {

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
	
	// database name--base
	public static final String DATABASE_NAME = "test.db";
	
	public static final String AUTHORITY = "com.example.androiddatabase_complex";
	
	@Override
	protected SQLiteOpenHelper provideSQLiteOpenHelper(Context context) {
		return new LocalDBHelper(context, getCurrentVersion());
	}

	@Override
	protected String getAuthority() {
		return AUTHORITY;
	}
	
	/**
	 * get current <code>database version</code>;
	 * updating along with application's version
	 */
	public static int getCurrentVersion() {
		return VERSION_1_2;
	}

}
