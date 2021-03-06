package com.eldorado.newsfeed.dao;

/**
 * Dao for table channel
 * 
 * @author kamilabrito
 */


import java.util.ArrayList;

/**
 * Dao for table channel
 * 
 * @author kamilabrito
 */
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.eldorado.newsfeed.constants.Constants;
import com.eldorado.newsfeed.database.NewsSQLiteHelper;
import com.eldorado.newsfeed.model.Channel;

public class ChannelDAO {

	private SQLiteDatabase database;
	private NewsSQLiteHelper dbHelper;
	private String[] allColumns = { Constants.KEY_ID,
			Constants.KEY_NAME };

	public ChannelDAO(Context context) {
		dbHelper = new NewsSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	//insert object on channel table
	public Channel createChannel(String name) {
		ContentValues values = new ContentValues();
		values.put(Constants.KEY_NAME, name);
		long insertId = database.insert(Constants.TABLE_CHANNEL, null,
				values);
		Cursor cursor = database.query(Constants.TABLE_CHANNEL,
				allColumns, Constants.KEY_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Channel newChannel = cursorToChannel(cursor);
		cursor.close();
		return newChannel;
	}
	
	//delete a specific channel object
	public void deleteChannel(Channel channel) {
		long id = channel.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(Constants.TABLE_CHANNEL, Constants.KEY_ID
				+ " = " + id, null);
	}

	//return list with all the content from channel table
	public List<Channel> getAllChannels() {
		List<Channel> channels = new ArrayList<Channel>();

		Cursor cursor = database.query(Constants.TABLE_CHANNEL,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Channel channel = cursorToChannel(cursor);
			channels.add(channel);
			cursor.moveToNext();
		}
		cursor.close();
		return channels;
	}
	
	//delete ALL table content
	public void deleteTableContent() {
		database.delete(Constants.TABLE_CHANNEL, null, null);
	}

	private Channel cursorToChannel(Cursor cursor) {
		Channel channel = new Channel();
		channel.setId(cursor.getLong(0));
		channel.setName(cursor.getString(1));
		return channel;
	}
}
