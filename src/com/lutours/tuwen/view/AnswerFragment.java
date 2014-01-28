package com.lutours.tuwen.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.lutours.tuwen.R;
import com.lutours.tuwen.service.IQuestionService;
import com.lutours.tuwen.service.Question;
import com.lutours.tuwen.service.QuestionServiceImpl;

/**
 * Created by apple on 14-1-26.
 */
public class AnswerFragment extends Fragment implements View.OnClickListener {

	private static final String KEY_BITMAP_DATA = "KEY_BITMAP_DATA";
	private byte[] mBitmapData;
	private EditText etQuestion;

	private AnswerFragment() {
	}

	public static AnswerFragment create(byte[] bitmapData) {
		AnswerFragment frag = new AnswerFragment();
		Bundle args = new Bundle();
		args.putByteArray(KEY_BITMAP_DATA, bitmapData);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = this.getArguments();
		mBitmapData = args.getByteArray(KEY_BITMAP_DATA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.answer_frag, container, false);

		ImageView ivPhoto = (ImageView) rootView.findViewById(R.id.ivPhoto);
		Bitmap bitmap = BitmapFactory.decodeByteArray(mBitmapData, 0, mBitmapData.length);
		ivPhoto.setImageBitmap(bitmap);

		etQuestion = (EditText) rootView.findViewById(R.id.etQuestion);

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
		        FragmentManager fm = getFragmentManager();
	            fm.popBackStack();

	            FragmentTransaction ft = fm.beginTransaction();
	            ft.remove(AnswerFragment.this);
	            ft.commit();
	            break;
	        }
	        case R.id.next_button: {
		        IQuestionService svr = new QuestionServiceImpl(getActivity());
		        Question question = new Question();
		        question.setUserId(123456);
		        question.setText(etQuestion.getText().toString());
		        question.setBitmapData(mBitmapData);

		        svr.addQuestion(question);
		        break;
	        }
        }
    }
}