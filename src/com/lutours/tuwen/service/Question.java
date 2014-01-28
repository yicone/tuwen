package com.lutours.tuwen.service;

/**
 * Created by xdzheng on 14-1-28.
 */
public class Question {
	private long questionId;
	private long userId;
	private String text;
	private byte[] bitmapData;
	private int latitude;
	private int longitude;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getBitmapData() {
		return bitmapData;
	}

	public void setBitmapData(byte[] bitmapData) {
		this.bitmapData = bitmapData;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
}
