package com.lutours.tuwen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        final List<Question> questions = svr.getQuestions(null, null);
        ImageAdapter adapter = new ImageAdapter(getActivity(), questions);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Question question = questions.get(position);
                QuestionFragment frag = QuestionFragment.create(question);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(android.R.id.content, frag).addToBackStack(null).commit();
            }
        });

        return rootView;
    }

    private static class ImageAdapter extends ArrayAdapter<Question> {
        List<Question> mQuestions;

        public ImageAdapter(Context context, List<Question> questions) {
            super(context, 0, questions);
            mQuestions = questions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                holder = new Holder();
                SquareImageView imageView = new SquareImageView(getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //imageView.setPadding(8, 8, 8, 8);
                holder.setImageView(imageView);
                convertView = imageView;
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            byte[] data = mQuestions.get(position).getBitmapData();
            holder.getImageView().setBitmapData(data);

            return convertView;
        }

        private static class Holder {
            private SquareImageView imageView;

            public SquareImageView getImageView() {
                return imageView;
            }

            public void setImageView(SquareImageView imageView) {
                this.imageView = imageView;
            }
        }
    }
}
