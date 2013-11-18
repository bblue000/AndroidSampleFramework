package org.ixming.android.sqlite;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;

import org.ixming.android.sqlite.annotations.Table;
import org.ixming.android.utils.FrameworkLog;
import org.ixming.framework.annotation.UncertainState;

class SQLiteModelInfo implements Sqlable{
	final static String TAG = SQLiteModelInfo.class.getSimpleName();
	/**
	 * <p>
	 * <i>Tip1</i><br/>
	 * 传入的类必须是Annotation-{@link Table}标识的；
	 * </p>
	 * <p>
	 * <i>Tip2</i><br/>
	 * 传入的类必须是接口{@link BaseSQLiteModel}标识的；
	 * </p>
	 * <p>
	 * <i>Tip3</i><br/>
	 * 表字段列的查找顺序：从当前类往父类依次查询，直到遇到上层类中没有继承BaseSQLiteModel接口的情况才停止；
	 * </p>
	 * <p>
	 * <i>Tip4</i><br/>
	 * 关于<b>主键：</b>此处注意最先找到的主键认定为主键，所以确保主键定义不会受继承关系影响
	 * </p>
	 */
	public static SQLiteModelInfo parseModel(Class<?> clz) {
		int modifiers = clz.getModifiers();
		if (Modifier.isInterface(modifiers)) {
			throw new IllegalArgumentException("clz: " + clz + " is a interface!");
		}
		if (Modifier.isAbstract(modifiers)) {
			throw new IllegalArgumentException("clz: " + clz + " is a abstract class!");
		}
		// create a new one or update the map
		Table tableAn = clz.getAnnotation(Table.class);
		if (null == tableAn) {
			throw new IllegalArgumentException("clz: " + clz + " has no <Table> Annotation!");
		}
		SQLiteColumnInfo pkInfo = null;
		HashMap<String, SQLiteColumnInfo> columnInfoMap = new HashMap<String, SQLiteColumnInfo>();
		
		boolean isPKLoaded = false;
		boolean isTargetClass = true;
		@UncertainState
		Class<?> raw = clz;
		while (null != raw && BaseSQLiteModel.class.isAssignableFrom(raw)) {
			Field[] fields = raw.getDeclaredFields();
			if (null == fields || fields.length == 0) {
				//throw new UnsupportedOperationException("there is no field inside class: " + clz);
			} else {
				for (Field f: fields) {
					SQLiteColumnInfo colInfo = SQLiteColumnInfo.createFrom(f);
					// 不是目标类
					if (null == colInfo) {
						FrameworkLog.d(TAG, "parseModel::not a target Column field");
						continue;
					}
					if (!isTargetClass && !colInfo.isExtendable()) {
						FrameworkLog.d(TAG, "parseModel::not a extendable Column field");
						continue;
					}
					if (isPKLoaded && colInfo.isPrimaryKey()/* && colInfo.isExtendable()*/) {
						FrameworkLog.w(TAG, "parseModel::there are multi extendable Column fields!");
						//throw new UnsupportedOperationException("parseModel::there are multi extendable Column fields!"
						//		+ "please set 'false' to Column's 'extendable' field in super classes!");
						continue;
					}
					// 判断是否是主键。此处注意最先找到的主键认定为主键，所以确保主键定义不会受继承关系影响
					if (!isPKLoaded) {
						if (colInfo.isPrimaryKey()) {
							isPKLoaded = true;
							pkInfo = colInfo;
							continue;
						}
					}
					// 内部实现的hash查询应当快一点，与使用HashSet一样，而HashSet内部实际是HashMap实现的
					if (columnInfoMap.containsKey(colInfo.getColumnName())) {
						continue;
					}
					columnInfoMap.put(colInfo.getColumnName(), colInfo);
				}
			}
			raw = raw.getSuperclass();
			isTargetClass = false;
		}

		if (null == pkInfo && columnInfoMap.isEmpty()) {
			throw new UnsupportedOperationException("no columns! if exception occurs when doing parsing? or "
					+ "no class implements interface <BaseSQLiteModel>");
		}
		
		return new WeakReference<SQLiteModelInfo>(new SQLiteModelInfo(tableAn, pkInfo, columnInfoMap)).get();
	}
	
	
	private Table mTableInfo;
	private SQLiteColumnInfo mPrimaryColumn;
	private HashMap<String, SQLiteColumnInfo> mColumnMap;
	private SQLiteModelInfo(Table tableInfo, SQLiteColumnInfo primaryColumn,
			HashMap<String, SQLiteColumnInfo> columnMap) {
		mTableInfo = tableInfo;
		mPrimaryColumn = primaryColumn;
		mColumnMap = columnMap;
	}
	
	public String getAuthority() {
		return mTableInfo.authority();
	}
	
	public String getTableName() {
		return mTableInfo.name();
	}
	
	public String[] getColumns() {
		int count = 0;
		if (null != mPrimaryColumn) count++;
		if (null != mColumnMap) count += mColumnMap.size();
		String[] cols = new String[count];
		int index = 0;
		if (null != mPrimaryColumn) cols[index++] = mPrimaryColumn.getColumnName();
		for (SQLiteColumnInfo colInfo : mColumnMap.values()) {
			cols[index++] = colInfo.getColumnName();
		}
		return cols;
	}
	
	public SQLiteColumnInfo getPKInfo() {
		return mPrimaryColumn;
	}
	
	public SQLiteColumnInfo getColumnInfo(String colName) {
		if (null != mPrimaryColumn && mPrimaryColumn.getColumnName().equalsIgnoreCase(colName)) {
			return mPrimaryColumn;
		}
		return mColumnMap.get(colName);
	}

	@Override
	public String toSql() {
		StringBuffer sb = new StringBuffer();
		
		boolean hasPK = (null != mPrimaryColumn);
		sb.append("CREATE TABLE ").append(mTableInfo.name()).append(" ( ");
		if (hasPK) {
			sb.append(mPrimaryColumn.toSql());
			sb.append(" ");
		}
		
		int i = -1;
		Iterator<SQLiteColumnInfo> ite = mColumnMap.values().iterator();
		while (ite.hasNext()) {
			if (i < 0) {
				if (hasPK) {
					sb.append(" , ");
				}
				i ++;
			} else {
				sb.append(" , ");
			}
			sb.append(ite.next().toSql());
		}
		
		sb.append(" ); ");
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return toSql();
	}
	
	@Override
	protected void finalize() throws Throwable {
		FrameworkLog.d(TAG, "finalized");
		
		mTableInfo = null;
		mPrimaryColumn = null;
		if (null != mColumnMap) {
			mColumnMap.clear();
			mColumnMap = null;
		}
		
		super.finalize();
	}
}
