package com.eldorado.newsfeed.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.eldorado.newsfeed.database.NewsSQLiteHelper;
import com.eldorado.newsfeed.model.Channel;
import com.eldorado.newsfeed.model.News;

public class NewsDAO {

	// Database fields
	private SQLiteDatabase database;
	private NewsSQLiteHelper dbHelper;
	private String[] allColumns = {NewsSQLiteHelper.KEY_TITLE, NewsSQLiteHelper.KEY_CATEGORY, NewsSQLiteHelper.KEY_HOUR};

	public NewsDAO(Context context) {
		dbHelper = new NewsSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public News createNews(String title, String category, String hour) {
		ContentValues values = new ContentValues();
		values.put(NewsSQLiteHelper.KEY_TITLE, title);
		values.put(NewsSQLiteHelper.KEY_CATEGORY, category);
		values.put(NewsSQLiteHelper.KEY_HOUR, hour);
		long insertId = database.insert(NewsSQLiteHelper.TABLE_NEWS, null,
				values);
		Cursor cursor = database.query(NewsSQLiteHelper.TABLE_NEWS,
				allColumns, NewsSQLiteHelper.KEY_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		News newNews = cursorToNews(cursor);
		cursor.close();
		return newNews;
	}

	public void deleteComment(News news) {
		long id = news.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(NewsSQLiteHelper.TABLE_NEWS, NewsSQLiteHelper.KEY_ID
				+ " = " + id, null);
	}

	public ArrayList<News> getAllNews() {
		ArrayList<News> newss = new ArrayList<>();

		Cursor cursor = database.query(NewsSQLiteHelper.TABLE_NEWS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			News news = cursorToNews(cursor);
			newss.add(news);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return newss;
	}
	
	public void deleteTableContent() {
		database.delete(NewsSQLiteHelper.TABLE_NEWS, null, null);
	}

	private News cursorToNews(Cursor cursor) {
		News news = new News();
		news.setTitle(cursor.getString(0));
		news.setCategory(cursor.getString(1));
		news.setHour(cursor.getString(2));
		return news;
	}
}
