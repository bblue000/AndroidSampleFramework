package com.example.androiddatabase_simple;

import org.ixming.android.model.provider.BaseDBProvider;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class LocalDBProvider extends BaseDBProvider {
	final String TAG = LocalDBProvider.class.getSimpleName();
	
	@Override
	protected String getAuthority() {
		return DBTable.AUTHORITY;
	}
	
	@Override
	protected SQLiteOpenHelper createSQLiteOpenHelper(Context context) {
		Log.i(TAG, "enter createSQLiteOpenHelper!");
		return new LocalDBHelper(context, DBTable.getCurrentVersion());
	}

}
