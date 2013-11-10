package com.example.androiddatabase_complex;

import org.ixming.android.sqlite.annotations.Column;
import org.ixming.android.sqlite.annotations.Table;

@Table(authority=LocalDBProvider.AUTHORITY, name=TestTable2.TABLE_NAME)
public class TestBean2 extends TestBean {

	@Column(name=TestTable2.COL1)
	private String col1 = "TestBean2";
	
	
	@Column(name=TestTable2.COL3)
	private byte[] col3 = new byte[128];


	public String getCol1() {
		return col1;
	}


	public void setCol1(String col1) {
		this.col1 = col1;
	}


	public byte[] getCol3() {
		return col3;
	}


	public void setCol3(byte[] col3) {
		this.col3 = col3;
	}
	
}
