package com.lutours.tuwen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.lutours.tuwen.R;
import com.lutours.tuwen.service.IQuestionService;
import com.lutours.tuwen.service.Question;
import com.lutours.tuwen.service.QuestionServiceImpl;

import java.util.List;

/**
 * 问题列表页
 */
public class QuestionListFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.question_list_frag, container, false);
		GridView gridView = (GridView) rootView.findViewById(R.id.gridView);

		IQuestionService svr = new QuestionServiceImpl(getActivity());
		List<Question> questions = svr.getQuestions(null, null);
		Adapter adapter = new Adapter(getActivity(), questions);
		gridView.setAdapter(adapter);

		return rootView;
	}

	private static class Adapter extends ArrayAdapter<Question> {

		public Adapter(Context context, List<Question> questions) {
			super(context, 0, questions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_list_item, parent, false);
			Question item = this.getItem(position);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
			Bitmap bitmap = BitmapFactory.decodeByteArray(item.getBitmapData(), 0, item.getBitmapData().length);
			imageView.setImageBitmap(bitmap);

			return convertView;
		}
	}
}
