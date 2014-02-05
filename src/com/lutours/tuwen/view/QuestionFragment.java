package com.lutours.tuwen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.lutours.tuwen.R;
import com.lutours.tuwen.service.Answer;
import com.lutours.tuwen.service.IQuestionService;
import com.lutours.tuwen.service.Question;
import com.lutours.tuwen.service.QuestionServiceImpl;

import java.util.List;

/**
 * Created by apple on 14-2-4.
 */
public class QuestionFragment extends Fragment {
    private static final String KEY_QUESTION = "KEY_QUESTION";
    private Question mQuestion;

    public static QuestionFragment create(Question question) {
        QuestionFragment frag = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_QUESTION, question);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = this.getArguments();
        mQuestion = (Question) args.getSerializable(KEY_QUESTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.question_frag, container, false);

        ListView lvAnswers = (ListView) rootView.findViewById(R.id.lvAnswers);

        View header = inflater.inflate(R.layout.answer_list_header, null);
        TextView tvQuestion = (TextView) header.findViewById(R.id.tvQuestion);
        SquareImageView ivPhoto = (SquareImageView) header.findViewById(R.id.ivPhoto);
        tvQuestion.setText(mQuestion.getText());
        ivPhoto.setBitmapData(mQuestion.getBitmapData());
        lvAnswers.addHeaderView(header);

        IQuestionService svr = new QuestionServiceImpl(getActivity());
        List<Answer> list = svr.getAnswers(mQuestion.getQuestionId());
        AnswerListAdapter adapter = new AnswerListAdapter(getActivity(), list);
        lvAnswers.setAdapter(adapter);

        return rootView;
    }

    private static class AnswerListAdapter extends ArrayAdapter<Answer> {
        private List<Answer> mAnswerList;

        public AnswerListAdapter(Context context, List<Answer> objects) {
            super(context, 0, objects);

            mAnswerList = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.answer_list_item, parent, false);
                holder = new Holder();
                holder.tvNickname = (TextView) convertView.findViewById(R.id.tvNickname);
                holder.ivAvatar = (ImageView) convertView.findViewById(R.id.ivAvatar);
                holder.tvApproveCount = (TextView) convertView.findViewById(R.id.tvApproveCount);
                holder.tvAnswerText = (TextView) convertView.findViewById(R.id.tvAnswerText);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();

            }

            Answer answer = mAnswerList.get(position);
            holder.tvNickname.setText(answer.nickname);
            holder.tvAnswerText.setText(answer.text);
            holder.tvApproveCount.setText(answer.approveCount + "");
            // todo
            //holder.ivAvatar.setImage(null);


            return convertView;
        }

        private static class Holder {
            public TextView tvNickname;
            public ImageView ivAvatar;
            public TextView tvApproveCount;
            public TextView tvAnswerText;
        }
    }
}