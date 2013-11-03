package com.example.androiddatabase_simple;

import org.ixming.android.sqlite.BaseDBManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TestPKDBManager extends BaseDBManager<TestPKBean> {

	private static TestPKDBManager mInstance;
	public static TestPKDBManager getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new TestPKDBManager(context);
		}
		return mInstance;
	}
	
	protected TestPKDBManager(Context context) {
		super(context);
	}

	@Override
	protected String[] provideColumnArray() {
		return new String[]{ "_id", "col1", "col2" };
	}

	@Override
	protected TestPKBean createFromCursor(Cursor cursor) {
		TestPKBean bean = new TestPKBean();
		bean.setId(cursor.getLong(cursor.getColumnIndex("_id")));
		bean.col1 = cursor.getString(cursor.getColumnIndex("col1"));
		bean.col2 = cursor.getLong(cursor.getColumnIndex("col2"));
		return bean;
	}

	@Override
	protected String provideAuthority() {
		return DBTable.AUTHORITY;
	}

	@Override
	protected String provideTableName() {
		return "test2";
	}

	@Override
	protected void createFromBean(TestPKBean t, ContentValues values) {
		values.put("col1", t.col1);
		values.put("col2", t.col2);
	}
}
