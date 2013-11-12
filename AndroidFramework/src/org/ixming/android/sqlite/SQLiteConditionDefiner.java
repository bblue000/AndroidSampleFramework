package org.ixming.android.sqlite;

import org.ixming.framework.annotation.TemporarilyDone;

/**
 * 处理数据时，根据传入的T对象，在调用处动态将条件传入SQLiteCondition中。
 * @author Yin Yong
 * @version 1.0
 * @param <T> extends BaseSQLiteModel, 限制一下支持该接口的类型
 */
@TemporarilyDone
public interface SQLiteConditionDefiner<T extends BaseSQLiteModel> {

	/**
	 * @param condition never null，调用者自行设置SQL语句的条件
	 * @param t never null
	 */
	void config(SQLiteCondition condition, T t);
	
}
