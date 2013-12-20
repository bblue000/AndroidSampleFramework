package com.frameworkexample.android.model;

import org.ixming.android.sqlite.BaseSQLiteModel;
import org.ixming.android.sqlite.annotations.Column;
import org.ixming.android.sqlite.annotations.PrimaryKey;
import org.ixming.android.sqlite.annotations.Table;

import com.frameworkexample.android.model.provider.LocalDBProvider;
import com.frameworkexample.android.model.provider.TableUser;

@Table(authority=LocalDBProvider.AUTHORITY, name=TableUser.TABLE_NAME)
public class User implements BaseSQLiteModel{

	@PrimaryKey
	@Column(name=TableUser._ID)
	long id;
	
	@Column(name=TableUser.COLUMN_NICKNAME)
	String name;
	
	@Column(name=TableUser.COLUMN_USERID, asIndex=true)
	long userId;

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", userId=" + userId + "]";
	}
}
