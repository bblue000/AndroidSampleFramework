package org.ixming.android.sqlite;

/**
 * 该类规定：所有的带有Primary Key的数据库表应该都继承自BaseColumns
 * @author Yin Yong
 * @version 1.0
 */
public abstract class BaseSQLitePKModel implements BaseSQLiteModel {

	public BaseSQLitePKModel() { }
	
	private long _id;
	/**
	 * set the id property from database ,filled with column <code>_ID</code>
	 */
	public void setId(long id) {
		this._id = id;
	}
	
	/**
	 * get the id property
	 */
	public long getId() {
		return _id;
	}
	
}
