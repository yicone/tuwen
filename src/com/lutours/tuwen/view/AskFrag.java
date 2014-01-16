package com.lutours.tuwen.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lutours.tuwen.R;

/**
 * Created by xdzheng on 14-1-16.
 */
public class AskFrag extends Fragment {
	private static final int CAMERA_REQUEST = 1888;
	private View m_rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		m_rootView = inflater.inflate(R.layout.ask_frag, container, false);
		View camera = m_rootView.findViewById(R.id.camera);
		camera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ∆Ù∂Ø≈ƒ’’
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
		});
		return m_rootView;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			m_rootView.setBackground(new BitmapDrawable(getResources(), photo));
		}
	}
}