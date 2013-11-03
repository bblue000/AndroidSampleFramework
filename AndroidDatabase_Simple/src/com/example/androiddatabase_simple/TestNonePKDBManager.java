package com.example.androiddatabase_simple;

import org.ixming.android.sqlite.BaseNonePKDBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TestNonePKDBManager extends BaseNonePKDBManager<TestNonePKBean> {

	private static TestNonePKDBManager mInstance;
	public static TestNonePKDBManager getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new TestNonePKDBManager(context);
		}
		return mInstance;
	}
	
	protected TestNonePKDBManager(Context context) {
		super(context);
	}

	@Override
	protected String[] provideColumnArray() {
		return new String[]{ "col1", "col2", "col3" };
	}

	@Override
	protected TestNonePKBean createFromCursor(Cursor cursor) {
		TestNonePKBean bean = new TestNonePKBean();
		bean.col1 = cursor.getString(cursor.getColumnIndex("col1"));
		bean.col2 = cursor.getLong(cursor.getColumnIndex("col2"));
		bean.col3 = cursor.getFloat(cursor.getColumnIndex("col3"));
		return bean;
	}

	@Override
	protected void createFromBean(TestNonePKBean t, ContentValues values) {
		values.put("col1", t.col1);
		values.put("col2", t.col2);
		values.put("col3", t.col3);
	}

	@Override
	protected String provideAuthority() {
		return DBTable.AUTHORITY;
	}

	@Override
	protected String provideTableName() {
		return "test";
	}
}
