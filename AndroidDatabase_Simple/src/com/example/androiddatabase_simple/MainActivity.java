package com.example.androiddatabase_simple;

import java.util.ArrayList;
import java.util.List;

import org.ixming.android.sqlite.SQLiteCondition;
import org.ixming.android.sqlite.SQLiteConditionDefiner;

import com.example.androiddatabase_simple.test.PKManagerTest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		PKManagerTest.test(getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
