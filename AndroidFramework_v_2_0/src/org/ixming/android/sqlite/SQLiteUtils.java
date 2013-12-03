package org.ixming.android.sqlite;

import org.ixming.android.utils.FrameworkLog;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

public class SQLiteUtils {

	static final String TAG = SQLiteUtils.class.getSimpleName();
	
	private SQLiteUtils() { }

	// utilities
	public static boolean isTableExisted(SQLiteDatabase db, String tableName) {
		boolean flag = false;
		Cursor c = null;
		if (TextUtils.isEmpty(tableName)) {
			return flag;
		}
		try {
			c = db.query(" sqlite_master ", new String[] { "name" },
					" type='table' AND name = ? ", new String[] { tableName },
					null, null, null);
			if (null != c) {
				flag = c.getCount() > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			if (null != c) {
				c.close();
			}
			FrameworkLog.i(TAG, tableName + " isTableExisted " + flag);
		}
		return flag;
	}

	public static void dropTable(SQLiteDatabase db, String tableName) {
		if (TextUtils.isEmpty(tableName)) {
			return;
		}
		try {
			db.execSQL(String.format("DROP TABLE IF EXISTS %s;", tableName));
		} catch (Exception e) {
			FrameworkLog.e(TAG, "dropTable Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void dropTables(SQLiteDatabase db, String[] tableNames) {
		if (null == tableNames || tableNames.length == 0) {
			return;
		}
		db.beginTransaction();
		try {
			final String ORIGINAL_SQL = "DROP TABLE IF EXISTS %s;";
			for (String tabName : tableNames) {
				if (!TextUtils.isEmpty(tabName))
					db.execSQL(String.format(ORIGINAL_SQL, tabName));
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			FrameworkLog.e(TAG, "dropTables Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 结束事务
			if (null != db)
				db.endTransaction();
		}
	}

}
