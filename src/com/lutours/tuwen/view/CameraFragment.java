package com.lutours.tuwen.view;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.*;
import android.widget.ImageView;
import com.lutours.tuwen.R;

import java.io.IOException;

/**
 * Created by xdzheng on 14-1-26.
 */
public class CameraFragment extends Fragment implements View.OnClickListener, SurfaceHolder.Callback {
	private Camera mCamera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private boolean previewing = false;
	private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            camera.stopPreview();

            // goto DrawingFragment
	        FragmentManager fm = getFragmentManager();
	        FragmentTransaction ft = fm.beginTransaction();
	        Fragment fragment = DrawingFragment.create(data);
	        ft.replace(android.R.id.content, fragment).addToBackStack(null).commit();
        }
    };

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_frag, container, false);

        ImageView ivCamera = (ImageView) rootView.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(this);

//	    ActionBar actionBar = getSherlockActivity().getSupportActionBar();
//	    actionBar.setCustomView(R.layout.camera_action_bar);
//	    View abView = actionBar.getCustomView();
		ImageView ivClose = (ImageView) rootView.findViewById(R.id.ivClose);
		ImageView ivReverse = (ImageView) rootView.findViewById(R.id.ivReverse);
		ImageView ivLight = (ImageView) rootView.findViewById(R.id.ivLight);
		ivClose.setOnClickListener(this);
		ivReverse.setOnClickListener(this);
		ivLight.setOnClickListener(this);

		surfaceView = (SurfaceView) rootView.findViewById(R.id.svPreview);
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
                mCamera.stopPreview();
                // 将自己出栈
                getFragmentManager().popBackStackImmediate();
                break;
            case R.id.ivReverse:
                break;
            case R.id.ivLight:
                break;
            case R.id.ivCamera:
                mCamera.takePicture(null, null, mPictureCallback);
                break;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (previewing) {
            mCamera.stopPreview();
            previewing = false;
        }

        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
                previewing = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
	    setCameraDisplayOrientation(getActivity(), Camera.CameraInfo.CAMERA_FACING_BACK, mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        previewing = false;
    }

	// http://stackoverflow.com/questions/4645960/how-to-set-android-camera-orientation-properly
	public static void setCameraDisplayOrientation(Activity activity,
	                                               int cameraId, android.hardware.Camera camera) {
		android.hardware.Camera.CameraInfo info =
				new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraId, info);
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
			case Surface.ROTATION_0: degrees = 0; break;
			case Surface.ROTATION_90: degrees = 90; break;
			case Surface.ROTATION_180: degrees = 180; break;
			case Surface.ROTATION_270: degrees = 270; break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360;  // compensate the mirror
		} else {  // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}
}