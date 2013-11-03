package org.ixming.android.sqlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ixming.android.sqlite.BaseSQLiteModel;
import org.ixming.android.utils.FrameworkLog;
import org.ixming.utils.NumberUtil;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public abstract class BaseNonePKDBManager<T extends BaseSQLiteModel> extends AbstractDBManager<T> {
	
	protected BaseNonePKDBManager(Context context) {
		super(context);
	}
	
	// =========================================================================
	// basic database operations
	private final boolean checkUriWithResultRowId(Uri resultUri) {
		if (null == resultUri) {
			return false;
		}
		List<String> pathSet = resultUri.getPathSegments();
		if (null == pathSet || pathSet.size() < 2) {
			FrameworkLog.w(TAG, "checkUriWithResultRowId uri path is null or incorrect");
			return false;
		}
		return NumberUtil.getLong(pathSet.get(1)) > 0L;
	}
	
	// >>>>>>>>>>>insert
	public boolean insertData(T t){
		return insertData(t, false);
	}
	
	public boolean insertData(T t, boolean deleteBeforeInsert){
		if (deleteBeforeInsert) delete(null);
		if (null == t) return false;
		Uri uri = mContentResolver.insert(mTableBaseUri, createFromBean(t));
		return checkUriWithResultRowId(uri);
	}
	
	public <C extends Collection<T>>boolean insertData(C list){
		return insertData(list, false);
	}
	
	public <C extends Collection<T>>boolean insertData(C list, boolean deleteBeforeInsert){
		try {
			// check whether deleting before inserting new items
			if (deleteBeforeInsert) delete(null);
			// check whether the list is null, or an empty one
			if (null == list || list.isEmpty()) return false;
			
			ArrayList<ContentProviderOperation> operations = 
					new ArrayList<ContentProviderOperation>();
			Iterator<T> ite = list.iterator();
			while (ite.hasNext()) {
				T t = ite.next();
				if (null == t) {
					ite.remove();
					continue;
				}
				ContentValues values = new ContentValues();
				createFromBean(t, values);
				operations.add(ContentProviderOperation.newInsert(mTableBaseUri).withValues(values).build());
			}
			ContentProviderResult[] results = mContentResolver.applyBatch(mAuthority, operations);
			FrameworkLog.d(TAG, "insertData<C, boolean> insert size: " + list.size());
			FrameworkLog.d(TAG, "insertData<C, boolean> results.length: " + results.length);
			return results.length == list.size();
		} catch (Exception e) {
			FrameworkLog.e(TAG, "insertData<C, boolean> Exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public <M extends Map<?, T>>boolean insertData(M map){
		if (null == map) {
			return false;
		}
		return insertData(map.values());
	}
	
	
	// >>>>>>>>>>>query
	/**
	 * @param condition null 相当于查询全部。
	 */
	public List<T> queryList(SQLiteCondition condition) {
		Cursor cursor = null;
		try {
			condition = SQLiteCondition.checkNull(condition);
			cursor = mContentResolver.query(mTableBaseUri, mProjection, 
					condition.getWhereClause(), condition.getWhereArgs(), condition.getSortOrder());
			
			if (!cursor.moveToFirst()) return null;
			
			List<T> list = new ArrayList<T>();
			do {
				list.add(createFromCursor(cursor));
			} while (cursor.moveToNext());
			return list;
		} catch (Exception e) {
			FrameworkLog.e(TAG, "<List>query Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if (null != cursor){
				cursor.close();
			}
		}
	}
	
	public T queryOne(SQLiteCondition condition) {
		Cursor cursor = null;
		try {
			condition = SQLiteCondition.checkNull(condition);
			cursor = mContentResolver.query(mTableBaseUri, mProjection, 
					condition.getWhereClause(), condition.getWhereArgs(), condition.getSortOrder());
			if (!cursor.moveToFirst()) return null;
			return createFromCursor(cursor);
		} catch (Exception e) {
			FrameworkLog.e(TAG, "<T>queryOne Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if (null != cursor){
				cursor.close();
			}
		}
	}
	
	public List<T> queryAll(String sortOrder) {
		return queryList(SQLiteCondition.create().setSortOrder(sortOrder));
	}
	
	
	// =============================================================================
	// update
	protected final UpdateContentValues<T> mDefaultUpdateContentValues = new UpdateContentValues<T>() {
		@Override
		public void setUpdateContentValues(T t, ContentValues values) {
			createFromBean(t, values);
		}
	};
	
	/**
	 * 删除单个，此时我们认为在外部直接声称了ContentValues，作为参数传入更好一些，否则有过多冗余。
	 */
	public boolean update(ContentValues values, SQLiteCondition condition) {
		condition = SQLiteCondition.checkNull(condition);
		return 1 == mContentResolver.update(mTableBaseUri, values, condition.getWhereClause(), condition.getWhereArgs());
	}
	
	@Deprecated
	public boolean update(T t, UpdateContentValues<T> updateValues, SQLiteCondition condition) {
		if (null == updateValues) {
			updateValues = mDefaultUpdateContentValues;
		}
		ContentValues values = new ContentValues();
		updateValues.setUpdateContentValues(t, values);
		condition = SQLiteCondition.checkNull(condition);
		return 1 == mContentResolver.update(mTableBaseUri, values, condition.getWhereClause(), condition.getWhereArgs());
	}
	
	/**
	 * @param list
	 * @param updateValues 如果为NULL，则使用默认的UpdateContentValues，即相当于
	 * {@link #createFromBean(BaseSQLiteModel, ContentValues)}
	 * @param definer 如果为NULL，则不予执行
	 */
	public <C extends Collection<T>>boolean updateList(C list, UpdateContentValues<T> updateValues,
			SQLiteConditionDefiner<T> definer) {
		try {
			if (null == list || list.isEmpty()) return false;
			if (null == definer) return false;
			if (null == updateValues) updateValues = mDefaultUpdateContentValues;
			
			ArrayList<ContentProviderOperation> operations = 
					new ArrayList<ContentProviderOperation>();
			Iterator<T> ite = list.iterator();
			SQLiteCondition condition = SQLiteCondition.create();
			ContentValues values = new ContentValues();
			while (ite.hasNext()) {
				T t = ite.next();
				if (null == t) {
					ite.remove();
					continue;
				}
				// 能使用此方式的原因是ContentProviderOperation的withValues的实现是new自己内部的ContentValues对象，
				// 而不是直接使用。
				values.clear();
				updateValues.setUpdateContentValues(t, values);
				definer.config(condition.clear(), t);
				operations.add(ContentProviderOperation.newUpdate(mTableBaseUri).withValues(values)
						.withSelection(condition.getWhereClause(), condition.getWhereArgs()).build());
			}
			ContentProviderResult[] results = mContentResolver.applyBatch(mAuthority, operations);
			FrameworkLog.d(TAG, "update<List, values, definer> list.size(): " + list.size());
			FrameworkLog.d(TAG, "update<List, values, definer> results.length: " + results.length);
			return results.length == list.size();
		} catch (Exception e) {
			FrameworkLog.e(TAG, "update<List, values, definer> Exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	
	// =============================================================================
	// delete
	public int delete(SQLiteCondition condition) {
		condition = SQLiteCondition.checkNull(condition);
		return mContentResolver.delete(mTableBaseUri, condition.getWhereClause(),
				condition.getWhereArgs());
	}
	
	/**
	 * first you should ensure that all items in the collection must be loaded from database,
	 * in other words, it must contains a database _ID field
	 */
	public <C extends Collection<T>>boolean deleteList(C list, SQLiteConditionDefiner<T> definer) {
		try {
			if (null == list || list.isEmpty()) return false;
			if (null == definer) return false;
			ArrayList<ContentProviderOperation> operations = 
					new ArrayList<ContentProviderOperation>();
			SQLiteCondition condition = SQLiteCondition.create();
			Iterator<T> ite = list.iterator();
			while (ite.hasNext()) {
				T t = ite.next();
				if (null == t) {
					ite.remove();
					continue;
				}
				definer.config(condition.clear(), t);
				operations.add(ContentProviderOperation.newDelete(mTableBaseUri)
						.withSelection(condition.getWhereClause(), condition.getWhereArgs()).build());
			}
			ContentProviderResult[] results = mContentResolver.applyBatch(mAuthority, operations);
			FrameworkLog.d(TAG, "delete<List, SQLiteConditionDefiner> list.size(): " + list.size());
			FrameworkLog.d(TAG, "delete<List, SQLiteConditionDefiner> results.length: " + results.length);
			return results.length == list.size();
		} catch (Exception e) {
			FrameworkLog.e(TAG, "delete<List, SQLiteConditionDefiner> Exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
}
