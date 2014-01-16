package com.lutours.tuwen.view;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.lutours.tuwen.R;

public class MainActivity extends FragmentActivity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Fragment askFrag = new AskFrag();
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.frag_container, askFrag);
		fragmentTransaction.commitAllowingStateLoss();
	}
}
