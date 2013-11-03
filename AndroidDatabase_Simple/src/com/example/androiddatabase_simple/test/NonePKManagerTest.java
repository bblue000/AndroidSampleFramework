package com.example.androiddatabase_simple.test;

import android.content.Context;

public class NonePKManagerTest {

	
	public static void test(Context context) {
		// 1. insert one
//		TestBean bean = new TestBean();
//		bean.col1 = "text1";
//		bean.col2 = 1L;
//		bean.col3 = 1.0F;
//		TestDBManager.getInstance(context).insertData(bean);
		
		
		
		
		// 2. insert list
//		List<TestBean> list = new ArrayList<TestBean>();
//		for (int i = 0; i < 5; i++) {
//			TestBean bean = new TestBean();
//			bean.col1 = "text" + (1 + i + 1);
//			bean.col2 = (1 + i + 1);
//			bean.col3 = (1.0F + i + 1);
//			list.add(bean);
//		}
//		TestDBManager.getInstance(context).insertData(list);
		
		
		
		
		
		// 3. insert list and delete before inserting
//		List<TestBean> list = new ArrayList<TestBean>();
//		for (int i = 0; i < 2; i++) {
//			TestBean bean = new TestBean();
//			bean.col1 = "text" + (1 + i + 1);
//			bean.col2 = (1 + i + 1);
//			bean.col3 = (1.0F + i + 1);
//			list.add(bean);
//		}
//		TestDBManager.getInstance(context).insertData(list, true);
		
		
		
		
		
		// 4. update list
//		List<TestBean> list = TestDBManager.getInstance(context).queryAll(null);
//		for (int i = 0; null != list && i < list.size(); i++) {
//			TestBean bean = list.get(i);
//			bean.col1 = "text" + (i + 10);
//			//bean.col2 = (1 + i + 10);
//			bean.col3 = (1.0F + i + 10);
//		}
//		TestDBManager.getInstance(context).update(list, null, new SQLiteConditionDefiner<TestBean>() {
//			@Override
//			public void config(SQLiteCondition condition, TestBean t) {
//				condition.setWhereClause(" col2 = ? ").setWhereArgs(t.col2 + "");
//			}
//		});
		
		
		
		
		
		// 5. delete list
//		List<TestBean> list = TestDBManager.getInstance(context).queryAll(null);
//		TestDBManager.getInstance(context).delete(list, new SQLiteConditionDefiner<TestBean>() {
//			@Override
//			public void config(SQLiteCondition condition, TestBean t) {
//				condition.setWhereClause(" col2 = ? ").setWhereArgs(t.col2 + "");
//			}
//		});
	}
}
