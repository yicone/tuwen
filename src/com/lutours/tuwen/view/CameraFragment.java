package com.lutours.tuwen.view;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.*;
import android.widget.ImageView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragment;
import com.lutours.tuwen.R;

import java.io.IOException;

/**
 * Created by xdzheng on 14-1-26.
 */
public class CameraFragment extends SherlockFragment implements View.OnClickListener, SurfaceHolder.Callback {
    Camera mCamera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            camera.stopPreview();

            // goto DrawingFragment
            Fragment fragment = DrawingFragment.create(data);
            fragment.setTargetFragment(CameraFragment.this, 0);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().add(android.R.id.content, fragment).commitAllowingStateLoss();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.camera_frag, container, false);

        ActionBar actionBar = getSherlockActivity().getSupportActionBar();
        actionBar.setCustomView(R.layout.camera_action_bar);
        View abView = actionBar.getCustomView();
        ImageView ivClose = (ImageView) abView.findViewById(R.id.ivClose);
        ImageView ivReverse = (ImageView) abView.findViewById(R.id.ivReverse);
        ImageView ivLight = (ImageView) abView.findViewById(R.id.ivLight);

        ImageView ivCamera = (ImageView) rootView.findViewById(R.id.ivCamera);

        ivClose.setOnClickListener(this);
        ivReverse.setOnClickListener(this);
        ivLight.setOnClickListener(this);
        ivCamera.setOnClickListener(this);

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
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        previewing = false;
    }
}