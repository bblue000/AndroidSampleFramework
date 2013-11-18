package org.ixming.android.sqlite;

/**
 * SQL数据类型，定义该数据类型在SQL中的术语，并提供在API中的对应的类的数组。
 * @author Yin Yong
 * @version 1.0
 */
interface SQLDataType {

	String getSQLTypeName();
	
	Class<?>[] getSQLRelatedClasses();
	
}
