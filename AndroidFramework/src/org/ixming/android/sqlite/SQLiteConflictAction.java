package org.ixming.android.sqlite;

public enum SQLiteConflictAction implements Sqlable{

	/**
	 * we do not care about his
	 */
	NONE{
		@Override
		public String toSql() {
			return " ";
		}
	},
	
	REPLACE {
		@Override
		public String toSql() {
			return " REPLACE ";
		}
	};

	public abstract String toSql();
	
}
