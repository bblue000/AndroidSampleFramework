package com.example.androiddatabase_complex;

import org.ixming.android.sqlite.DBManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * a example of SQLiteOpenHelper
 */
class LocalDBHelper extends SQLiteOpenHelper {
	final String TAG = LocalDBHelper.class.getSimpleName();
	
	protected LocalDBHelper(Context context, int dataBaseVersion) {
		super(context, LocalDBProvider.DATABASE_NAME, null, dataBaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		modifyAllTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			if (oldVersion < LocalDBProvider.VERSION_1_0) {
				modifyTables_V1_0(db);
				oldVersion = LocalDBProvider.VERSION_1_0;
			}
			if (oldVersion < LocalDBProvider.VERSION_1_1) {
				// do 1.0-->1.1
				modifyTables_V1_1(db);
				oldVersion = LocalDBProvider.VERSION_1_1;
			}
			if (oldVersion < LocalDBProvider.VERSION_1_2) {
				// do 1.1-->1.2
				oldVersion = LocalDBProvider.VERSION_1_2;
			}
			if (oldVersion < LocalDBProvider.VERSION_1_3) {
				// do 1.2-->1.3
				oldVersion = LocalDBProvider.VERSION_1_3;
			}
		}
	}

	private void modifyAllTables(SQLiteDatabase db) {
		modifyTables_V1_0(db);
		modifyTables_V1_1(db);
	}
	
	// =====================================================
	private void modifyTables_V1_0(SQLiteDatabase db){
		String sql = DBManager.getTableCreation(TestBean.class);
		Log.d(TAG, "TestBean: " + sql);
		db.execSQL(sql);
		
		sql = DBManager.getTableCreation(TestBean2.class);
		Log.d(TAG, "TestBean2: " + sql);
		db.execSQL(sql);
	}
	
	private void modifyTables_V1_1(SQLiteDatabase db){
	}
}
