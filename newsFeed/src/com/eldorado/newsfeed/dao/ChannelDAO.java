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

public class ChannelDAO {

	// Database fields
	private SQLiteDatabase database;
	private NewsSQLiteHelper dbHelper;
	private String[] allColumns = { NewsSQLiteHelper.KEY_ID,
			NewsSQLiteHelper.KEY_NAME };

	public ChannelDAO(Context context) {
		dbHelper = new NewsSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Channel createChannel(String name) {
		ContentValues values = new ContentValues();
		values.put(NewsSQLiteHelper.KEY_NAME, name);
		long insertId = database.insert(NewsSQLiteHelper.TABLE_CHANNEL, null,
				values);
		Cursor cursor = database.query(NewsSQLiteHelper.TABLE_CHANNEL,
				allColumns, NewsSQLiteHelper.KEY_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Channel newChannel = cursorToChannel(cursor);
		cursor.close();
		return newChannel;
	}

	public void deleteComment(Channel channel) {
		long id = channel.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(NewsSQLiteHelper.TABLE_CHANNEL, NewsSQLiteHelper.KEY_ID
				+ " = " + id, null);
	}

	public List<Channel> getAllChannels() {
		List<Channel> channels = new ArrayList<Channel>();

		Cursor cursor = database.query(NewsSQLiteHelper.TABLE_CHANNEL,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Channel channel = cursorToChannel(cursor);
			channels.add(channel);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return channels;
	}
	
	public void deleteTableContent() {
		database.delete(NewsSQLiteHelper.TABLE_CHANNEL, null, null);
	}

	private Channel cursorToChannel(Cursor cursor) {
		Channel channel = new Channel();
		channel.setId(cursor.getLong(0));
		channel.setName(cursor.getString(1));
		return channel;
	}
}
