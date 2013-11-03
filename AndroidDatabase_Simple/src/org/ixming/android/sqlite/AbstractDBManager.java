package org.ixming.android.sqlite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

abstract class AbstractDBManager<T extends BaseSQLiteModel> {
	
	final String TAG = "DBManager";
	
	protected final ContentResolver mContentResolver;
	protected final Uri mTableBaseUri;
	protected final String mAuthority;
	protected final String mTableName;
	protected final String[] mProjection;
	protected AbstractDBManager(Context context) {
		mContentResolver = context.getContentResolver();
		mAuthority = provideAuthority();
		mTableName = provideTableName();
		mTableBaseUri = Uri.parse("content://" + mAuthority).buildUpon().appendPath(mTableName).build();
		mProjection = provideColumnArray();
	}
	
	/**
	 * 提供该DBManager的authority
	 */
	protected abstract String provideAuthority() ;
	
	/**
	 * 提供该DBManager的authority
	 */
	protected abstract String provideTableName() ;
	
	/**
	 * 提供该DBManager的authority
	 */
	protected abstract String[] provideColumnArray() ;
	
	
	protected abstract T createFromCursor(Cursor cursor);
	
	/**
	 * @param t a database bean
	 * @param values a non-null ContentValues object
	 */
	protected abstract void createFromBean(T t, ContentValues values) ;
	protected final ContentValues createFromBean(T t) {
		if (null == t) {
			return null;
		}
		ContentValues values = new ContentValues();
		createFromBean(t, values);
		return values;
	}
}
