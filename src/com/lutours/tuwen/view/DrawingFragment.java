package com.lutours.tuwen.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.lutours.tuwen.R;

/**
 * Created by xdzheng on 14-1-16.
 */
public class DrawingFragment extends SherlockFragment implements View.OnClickListener {
    private static final int CAMERA_REQUEST = 1888;
    private View rootView;

    private ImageView dvCanvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.drawing_frag, container, false);

        dvCanvas = (ImageView) rootView.findViewById(R.id.dvCanvas);

        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
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
                // ∆Ù∂Ø≈ƒ’’
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.next_button:
                AnswerFragment frag = new AnswerFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frag_container, frag);
                fragmentTransaction.commitAllowingStateLoss();

//                Dialog dialog = new Dialog(getActivity());
//                dialog.setContentView(R.layout.ask_dialog);
//                Display display = getActivity().getWindowManager().getDefaultDisplay();
//                DisplayMetrics dm = new DisplayMetrics();
//                display.getMetrics(dm);
//                dialog.getWindow().setLayout(dm.widthPixels, dm.heightPixels / 2);  //Controlling width and height
//
//                ImageView ivCanvasPreview = (ImageView) dialog.findViewById(R.id.ivPhotoPreview);
//                // TODO Àı∑≈
//                ivCanvasPreview.setImageDrawable(dvCanvas.getDrawable());
//
//                final View question_container = dialog.findViewById(R.id.question_container);
//                final EditText etQuestion = (EditText) dialog.findViewById(R.id.question);
//                question_container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        Log.v("DrawingFragment", "question_container hasFocus: " + hasFocus);
//                    }
//                });
//
//                etQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        Log.v("DrawingFragment", "question hasFocus: " + hasFocus);
//                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        Animation anim;
//                        if (hasFocus) {
//                            imm.showSoftInput(etQuestion, 0);
//                            anim = new TranslateAnimation(0, 0, 0, -60);
//                            anim.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//                                    question_container.setY(0);
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {
//
//                                }
//                            });
//
//                        } else {
//                            imm.hideSoftInputFromWindow(etQuestion.getWindowToken(), 0);
//                            anim = new TranslateAnimation(0, 0, -60, 0);
//                            anim.setAnimationListener(new Animation.AnimationListener() {
//                                @Override
//                                public void onAnimationStart(Animation animation) {
//
//                                }
//
//                                @Override
//                                public void onAnimationEnd(Animation animation) {
//                                    question_container.setY(60);
//                                }
//
//                                @Override
//                                public void onAnimationRepeat(Animation animation) {
//
//                                }
//                            });
//                        }
//                        question_container.startAnimation(anim);
//                    }
//                });
//                dialog.show();
//                etQuestion.requestFocus();
                break;
        }
    }
}