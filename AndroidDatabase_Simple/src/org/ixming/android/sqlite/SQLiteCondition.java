package org.ixming.android.sqlite;

import java.lang.ref.WeakReference;

public class SQLiteCondition {

	/**
	 * 如果instance，则生成一个空的SQLiteCondition对象
	 */
	public static SQLiteCondition checkNull(SQLiteCondition instance) {
		return null == instance ? create() : instance;
	}
	
	public static final String SORT_ASC = "ASC";
	public static final String SORT_DESC = "DESC";
	public static SQLiteCondition create() {
		return new WeakReference<SQLiteCondition>(new SQLiteCondition()).get();
	}
	
	private String where; private String[] whereArgs;
	private String sortClause;
	private SQLiteCondition() { }
	
	public SQLiteCondition setWhereClause(String where) {
		this.where = where;
		return this;
	}
	
	public String getWhereClause() {
		return this.where;
	}
	
	public SQLiteCondition setWhereArgs(String...whereArgs) {
		this.whereArgs = whereArgs;
		return this;
	}

	public String[] getWhereArgs() {
		return this.whereArgs;
	}
	
	public SQLiteCondition setSortOrder(String sortClause) {
		this.sortClause = sortClause;
		return this;
	}

	public String getSortOrder() {
		return this.sortClause;
	}
	
	public SQLiteCondition clear() {
		this.where = null;
		this.whereArgs = null;
		this.sortClause = null;
		return this;
	}
}
