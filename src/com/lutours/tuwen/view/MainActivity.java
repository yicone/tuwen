package com.lutours.tuwen.view;

import android.os.Bundle;
import android.support.v4.app.*;
import android.widget.TabHost;
import com.lutours.tuwen.R;

public class MainActivity extends FragmentActivity {

	private FragmentTabHost mTabHost = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(new DrawingView(this, null));
        setContentView(R.layout.main);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.frag_container);

		TabHost.TabSpec tabSpec = mTabHost.newTabSpec("home");
		tabSpec.setIndicator("list", getApplicationContext().getResources().getDrawable(R.drawable.camera_icon));
		mTabHost.addTab(tabSpec, QuestionListFragment.class, null);

		tabSpec = mTabHost.newTabSpec("camera");
		tabSpec.setIndicator("camera", getApplicationContext().getResources().getDrawable(R.drawable.camera_icon));
		mTabHost.addTab(tabSpec, CameraFragment.class, null);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		Fragment askFrag = new QuestionListFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(android.R.id.content, askFrag);
		ft.commitAllowingStateLoss();
	}
}
