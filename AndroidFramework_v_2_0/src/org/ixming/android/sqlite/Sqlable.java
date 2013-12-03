package org.ixming.android.sqlite;

/**
 * 对象可以转为SQL语句
 * @author Yin Yong
 * @version 1.0
 */
public interface Sqlable {

	String SEPERATOR = " ";
	
	/**
	 * 转化为SQL语句
	 */
	String toSql();
	
}
