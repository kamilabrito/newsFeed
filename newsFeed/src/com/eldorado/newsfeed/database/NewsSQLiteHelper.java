package com.eldorado.newsfeed.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eldorado.newsfeed.constants.Constants;

public class NewsSQLiteHelper extends SQLiteOpenHelper {
	 
    private static final String CREATE_TABLE_CHANNEL = "CREATE TABLE "
            + Constants.TABLE_CHANNEL + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_NAME
            + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_NEWS = "CREATE TABLE "
            + Constants.TABLE_NEWS + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.KEY_TITLE
            + " TEXT," + Constants.KEY_CATEGORY + " TEXT," + Constants.KEY_HOUR_COLUMN + " TEXT);";
    
	public NewsSQLiteHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CHANNEL);
		db.execSQL(CREATE_TABLE_NEWS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		    db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CHANNEL);
		    db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_NEWS);
		    onCreate(db);

	}

}
