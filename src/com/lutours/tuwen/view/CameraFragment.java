package com.lutours.tuwen.view;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;
import com.lutours.tuwen.R;

import java.io.IOException;

/**
 * Created by xdzheng on 14-1-26.
 */
public class CameraFragment extends Fragment implements View.OnClickListener, SurfaceHolder.Callback  {
	Camera camera;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean previewing = false;
	private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			camera.stopPreview();

			// goto DrawingFragment
			Toast.makeText(getActivity(), "≈ƒ∫√¡À", 0).show();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.camera_frag, container, false);

		ImageView ivCamera = (ImageView) rootView.findViewById(R.id.ivCamera);
		ivCamera.setOnClickListener(this);

		surfaceView = (SurfaceView)rootView.findViewById(R.id.svPreview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.ivClose:
				break;
			case R.id.ivReverse:
				break;
			case R.id.ivLight:
				break;
			case R.id.ivCamera:
				Toast.makeText(getActivity(), "ﬂ«‡Í", 0).show();
				camera.takePicture(null, null, mPictureCallback);
				break;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(previewing){
			camera.stopPreview();
			previewing = false;
		}

		if (camera != null){
			try {
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
				previewing = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
		previewing = false;
	}
}