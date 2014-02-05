package com.lutours.tuwen.service;

import android.location.Location;

import java.util.List;

/**
 * Created by xdzheng on 14-1-28.
 */
public interface IQuestionService {
	List<Question> getQuestions(String keyword, String tag);

	List<Question> getQuestions(long userId, Location location);

	Question getQuestion(long questionId);


	void addQuestion(Question question);

	void addAnswer(long questionId, Answer answer);

    List<Answer> getAnswers(long questionId);

	void commentQuestion(long questionId, Comment comment);

	void commentAnswer(long answerId, Comment comment);


	void shareQuestion(long userId, long questionId, ShareWay shareWay);

	void shareAnswer(long userId, long answerId, ShareWay shareWay);

	// �ղ�
	void collectQuestion(long userId, long questionId);
	// ��ע
	void followQuestion(long userId, long questionId);

	// ��л
	void thanksAnswer(long userId, long answerId);
	// �޳�
	void approveAnswer(long userId, long answerId);
	// ����
	void againstAnswer(long userId, long answerId);
}
