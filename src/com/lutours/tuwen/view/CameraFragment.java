package com.lutours.tuwen.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.lutours.tuwen.R;

/**
 * Created by xdzheng on 14-1-26.
 */
public class CameraFragment extends Fragment implements View.OnClickListener {
    private CameraPreview mPreview;

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            mPreview.releaseCamera();

            // goto DrawingFragment
            final FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.setRotate(mPreview.getDegrees());
            int yStart = 0;
            int xStart = (bitmap.getWidth() - bitmap.getHeight()) / 2;
            int width = Math.min(bitmap.getWidth(), bitmap.getHeight());
            int height = width;
            bitmap = Bitmap.createBitmap(bitmap, xStart, yStart, width, height, matrix, true);
            Fragment fragment = DrawingFragment.create(bitmap);
            ft.replace(android.R.id.content, fragment).addToBackStack(null).commit();
            fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (fm.getBackStackEntryCount() == 0) {
                        mPreview.setCamera();
                        mPreview.startPreview();
                    }
                }
            });
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


        // 修复部分相机镜头旋转的问题


        mPreview = new CameraPreview(getActivity());
//        if (minPreviewSize != null) {
//            mPreview.setLayoutParams(new LinearLayout.LayoutParams(minPreviewSize.width, minPreviewSize.height));
//        } else {
//            mPreview.setLayoutParams(new LinearLayout.LayoutParams(min, min));
//        }

        final SquareMaskLayout previewContainer = (SquareMaskLayout) rootView.findViewById(R.id.camera_preview);
        mPreview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mPreview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPreview.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                int w = mPreview.getWidth();
                int h = mPreview.getHeight();

                int delta = h - w;

                RelativeLayout maskContainer = new RelativeLayout(getActivity());
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

                LinearLayout maskLayout;
                RelativeLayout.LayoutParams relativeLayoutParams;

                maskLayout = new LinearLayout(getActivity());
                maskLayout.setBackgroundColor(Color.BLACK);
                maskLayout.setAlpha(0.8f);
                relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, delta / 2);
                relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                maskLayout.setLayoutParams(relativeLayoutParams);
                maskContainer.addView(maskLayout);

                maskLayout = new LinearLayout(getActivity());
                maskLayout.setBackgroundColor(Color.BLACK);
                maskLayout.setAlpha(0.7f);
                relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, delta / 2);
                relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                maskLayout.setLayoutParams(relativeLayoutParams);
                maskContainer.addView(maskLayout);

                maskContainer.setLayoutParams(layoutParams);
                previewContainer.addView(maskContainer);
            }
        });
        previewContainer.addView(mPreview);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPreview.releaseCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPreview.setCamera();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivClose:
                // 将自己出栈
                getActivity().onBackPressed();
                break;
            case R.id.ivReverse:
                break;
            case R.id.ivLight:
                break;
            case R.id.ivCamera:
                mPreview.takePicture(mPictureCallback);
                break;
        }
    }

}