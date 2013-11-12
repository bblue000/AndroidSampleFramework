package org.ixming.android.sqlite.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ixming.android.sqlite.SQLiteConflictAction;

/**
 * 标示一个类为相应的数据库表字段
 * 
 * @author Yin Yong
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
public @interface Column {

	static final int LEN_UNDEFINED = Integer.MIN_VALUE;

	/**
	 * 对应的名称，该框架需要客户端指定；当需要条件查询时，能够让客户端自行决定。
	 */
	public String name();
	
	/**
	 * 如果有继承关系的两个类，该列对应的属性存在于父类，是否被子类继承和识别。默认为True
	 */
	public boolean extendable() default true;
	
	public SQLiteConflictAction onUniqueConflict() default SQLiteConflictAction.NONE;
	
//	public SQLiteConflictAction onNullConflict() default SQLiteConflictAction.NONE;
	
//	/**
//	 * 数据类型，默认为{@link ColumnType#VARCHAR}
//	 * 
//	 * @see ColumnType
//	 */
//	public ColumnType type() default ColumnType.VARCHAR;

//	/**
//	 * 一些字段需要定义长度，该属性定义相应的长度
//	 */
//	public int len() default LEN_UNDEFINED;

}
