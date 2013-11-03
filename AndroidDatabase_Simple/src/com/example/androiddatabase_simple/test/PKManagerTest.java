package com.example.androiddatabase_simple.test;

import java.util.ArrayList;
import java.util.List;

import org.ixming.android.sqlite.UpdateContentValues;

import com.example.androiddatabase_simple.TestPKBean;
import com.example.androiddatabase_simple.TestPKDBManager;

import android.content.ContentValues;
import android.content.Context;

public class PKManagerTest {

	
	public static void test(Context context) {
		// 1. insert one
//		TestPKBean bean = new TestPKBean();
//		bean.col1 = "text1";
//		bean.col2 = 1L;
//		TestPKDBManager.getInstance(context).insertData(bean);
		
		
		
		// 2. insert list
		List<TestPKBean> list = new ArrayList<TestPKBean>();
		for (int i = 0; i < 5; i++) {
			TestPKBean bean = new TestPKBean();
			bean.col1 = "text" + (1 + i + 1);
			bean.col2 = (1 + i + 1);
			list.add(bean);
		}
		TestPKDBManager.getInstance(context).insertData(list);
		TestPKDBManager.getInstance(context).deleteListById(list.subList(0, 2));
		
		
		
		
		// 3. insert list and delete before inserting
//		List<TestPKBean> list = new ArrayList<TestPKBean>();
//		for (int i = 0; i < 10; i++) {
//			TestPKBean bean = new TestPKBean();
//			bean.col1 = "text" + (i + 1);
//			bean.col2 = (i + 1L);
//			list.add(bean);
//		}
//		TestPKDBManager.getInstance(context).insertData(list, true);
		
		
		
		
		// 4. update list
//		List<TestPKBean> list = TestPKDBManager.getInstance(context).queryAll(null);
//		for (int i = 0; null != list && i < list.size(); i++) {
//			TestPKBean bean = list.get(i);
//			bean.col1 = "text" + (i + 10);
//			bean.col2 = (1 + i + 10);
//		}
//		TestPKDBManager.getInstance(context).updateListById(list, new UpdateContentValues<TestPKBean>() {
//			@Override
//			public void setUpdateContentValues(TestPKBean t, ContentValues values) {
//				values.put("col2", t.col2 + 100);
//			}
//		});
		
		
		
		
		// 5. delete list
//		List<TestPKBean> list = TestPKDBManager.getInstance(context).queryAll(null);
//		TestPKDBManager.getInstance(context).deleteListById(list.subList(0, 5));
	}
}
