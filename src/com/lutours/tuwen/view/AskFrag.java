package com.lutours.tuwen.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.lutours.tuwen.R;

/**
 * Created by xdzheng on 14-1-16.
 */
public class AskFrag extends Fragment {
	private static final int CAMERA_REQUEST = 1888;
	private ImageView m_ivPhoto;
	private EditText m_etQuestion;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ask_frag, container, false);
		m_ivPhoto = (ImageView) rootView.findViewById(R.id.photo);
		View camera = rootView.findViewById(R.id.camera);
		final View questionContainer = rootView.findViewById(R.id.question_container);
		m_etQuestion = (EditText) rootView.findViewById(R.id.question);

		camera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ∆Ù∂Ø≈ƒ’’
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});
		questionContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Toast.makeText(getActivity(), "hasFocus: " + hasFocus, 0);
				questionContainer.setAnimation(AnimationUtils.loadAnimation(getActivity(), (hasFocus ? R.anim.move_up : R.anim.move_down)));
			}
		});

		m_etQuestion.requestFocus();
		questionContainer.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.move_up));

		return rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				m_ivPhoto.setImageDrawable(new BitmapDrawable(getResources(), photo));
			}
			m_etQuestion.requestFocus();
		}
	}


}