package com.eldorado.newsfeed.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsSQLiteHelper extends SQLiteOpenHelper {
	 
	private static final String DATABASE_NAME = "news_database";
	private static final int DATABASE_VERSION = 1;
 
    // Table Names
	public static final String TABLE_CHANNEL = "channel";
    public static final String TABLE_NEWS = "news";
 
    // Common column names
    public static final String KEY_ID = "id";
 
    //channel columns
    public static final String KEY_NAME= "name";
 
    // news columns
    public static final String KEY_TITLE = "title";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_HOUR = "hour";
    
    private static final String CREATE_TABLE_CHANNEL = "CREATE TABLE "
            + TABLE_CHANNEL + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME
            + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_NEWS = "CREATE TABLE "
            + TABLE_NEWS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE
            + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_HOUR + " TEXT);";
    
	public NewsSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
