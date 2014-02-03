package com.lutours.tuwen.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lutours.tuwen.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by xdzheng on 14-1-16.
 */
public class DrawingFragment extends Fragment implements View.OnClickListener {
	private static final int CAMERA_REQUEST = 1888;
	private static final String KEY_BITMAP_DATA = "KEY_BITMAP_DATA";
	private View rootView;

	private DrawingView dvCanvas;
	private Bitmap mBitmap;

	public static DrawingFragment create(Bitmap bitmap) {
		DrawingFragment fragment = new DrawingFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_BITMAP_DATA, bitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        mBitmap = args.getParcelable(KEY_BITMAP_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.drawing_frag, container, false);

        dvCanvas = (DrawingView) rootView.findViewById(R.id.dvCanvas);
        dvCanvas.setImageBitmap(mBitmap);
	    dvCanvas.setDrawingCacheEnabled(true);

//        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
//        actionBar.setCustomView(R.layout.drawing_action_bar);
//        View actionBarView = actionBar.getCustomView();
	    View btnBack = rootView.findViewById(R.id.back_button);
	    View btnNext = rootView.findViewById(R.id.next_button);
	    btnBack.setOnClickListener(this);
	    btnNext.setOnClickListener(this);

	    return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
	        case R.id.back_button: {
		        // ªÿµΩ≈ƒ’’“≥
		        FragmentManager fm = getFragmentManager();
		        fm.popBackStack();

		        FragmentTransaction ft = fm.beginTransaction();
		        ft.remove(DrawingFragment.this);
		        ft.commit();
		        break;
	        }
	        case R.id.next_button: {

		        FragmentManager fm = getFragmentManager();
		        FragmentTransaction ft = fm.beginTransaction();

		        // get bytes
		        Bitmap bmp = dvCanvas.getDrawingCache();
		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		        byte[] byteArray = stream.toByteArray();

		        AnswerFragment frag = AnswerFragment.create(byteArray);
		        ft.replace(android.R.id.content, frag);
		        ft.addToBackStack(null);
		        ft.commit();
		        break;
	        }
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
        }
    }
}