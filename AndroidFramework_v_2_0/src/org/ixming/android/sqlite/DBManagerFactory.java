package org.ixming.android.sqlite;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 集中管理和向外提供DBManager实例的工厂类。
 * <p>
 * 调用者不需要关心内部的资源释放问题，本框架内部已经做了一些处理，并一直关注它的优化。
 * </p>
 * 
 * How to use this framework:
 * <br/>
 * <br/>
 * <p>
 * 1、首先客户端需要有一个ContentProvider类，可以是继承自本框架的{@link org.ixming.android.model.provider.BaseDBProvider}，
 * 并在AndroidManifest.xml中定义；
 * </p>
 * <p>
 * 2、然后要有一个提供数据库表及其列名的接口/类（只需要提供表明和列名的常量类），它可能是继承自{@link android.provider.BaseColumns}:
 * <pre>
 * public interface TestTable extends BaseColumns {
 * 	String TABLE_NAME = "test";
 * 	String COL1 = "col1";
 * 	String COL2 = "col2";
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * 3、与“2”中相应的Model：
 * <pre>
 * &#169;Table(authority=LocalDBProvider.AUTHORITY, name=TestTable.TABLE_NAME)
 * public class TestBean implements BaseSQLiteModel {

	&#169;Column(name=TestTable._ID)
	&#169;PrimaryKey
	private long _id;
	
	&#169;Column(name=TestTable.COL1)
	private String col1 = "TestBean";
	
	&#169;Column(name=TestTable.COL2)
	private float col2 = 0.1F;
	
	...
}
 * </pre>
 * </p>
 * 
 * <p>
 * 4、使用到SQL创建语句，则可以直接调用{@link DBManager#getTableCreation(Class)}；
 * </p>
 * 
 * <p>
 * 5、使用{@link DBManagerFactory#getDBManager(Context, Class)}方法获取该表的数据库管理器。
 * </p>
 * @author Yin Yong
 * @version 1.0
 */
public class DBManagerFactory {

	/**
	 * no instance needed
	 */
	private DBManagerFactory() { }
	
	private static final Map<Class<?>, SoftReference<DBManager<?>>> mManagerCache = new HashMap<Class<?>, SoftReference<DBManager<?>>>();
	/**
	 * 获取数据库管理类
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseSQLiteModel>DBManager<T> getDBManager(Context context, Class<T> clz) {
		synchronized (mManagerCache) {
			SoftReference<DBManager<?>> ref = mManagerCache.get(clz);
			DBManager<?> ins;
			if (null == ref || null == (ins = ref.get())) {
				ins = new DBManager<T>(context, clz);
				mManagerCache.put(clz, new SoftReference<DBManager<?>>(ins));
				return (DBManager<T>) ins;
			} else {
				return (DBManager<T>) ins;
			}
		}
	}
}
