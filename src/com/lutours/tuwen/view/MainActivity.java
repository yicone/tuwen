package com.lutours.tuwen.view;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.lutours.tuwen.R;

public class MainActivity extends ActionBarActivity {

    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(new DrawingView(this, null));
        setContentView(R.layout.main);

        Fragment askFrag = new AskFrag();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag_container, askFrag);
        fragmentTransaction.commitAllowingStateLoss();

        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem add = menu.add(0, 1, 0, "Save");
        MenuItem open = menu.add(0, 2, 1, "Open");
        MenuItem close = menu.add(0, 3, 2, "Close");
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        open.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        close.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }
}
