package com.lutours.tuwen.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.ask_frag, container, false);

        ivPhoto = (ImageView) rootView.findViewById(R.id.ivPhoto);


        final View question_container = rootView.findViewById(R.id.question_container);
        question_container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.v("AskFrag", "question_container.setOnFocusChange: " + b);
                question_container.startAnimation(AnimationUtils.loadAnimation(getActivity(), b ? R.anim.move_up : R.anim.move_down));
            }
        });

        EditText etQuestion = (EditText)rootView.findViewById(R.id.question);
        etQuestion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.v("AskFrag", "etQuestion.setOnFocusChange: " + b);
                question_container.startAnimation(AnimationUtils.loadAnimation(getActivity(), b ? R.anim.move_up : R.anim.move_down));
            }
        });

//        Button btnTestAnim = (Button) rootView.findViewById(R.id.btnTestAnim);
//        btnTestAnim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                question_container.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move_up));
//            }
//        });

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