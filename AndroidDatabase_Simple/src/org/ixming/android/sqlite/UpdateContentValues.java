package org.ixming.android.sqlite;

import org.ixming.android.sqlite.BaseSQLiteModel;

import android.content.ContentValues;

/**
 * if you want to update some data, you'd determine the specific fields you'd modify,
 * use this class to put bean's fields and values to values
 */
public interface UpdateContentValues<Bean extends BaseSQLiteModel> {
	
	/**
	 * determine which columns you want to update
	 * @param t get data fields from bean
	 * @param values ContentValues of (column-value) to put into
	 */
	abstract void setUpdateContentValues(Bean t, ContentValues values) ;
}
