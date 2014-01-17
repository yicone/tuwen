package com.lutours.tuwen.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.lutours.tuwen.R;

/**
 * Created by xdzheng on 14-1-16.
 */
public class AskFrag extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    private View rootView;

    private ImageView ivPhoto;
    private EditText etQuestion;
    private View question_container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ask_frag, container, false);

        ivPhoto = (ImageView) rootView.findViewById(R.id.ivPhoto);
        question_container = rootView.findViewById(R.id.question_container);
        etQuestion = (EditText) rootView.findViewById(R.id.question);

        ivPhoto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.v("ivPhoto", "ivPhoto hasFocus: " + hasFocus);
            }
        });

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
        etQuestion.requestFocus();

        View camera = rootView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ∆Ù∂Ø≈ƒ’’
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageDrawable(new BitmapDrawable(getResources(), photo));
        }
    }
}