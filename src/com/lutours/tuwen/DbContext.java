package com.lutours.tuwen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.lutours.tuwen.service.Question;

import java.util.ArrayList;
import java.util.List;

public class DbContext {
	private static final String DATABASE_NAME = "tuwen_db";
	private static final int DATABASE_VERSION = 1;

	public static final String TABLE_QUESTIONS = "questions";

	public static final String KEY_USER_ID = "userId";
	public static final String KEY_TEXT = "text";
	public static final String KEY_BITMAP = "bitmap";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_QUESTION_ID = "questionId";

	private final Context mContext;
	private DbHelper mDbHelper;
	private SQLiteDatabase mDb;

	public DbContext(Context context) {
		mContext = context;
	}

	public DbContext open() throws SQLException {
		mDbHelper = new DbHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public long create(Question question) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USER_ID, question.getUserId());
		initialValues.put(KEY_TEXT, question.getText());
		initialValues.put(KEY_BITMAP, question.getBitmapData());
		initialValues.put(KEY_LATITUDE, question.getLatitude());
		initialValues.put(KEY_LONGITUDE, question.getLongitude());
		return mDb.insert(TABLE_QUESTIONS, null, initialValues);
	}

	public Question get(long questionId) {
		Question question = null;
		Cursor cursor =
				mDb.query(true, TABLE_QUESTIONS, new String[]{KEY_QUESTION_ID, KEY_USER_ID,
						KEY_TEXT, KEY_BITMAP, KEY_LATITUDE, KEY_LONGITUDE
				}, KEY_QUESTION_ID + "=" + questionId, null,
						null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			question = new Question();
			question.setQuestionId(cursor.getLong(0));
			question.setUserId(cursor.getLong(1));
			question.setText(cursor.getString(2));
			question.setBitmapData(cursor.getBlob(3));
			question.setLatitude(cursor.getInt(4));
			question.setLongitude(cursor.getInt(5));
		}
		return question;
	}

	public List<Question> getAll() {
		List<Question> list = new ArrayList<Question>();
		Cursor cursor = mDb.query(TABLE_QUESTIONS, new String[]{KEY_QUESTION_ID, KEY_USER_ID,
				KEY_TEXT, KEY_BITMAP, KEY_LATITUDE, KEY_LONGITUDE}, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
			Question question;
			while (!cursor.isAfterLast()) {
				question = new Question();
				question.setQuestionId(cursor.getLong(0));
				question.setUserId(cursor.getLong(1));
				question.setText(cursor.getString(2));
				question.setBitmapData(cursor.getBlob(3));
				question.setLatitude(cursor.getInt(4));
				question.setLongitude(cursor.getInt(5));
				list.add(question);
				cursor.moveToNext();
			}
		}
		return list;
	}

	private static class DbHelper extends SQLiteOpenHelper {
		private static final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE questions (" + KEY_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_USER_ID + " INTEGER, "
				+ KEY_TEXT + " TEXT, "
				+ KEY_BITMAP + " BLOB, "
				+ KEY_LATITUDE + " INTEGER, "
				+ KEY_LONGITUDE + " INTEGER);";
		private static final String TAG = "DbContext";

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
			Log.i(TAG, "Creating DataBase: " + SQL_CREATE_QUESTIONS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion);
		}
	}
}
