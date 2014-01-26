package com.lutours.tuwen.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.lutours.tuwen.R;

/**
 * Created by apple on 14-1-26.
 */
public class AnswerFragment extends SherlockFragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.answer_frag, container, false);

        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.camera_action_bar);
        View actionBarView = actionBar.getCustomView();
        View btnBack = actionBarView.findViewById(R.id.back_button);
        btnBack.setOnClickListener(this);
        View btnNext = actionBarView.findViewById(R.id.next_button);
        btnNext.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(AnswerFragment.this);
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }
}