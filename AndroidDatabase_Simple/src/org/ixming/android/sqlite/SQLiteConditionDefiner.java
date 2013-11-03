package org.ixming.android.sqlite;

/**
 * 删除数据时，根据传入的T对象，将条件传入SQLiteCondition中
 * @author Yin Yong
 * @version 1.0
 * @param <T>  extends BaseSQLiteModel, 限制一下支持该接口的类型
 */
public interface SQLiteConditionDefiner<T extends BaseSQLiteModel> {

	/**
	 * @param condition never null
	 * @param t never null
	 */
	void config(SQLiteCondition condition, T t);
	
}
