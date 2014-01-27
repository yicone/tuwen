package com.lutours.tuwen.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.lutours.tuwen.R;

public class MainActivity extends FragmentActivity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(new DrawingView(this, null));
        setContentView(R.layout.main);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		Fragment askFrag = new CameraFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.add(android.R.id.content, askFrag);
		ft.commitAllowingStateLoss();
	}
}
