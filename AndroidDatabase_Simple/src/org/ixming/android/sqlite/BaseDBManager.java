package org.ixming.android.sqlite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ixming.android.sqlite.BaseSQLitePKModel;
import org.ixming.utils.NumberUtil;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public abstract class BaseDBManager<T extends BaseSQLitePKModel> extends BaseNonePKDBManager<T>{

	protected BaseDBManager(Context context) {
		super(context);
	}
	
	// =========================================================================
	// basic database operations
	protected final boolean setNewInsertId(Uri resultUri, T t) {
		if (null == resultUri || null == t) return false;
		List<String> pathSet = resultUri.getPathSegments();
		if (null == pathSet || pathSet.size() < 2) {
			Log.w(TAG, "setNewInsertId uri path is null or incorrect");
			return false;
		}
		t.setId(NumberUtil.getLong(pathSet.get(1)));
		return true;
	}
	
	// >>>>>>>>>>>insert
	@Override
	public final boolean insertData(T t, boolean deleteBeforeInsert){
		if (deleteBeforeInsert) delete(null);
		if (null == t) return false;
		Uri uri = mContentResolver.insert(mTableBaseUri, createFromBean(t));
		return setNewInsertId(uri, t);
	}
	
	@Override
	public final <C extends Collection<T>>boolean insertData(C list, boolean deleteBeforeInsert){
		try {
			if (deleteBeforeInsert) delete(null);
			if (null == list || list.isEmpty()) return false;
			
			ArrayList<ContentProviderOperation> operations = 
					new ArrayList<ContentProviderOperation>();
			Iterator<T> ite = list.iterator();
			ContentValues values = new ContentValues();
			while (ite.hasNext()) {
				T t = ite.next();
				if (null == t) {
					ite.remove();
					continue;
				}
				values.clear();
				createFromBean(t, values);
				operations.add(ContentProviderOperation.newInsert(mTableBaseUri).withValues(values).build());
			}
			ContentProviderResult[] results = mContentResolver.applyBatch(mTableBaseUri.getAuthority(), operations);
			ite = list.iterator();
			int index = 0;
			while (ite.hasNext()) {
				T t = ite.next();
				setNewInsertId(results[index++].uri, t);
			}
			Log.d(TAG, "insertData<C> insert size: " + index);
			Log.d(TAG, "insertData<C> results.length: " + results.length);
			return results.length == list.size();
		} catch (Exception e) {
			Log.e(TAG, "insertData<C> Exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	// >>>>>>>>>>>query
	public final T queryById(long id) {
		Cursor cursor = null;
		try {
			cursor = mContentResolver.query(ContentUris.withAppendedId(mTableBaseUri, id),
					mProjection, null, null, null);
			if (!cursor.moveToFirst()) return null;
			Log.d(TAG, "queryById get first record");
			return createFromCursor(cursor);
		} catch (Exception e) {
			Log.e(TAG, "queryById Exception: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if (null != cursor){
				cursor.close();
			}
		}
	}
	
	// =============================================================================
	// update
	/**
	 * first you should ensure that all items in the collection must be loaded from database,
	 * in other words, it must contains a database _ID field
	 * @param list
	 * @param updateValues null to use default, just like invoking {@link #createFromBean}
	 */
	public final <C extends Collection<T>>boolean updateListById(C list, UpdateContentValues<T> updateValues) {
		try {
			if (null == list || list.isEmpty()) return false;
			if (null == updateValues) updateValues = mDefaultUpdateContentValues;
			
			ArrayList<ContentProviderOperation> operations = 
					new ArrayList<ContentProviderOperation>();
			Iterator<T> ite = list.iterator();
			while (ite.hasNext()) {
				T t = ite.next();
				if (null == t) {
					ite.remove();
					continue;
				}
				// 能使用此方式的原因是ContentProviderOperation的withValues的实现是new自己内部的ContentValues对象，
				// 而不是直接使用。但为了不被Google坑，依然重新new
				ContentValues values = new ContentValues();
				updateValues.setUpdateContentValues(t, values);
				operations.add(ContentProviderOperation.newUpdate(ContentUris.withAppendedId(mTableBaseUri, t.getId()))
						.withValues(values).build());
			}
			ContentProviderResult[] results = mContentResolver.applyBatch(mTableBaseUri.getAuthority(), operations);
			Log.d(TAG, "update<List, values> list.size(): " + list.size());
			Log.d(TAG, "update<List, values> results.length: " + results.length);
			return results.length == list.size();
		} catch (Exception e) {
			Log.e(TAG, "update<List, values> Exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public final boolean updateById(long id, ContentValues values) {
		return 1 == mContentResolver.update(ContentUris.withAppendedId(mTableBaseUri, id), values, null, null);
	}
	
	/**
	 * @param t
	 * @param updateValues null to use default, just like invoking {@link #createFromBean}
	 * @return
	 */
	public final boolean updateById(T t, UpdateContentValues<T> updateValues) {
		if (null == t) return false;
		if (null == updateValues) updateValues = mDefaultUpdateContentValues;
		ContentValues values = new ContentValues();
		updateValues.setUpdateContentValues(t, values);
		return updateById(t.getId(), values);
	}
	
	
	// =============================================================================
	// delete
	/**
	 * first you should ensure that all items in the collection must be loaded from database,
	 * in other words, it must contains a database _ID field
	 */
	public final <C extends Collection<T>>boolean deleteListById(C list) {
		try {
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
				operations.add(ContentProviderOperation.newDelete(
						ContentUris.withAppendedId(mTableBaseUri, t.getId())).build());
			}
			ContentProviderResult[] results = mContentResolver.applyBatch(mTableBaseUri.getAuthority(), operations);
			Log.d(TAG, "deleteById<List> list.size(): " + list.size());
			Log.d(TAG, "deleteById<List> results.length: " + results.length);
			return results.length == list.size();
		} catch (Exception e) {
			Log.e(TAG, "deleteById<List> Exception: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public final boolean deleteById(long id) {
		return 1 == mContentResolver.delete(ContentUris.withAppendedId(mTableBaseUri, id), null, null);
	}
}
