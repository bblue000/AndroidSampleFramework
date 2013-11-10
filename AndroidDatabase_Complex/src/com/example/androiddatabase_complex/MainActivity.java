package com.example.androiddatabase_complex;

import java.util.ArrayList;
import java.util.List;

import org.ixming.android.sqlite.DBManager;
import org.ixming.android.sqlite.DBManagerFactory;
import org.ixming.android.sqlite.SQLiteCondition;
import org.ixming.android.sqlite.SQLiteConditionDefiner;
import org.ixming.android.sqlite.UpdateContentValues;
import org.ixming.utils.NumberUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Log.d("yytest", SQLiteModelInfo.parseModel(TestBean.class).toSql());
//		Log.d("yytest", "" + SQLiteModelInfo.parseModel(TestBean.class).getPKInfo());
//		
//		Log.d("yytest", "" + DBManager.getInstance(getApplicationContext(), TestBean.class)
//				.createFromBean(new TestBean2()));
		
//		TestBean test1 = new TestBean();
//		test1.setCol1("test1");
//		test1.setCol2(1);
//		DBManager.getInstance(getApplicationContext(), TestBean.class)
//			.insertData(test1);
		
//		ArrayList<TestBean> list2 = new ArrayList<TestBean>();
//		for (int i = 0; i < 10; i++) {
//			TestBean bean = new TestBean();
//			bean.setCol1("test2-" + (i + 1));
//			bean.setCol2(100F + (i + 1));
//			list2.add(bean);
//		}
//		DBManager.getInstance(getApplicationContext(), TestBean.class)
//			.insertData(list2);
		
		
//		TestBean test3 = DBManager.getInstance(getApplicationContext(), TestBean.class)
//				.queryOne(null);
//		Log.i("yytest", "" + test3);
//		List<TestBean> list3 = DBManager.getInstance(getApplicationContext(), TestBean.class)
//				.queryList(null);
//		Log.i("yytest", "" + list3);
		
		
//		TestBean test41 = DBManager.getInstance(getApplicationContext(),
//				TestBean.class).queryById(10L);
//		Log.i("yytest", "" + test41);
//		TestBean test42 = DBManager.getInstance(getApplicationContext(),
//				TestBean.class).queryByPrimaryKey(10L);
//		Log.i("yytest", "" + test42);
		
		
//		ArrayList<TestBean> list5 = new ArrayList<TestBean>();
//		for (int i = 0; i < 10; i++) {
//			TestBean bean = new TestBean();
//			bean.setCol1("test5-" + (i + 1));
//			bean.setCol2(100F + (i + 1));
//			list5.add(bean);
//		}
//		Log.i("yytest", "" + list5);
//		DBManager.getInstance(getApplicationContext(), TestBean.class)
//			.insertData(list5, true);
//		Log.i("yytest", "" + list5);
		
		
		
//		List<TestBean> list6 = DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class)
//		.queryList(null);
//		Log.i("yytest", "" + DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class).deleteListByPrimaryKey(list6));
		
		
		
//		ArrayList<TestBean> list7 = new ArrayList<TestBean>();
//		for (int i = 0; i < 10; i++) {
//			TestBean bean = new TestBean();
//			bean.setCol1("test7-" + (i + 1));
//			bean.setCol2(700F + (i + 1));
//			list7.add(bean);
//		}
//		DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class)
//			.insertData(list7);
		
		
//		List<TestBean> list8 = DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class)
//				.queryList(null);
//		for (int i = 0; i < list8.size(); i++) {
//			TestBean bean = list8.get(i);
//			bean.setCol1("test8-" + (i + 1));
//			bean.setCol2(800F + (i + 1));
//		}
//		DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class).updateListByPrimaryKey(list8, null);
		
		
//		List<TestBean> list9 = DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class)
//				.queryList(null);
//		DBManagerFactory.getDBManager(getApplicationContext(), TestBean.class).updateList(list9,
//				new UpdateContentValues<TestBean>() {
//
//					@Override
//					public void setUpdateContentValues(TestBean t,
//							ContentValues values) {
//						values.put("col2", t.getCol2() + 100);
//					}
//		},
//				new SQLiteConditionDefiner<TestBean>() {
//
//					@Override
//					public void config(SQLiteCondition condition, TestBean t) {
//						condition.setWhereColumnArray("col1").setWhereArgs(t.getCol1());
//					}
//				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
