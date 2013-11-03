package com.example.androiddatabase_simple;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * a example of SQLiteOpenHelper
 */
class LocalDBHelper extends SQLiteOpenHelper {
	final String TAG = LocalDBHelper.class.getSimpleName();
	
	public LocalDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public LocalDBHelper(Context context, int dataBaseVersion) {
		super(context, DBTable.DATABASE_NAME, null, dataBaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		modifyAllTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			if (oldVersion < DBTable.VERSION_1_0) {
				modifyTables_V1_0(db);
				oldVersion = DBTable.VERSION_1_0;
			}
			if (oldVersion < DBTable.VERSION_1_1) {
				// do 1.0-->1.1
				modifyTables_V1_1(db);
				oldVersion = DBTable.VERSION_1_1;
			}
			if (oldVersion < DBTable.VERSION_1_2) {
				// do 1.1-->1.2
				oldVersion = DBTable.VERSION_1_2;
			}
			if (oldVersion < DBTable.VERSION_1_3) {
				// do 1.2-->1.3
				oldVersion = DBTable.VERSION_1_3;
			}
		}
	}

	private void modifyAllTables(SQLiteDatabase db) {
		modifyTables_V1_0(db);
		modifyTables_V1_1(db);
	}
	
	// =====================================================
	private void modifyTables_V1_0(SQLiteDatabase db){
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE test(")
			.append("col1 TEXT,")
			.append("col2 LONG,")
			.append("col3 REAL")
			.append(");");
		db.execSQL(sb.toString());
		
	}
	
	private void modifyTables_V1_1(SQLiteDatabase db){
		StringBuffer sb = new StringBuffer();
		sb.append("CREATE TABLE test2(")
			.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,")
			.append("col1 TEXT,")
			.append("col2 LONG")
			.append(");");
		db.execSQL(sb.toString());
	}
}
