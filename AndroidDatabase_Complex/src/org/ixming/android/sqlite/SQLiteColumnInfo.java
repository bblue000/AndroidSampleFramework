package org.ixming.android.sqlite;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.ixming.android.sqlite.annotations.Column;
import org.ixming.android.sqlite.annotations.PrimaryKey;
import org.ixming.android.utils.FrameworkLog;
import org.ixming.framework.annotation.UncertainState;

import android.content.ContentValues;
import android.database.Cursor;

final class SQLiteColumnInfo implements Sqlable{

	final String TAG = SQLiteColumnInfo.class.getSimpleName();
	
	private static final int TYPE_STRING = 0x1;
	private static final int TYPE_INTEGER = 0x2;
	private static final int TYPE_LONG = 0x3;
	private static final int TYPE_FLOAT = 0x4;
	private static final int TYPE_DOUBLE = 0x5;
	private static final int TYPE_SHORT = 0x6;
	private static final int TYPE_BYTE = 0x7;
	private static final int TYPE_BYTEARRAY = 0x8;
	private static final HashMap<Class<?>, Integer> SUPPOTED_TYPE_MAP ;
	static {
		SUPPOTED_TYPE_MAP = new HashMap<Class<?>, Integer>();
		SUPPOTED_TYPE_MAP.put(String.class, TYPE_STRING);
		
		SUPPOTED_TYPE_MAP.put(Integer.class, TYPE_INTEGER);
		SUPPOTED_TYPE_MAP.put(int.class, TYPE_INTEGER);
		
		SUPPOTED_TYPE_MAP.put(Long.class, TYPE_LONG);
		SUPPOTED_TYPE_MAP.put(long.class, TYPE_LONG);
		
		SUPPOTED_TYPE_MAP.put(Float.class, TYPE_FLOAT);
		SUPPOTED_TYPE_MAP.put(float.class, TYPE_FLOAT);
		
		SUPPOTED_TYPE_MAP.put(Double.class, TYPE_DOUBLE);
		SUPPOTED_TYPE_MAP.put(double.class, TYPE_DOUBLE);
		
		SUPPOTED_TYPE_MAP.put(Short.class, TYPE_SHORT);
		SUPPOTED_TYPE_MAP.put(short.class, TYPE_SHORT);
		
		SUPPOTED_TYPE_MAP.put(Byte.class, TYPE_BYTE);
		SUPPOTED_TYPE_MAP.put(byte.class, TYPE_BYTE);
		
		SUPPOTED_TYPE_MAP.put(byte[].class, TYPE_BYTEARRAY);
	}

	
	public static SQLiteColumnInfo createFrom(Field field) {
		if (null == field) {
			return null;
		}
		Column colAn = field.getAnnotation(Column.class);
		if (null == colAn) {
			return null;
		}
		PrimaryKey pkAn = field.getAnnotation(PrimaryKey.class);
		return new SQLiteColumnInfo(field, colAn, pkAn);
	}
	
	private final int mColumnType;
	private final Field mField;
	private final Column mColumnToken;
	private final PrimaryKey mPKToken;
	private SQLiteColumnInfo(Field field, Column columnToken, PrimaryKey pKToken) {
		mField = field;
		mColumnToken = columnToken;
		mPKToken = pKToken;
		@UncertainState
		Integer val = SUPPOTED_TYPE_MAP.get(mField.getType());
		if (null == val) {
			throw new IllegalArgumentException("SQLiteColumnInfo<init> field type is not supported!");
		}
		if (!mField.isAccessible()) {
			mField.setAccessible(true);
		}
		mColumnType = val;
	}
	
	public boolean isPrimaryKey() {
		return null != mPKToken;
	}
	
	public boolean isExtendable() {
		return mColumnToken.extendable();
	}
	
	public String getColumnName() {
		return mColumnToken.name();
	}

	public ColumnType getSQLiteType() {
		switch (mColumnType) {
		case TYPE_INTEGER:
			return ColumnType.INTEGER;
		case TYPE_LONG:
			return ColumnType.LONG;
		case TYPE_FLOAT:
			return ColumnType.FLOAT;
		case TYPE_DOUBLE:
			return ColumnType.DOUBLE;
		case TYPE_SHORT:
			return ColumnType.SHORT;
		case TYPE_BYTE:
			return ColumnType.INTEGER;
		case TYPE_BYTEARRAY:
			return ColumnType.BLOB;
		case TYPE_STRING:
		default:
			return ColumnType.VARCHAR;
		}
	}
	
	@Override
	public String toSql() {
		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(mColumnToken.name())
			.append(" ").append(getSQLiteType().getSQLTypeName());
		if (null != mPKToken) {
			sb.append(" ").append("PRIMARY KEY");
			if (mPKToken.autoIncrement()) {
				sb.append(" ").append("AUTOINCREMENT");
			}
		}
		SQLiteConflictAction onUniqueConflict = mColumnToken.onUniqueConflict();
		switch (onUniqueConflict) {
		case REPLACE:
		default:
			sb.append(" ").append("UNIQUE ON CONFLICT")
			.append(" ").append(onUniqueConflict.toSql());
			break;
		case NONE:
			// do nothing
			break;
		}
		sb.append(" ");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return toSql();
	}
	
	public void putValueToContentValues(String colName, Object targetObj, ContentValues values) {
		switch (mColumnType) {
		case TYPE_STRING:
			values.put(colName, wrapAndCastIfNeed(targetObj, String.class));
			break;
		case TYPE_INTEGER:
			values.put(colName, wrapAndCastIfNeed(targetObj, Integer.class));
			break;
		case TYPE_LONG:
			values.put(colName, wrapAndCastIfNeed(targetObj, Long.class));
			break;
		case TYPE_FLOAT:
			values.put(colName, wrapAndCastIfNeed(targetObj, Float.class));
			break;
		case TYPE_DOUBLE:
			values.put(colName, wrapAndCastIfNeed(targetObj, Double.class));
			break;
		case TYPE_SHORT:
			values.put(colName, wrapAndCastIfNeed(targetObj, Short.class));
			break;
		case TYPE_BYTE:
			values.put(colName, wrapAndCastIfNeed(targetObj, Byte.class));
			break;
		case TYPE_BYTEARRAY:
			values.put(colName, wrapAndCastIfNeed(targetObj, byte[].class));
			break;
		default:
			break;
		}
	}
	
	public void setValueToField(String colName, Object target, Cursor cursor) {
		switch (mColumnType) {
		case TYPE_STRING:
			setFieldValueToTarget(target, cursor.getString(cursor.getColumnIndex(colName)));
			break;
		case TYPE_INTEGER:
			setFieldValueToTarget(target, cursor.getInt(cursor.getColumnIndex(colName)));
			break;
		case TYPE_LONG:
			setFieldValueToTarget(target, cursor.getLong(cursor.getColumnIndex(colName)));
			break;
		case TYPE_FLOAT:
			setFieldValueToTarget(target, cursor.getFloat(cursor.getColumnIndex(colName)));
			break;
		case TYPE_DOUBLE:
			setFieldValueToTarget(target, cursor.getDouble(cursor.getColumnIndex(colName)));
			break;
		case TYPE_SHORT:
			setFieldValueToTarget(target, cursor.getShort(cursor.getColumnIndex(colName)));
			break;
		case TYPE_BYTE:
			setFieldValueToTarget(target, (byte) cursor.getInt(cursor.getColumnIndex(colName)));
			break;
		case TYPE_BYTEARRAY:
			setFieldValueToTarget(target, cursor.getBlob(cursor.getColumnIndex(colName)));
			break;
		default:
			break;
		}
	}
	
	public <T>void setValueToField(Object target, T val) {
		setFieldValueToTarget(target, val);
	}
	
	private <T>T wrapAndCastIfNeed(Object o, Class<T> clz) {
		try {
			return clz.cast(mField.get(o));
		} catch (Exception e) {
			FrameworkLog.e(TAG, "wrapAndCastIfNeed Exception: " + e.getMessage());
			return null;
		}
	}
	
	private <T>void setFieldValueToTarget(Object target, T val) {
		try {
			mField.set(target, val);
		} catch (Exception e) {
			FrameworkLog.e(TAG, "setFieldValueToTarget Exception: " + e.getMessage());
		}
	}
	
	public Object getFieldValueFromTarget(Object target) {
		try {
			return mField.get(target);
		} catch (Exception e) {
			FrameworkLog.e(TAG, "getFieldValueFromTarget Exception: " + e.getMessage());
			return null;
		}
	}
}
