package com.example.androiddatabase_complex;

import org.ixming.android.sqlite.BaseSQLiteModel;
import org.ixming.android.sqlite.annotations.Column;
import org.ixming.android.sqlite.annotations.PrimaryKey;
import org.ixming.android.sqlite.annotations.Table;

@Table(authority=LocalDBProvider.AUTHORITY, name=TestTable1.TABLE_NAME)
public class TestBean implements BaseSQLiteModel {

	@Column(name=TestTable1._ID)
	@PrimaryKey
	private long _id;
	
	@Column(name=TestTable1.COL1)
	private String col1 = "TestBean";
	
	@Column(name=TestTable1.COL2)
	private float col2 = 0.1F;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public float getCol2() {
		return col2;
	}

	public void setCol2(float col2) {
		this.col2 = col2;
	}

	@Override
	public String toString() {
		return "TestBean [_id=" + _id + ", col1=" + col1 + ", col2=" + col2
				+ "]";
	}
}
