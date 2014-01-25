package com.lutours.tuwen.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.lutours.tuwen.R;

/**
 * Created by xdzheng on 14-1-16.
 */
public class AskFrag extends SherlockFragment implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private View rootView;

    private ImageView dvCanvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ask_frag, container, false);

        dvCanvas = (ImageView) rootView.findViewById(R.id.dvCanvas);

        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
        // actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setIcon(R.drawable.avatar);            // just set logo
        // actionBar.setDisplayShowHomeEnabled(false);       // show or hide home button. when hide, can't show "<" icon with anyway.
        //actionBar.setHomeButtonEnabled(true);         // only let home button was clickable
        //actionBar.setDisplayHomeAsUpEnabled(false);    // add "<" icon and let home button was clickable
        // actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_bar);
        View actionBarView = actionBar.getCustomView();
        View btnBack = actionBarView.findViewById(R.id.back_button);
        btnBack.setOnClickListener(this);
        View btnNext = actionBarView.findViewById(R.id.next_button);
        btnNext.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            dvCanvas.setImageDrawable(new BitmapDrawable(getResources(), photo));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                // Æô¶¯ÅÄÕÕ
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.next_button:
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.ask_dialog);
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                DisplayMetrics dm = new DisplayMetrics();
                display.getMetrics(dm);
                dialog.getWindow().setLayout(dm.widthPixels, dm.heightPixels / 2);  //Controlling width and height

                ImageView ivCanvasPreview = (ImageView) dialog.findViewById(R.id.ivPhotoPreview);
                // TODO Ëõ·Å
                ivCanvasPreview.setImageDrawable(dvCanvas.getDrawable());

                final View question_container = dialog.findViewById(R.id.question_container);
                final EditText etQuestion = (EditText) dialog.findViewById(R.id.question);
                question_container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Log.v("AskFrag", "question_container hasFocus: " + hasFocus);
                    }
                });

                etQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Log.v("AskFrag", "question hasFocus: " + hasFocus);
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        Animation anim;
                        if (hasFocus) {
                            imm.showSoftInput(etQuestion, 0);
                            anim = new TranslateAnimation(0, 0, 0, -60);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    question_container.setY(0);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                        } else {
                            imm.hideSoftInputFromWindow(etQuestion.getWindowToken(), 0);
                            anim = new TranslateAnimation(0, 0, -60, 0);
                            anim.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    question_container.setY(60);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                        question_container.startAnimation(anim);
                    }
                });
                dialog.show();
                etQuestion.requestFocus();
                break;
        }
    }
}