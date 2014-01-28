package com.lutours.tuwen.service;

import android.content.Context;
import android.location.Location;
import com.lutours.tuwen.DbContext;

import java.util.List;

/**
 * Created by xdzheng on 14-1-28.
 */
public class QuestionServiceImpl implements IQuestionService {

	private DbContext mDbContext;

	public QuestionServiceImpl(Context cxt) {
		mDbContext = new DbContext(cxt);
	}

	@Override
	public List<Question> getQuestions(String keyword, String tag) {
		try {
			mDbContext.open();
			return mDbContext.getAll();
		} finally {
			mDbContext.close();
		}
	}

	@Override
	public List<Question> getQuestions(long userId, Location location) {
		try {
			mDbContext.open();
			return mDbContext.getAll();
		} finally {
			mDbContext.close();
		}
	}

	@Override
	public Question getQuestion(long questionId) {
		try {
			mDbContext.open();
			return mDbContext.get(questionId);
		} finally {
			mDbContext.close();
		}
	}

	@Override
	public void addQuestion(Question question) {
		try {
			mDbContext.open();
			long newId = mDbContext.create(question);
			question.setQuestionId(newId);
		} finally {
			mDbContext.close();
		}

	}

	@Override
	public void addAnswer(long questionId, Answer answer) {

	}

	@Override
	public void commentQuestion(long questionId, Comment comment) {

	}

	@Override
	public void commentAnswer(long answerId, Comment comment) {

	}

	@Override
	public void shareQuestion(long userId, long questionId, ShareWay shareWay) {

	}

	@Override
	public void shareAnswer(long userId, long answerId, ShareWay shareWay) {

	}

	@Override
	public void collectQuestion(long userId, long questionId) {

	}

	@Override
	public void followQuestion(long userId, long questionId) {

	}

	@Override
	public void thanksAnswer(long userId, long answerId) {

	}

	@Override
	public void approveAnswer(long userId, long answerId) {

	}

	@Override
	public void againstAnswer(long userId, long answerId) {

	}
}
