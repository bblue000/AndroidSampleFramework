package org.ixming.android.model.provider;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.ixming.android.utils.FrameworkLog;
import org.ixming.framework.annotation.TemporarilyDone;
import org.ixming.framework.annotation.UncertainState;


import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

/**
 * this is a basic class to deal with database;
 * it doesn't support Uri as complex as “content://xxx/#/xxx/#”
 * @author Yin Yong
 * @version 1.0
 */
@TemporarilyDone
public abstract class BaseDBProvider extends ContentProvider {
	public static final String TAG = BaseDBProvider.class.getSimpleName();

	
	/** provide a new subclass of SQLiteOpenHelper, this is only called in onCreate()*/
	protected abstract SQLiteOpenHelper provideSQLiteOpenHelper(Context context);
	/** get the database authority*/
	protected abstract String getAuthority();
	
	private final String SELECTION_BY_ID = BaseColumns._ID + " = ? ";
	
	private final int URI_MATCHER_TABLE = 0;
	private final int URI_MATCHER_ID = 1;
	
	// inner properties
	private String mAuthority;
	private UriMatcher mUriMatcher;
	private SQLiteOpenHelper mSQLiteOpenHelper;
	private SQLiteDatabase db;
	private Context mContext;
	@Override
	public final boolean onCreate() {
		FrameworkLog.i(TAG, "DBProviderBase onCreate()");
		mContext = getContext();
		mAuthority = getAuthority();
		if (TextUtils.isEmpty(mAuthority)) {
			return false;
		}
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(mAuthority, "*", URI_MATCHER_TABLE);
		mUriMatcher.addURI(mAuthority, "*/#", URI_MATCHER_ID);
		
		mSQLiteOpenHelper = provideSQLiteOpenHelper(mContext);
		db = mSQLiteOpenHelper.getWritableDatabase();
		return true;
	}
	
	@Override
	public final Uri insert(Uri uri, ContentValues values) {
		UriArgs uriArgs = createUriArgsFrom(uri);
		long rowID = db.insert(uriArgs.getTableName(), null, values);
		if (rowID > 0) {
			try {
				return ContentUris.withAppendedId(uri, rowID);
			} finally {
				//TODO 通知监听者
				mContext.getContentResolver().notifyChange(uri, null);
			}
		}
		return null;
	}

	@Override
	public final Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		UriArgs uriArgs = createUriArgsFrom(uri);
		if (uriArgs.isById()) {
			// if it is a query-by-id, ignore the basic parameters--selection, selectionArgs
			selection = SELECTION_BY_ID;
			selectionArgs = new String[]{ uriArgs.getIdArgs() };
		}
		return db.query(uriArgs.getTableName(), projection, selection, selectionArgs, null, null, sortOrder);
	}
	
	@Override
	public final int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		UriArgs uriArgs = createUriArgsFrom(uri);
		if (uriArgs.isById()) {
			// if it is a query-by-id, ignore the basic parameters--selection, selectionArgs
			selection = SELECTION_BY_ID;
			selectionArgs = new String[]{ uriArgs.getIdArgs() };
		}
		try {
			return db.update(uriArgs.getTableName(), values, selection, selectionArgs);
		} finally {
			mContext.getContentResolver().notifyChange(
					uri.buildUpon().encodedPath(uriArgs.getTableName()).build(), null);
		}
	}
	
	@Override
	public final int delete(Uri uri, String selection, String[] selectionArgs) {
		UriArgs uriArgs = createUriArgsFrom(uri);
		if (uriArgs.isById()) {
			// if it is a query-by-id, ignore the basic parameters--selection, selectionArgs
			selection = SELECTION_BY_ID;
			selectionArgs = new String[]{ uriArgs.getIdArgs() };
		}
		try {
			return db.delete(uriArgs.getTableName(), selection, selectionArgs);
		} finally {
			mContext.getContentResolver().notifyChange(
					uri.buildUpon().encodedPath(uriArgs.getTableName()).build(), null);
		}
	}
	
	protected final SQLiteOpenHelper getSQLiteOpenHelper() {
		return mSQLiteOpenHelper;
	}
	
	protected final SQLiteDatabase getWritableDatabase() {
		return db;
	}
	
	@Override
	public final ContentProviderResult[] applyBatch(
			ArrayList<ContentProviderOperation> operations)
			throws OperationApplicationException {
		// 事务开始
		db.beginTransaction();
		try {
			ContentProviderResult[] results = super.applyBatch(operations);
			// 设置事务标示为successful
			db.setTransactionSuccessful();
			FrameworkLog.i(TAG, "DBProviderBase applyBatch count ----->>" + results.length);
			return results;
		} finally {
			// 结束事务
			if (null != db)
				db.endTransaction();
		}
	}
	
	@Override
	public final String getType(Uri uri) {
		try {
			UriArgs uriArgs = createUriArgsFrom(uri);
			if (uriArgs.isById()) {
				return "vnd.android.cursor.item/" + uriArgs.getTableName();
			} else {
				return "vnd.android.cursor.dir/" + uriArgs.getTableName();
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@UncertainState
	protected final UriArgs createUriArgsFrom(Uri uri) {
		return new WeakReference<UriArgs>(new UriArgs(uri)).get();
	}
	
	/**
	 * 解析出Uri中我们需要的信息
	 * @author Yin Yong
	 * @version 1.0
	 */
	protected final class UriArgs {
		private String mTableName;
		private boolean mIsById;
		private String mIdArgs;
		
		private UriArgs(Uri uri) {
			checkUriSecurity(uri);
			List<String> pathSegments = uri.getPathSegments();
			if (null == pathSegments || pathSegments.isEmpty()) {
				return;
			}
			mTableName = pathSegments.get(0);
			if (pathSegments.size() < 2) {
				return ;
			}
			mIsById = checkIsById(uri);
			if (mIsById) {
				mIdArgs = pathSegments.get(1);
			}
		}
		
		public String getTableName() {
			return mTableName;
		}
		
		public boolean isById() {
			return mIsById;
		}
		
		public String getIdArgs() {
			return mIdArgs;
		}
		
		private void checkUriSecurity(Uri uri) {
			if (null == uri) {
				throw new NullPointerException("uri is null!");
			}
			if (!mAuthority.equals(uri.getAuthority())) {
				throw new UnsupportedOperationException("uri's authority is ["
						+ uri.getAuthority() + "], which is unvalid!");
			}
			List<String> pathSegments = uri.getPathSegments();
			if (null == pathSegments || pathSegments.isEmpty()) {
				throw new UnsupportedOperationException("uri is [" + uri.toString() + "], "
						+ "its pathSegment is null, which is unvalid!");
			}
		}
		
		private boolean checkIsById(Uri uri) {
			if (null == uri) {
				return false;
			}
			int match = mUriMatcher.match(uri);
			return URI_MATCHER_ID == match;
		}
	}
	
	
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
			FrameworkLog.e(TAG, "DBProviderBase dropTable Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void dropTables(SQLiteDatabase db, String[] tableNames) {
		if (null == tableNames || tableNames.length == 0) {
			return;
		}
		db.beginTransaction();
		try {
			for (String tabName : tableNames) {
				if (!TextUtils.isEmpty(tabName))
					db.execSQL(String.format("DROP TABLE IF EXISTS %s;", tabName));
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			FrameworkLog.e(TAG, "DBProviderBase dropTables Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// 结束事务
			if (null != db)
				db.endTransaction();
		}
	}
}
