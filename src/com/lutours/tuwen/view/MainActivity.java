package com.lutours.tuwen.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TabHost;
import com.lutours.tuwen.R;

public class MainActivity extends FragmentActivity {

    private MyFragmentTabHost mTabHost = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mTabHost.setVisibility((tabId == "camera") ? View.GONE : View.VISIBLE);
            }
        });

        TabHost.TabSpec tabSpec = mTabHost.newTabSpec("home");
        tabSpec.setIndicator("HOME", getResources().getDrawable(android.R.drawable.star_on));
        mTabHost.addTab(tabSpec, QuestionListFragment.class, null);

        tabSpec = mTabHost.newTabSpec("camera");
        tabSpec.setIndicator("", getResources().getDrawable(R.drawable.camera_icon));
        mTabHost.addTab(tabSpec, CameraFragment.class, null);

        tabSpec = mTabHost.newTabSpec("my");
        tabSpec.setIndicator("MY", null);
        mTabHost.addTab(tabSpec, CameraFragment.class, null);

    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager()
                .findFragmentByTag(mTabHost.getCurrentTabTag());
        if (f.getFragmentManager() == null || f.getFragmentManager().getBackStackEntryCount() == 0) {
            if (mTabHost.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    public MyFragmentTabHost getTabHost() {
        return mTabHost;
    }
}
