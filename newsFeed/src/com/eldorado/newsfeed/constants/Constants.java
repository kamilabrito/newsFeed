package com.eldorado.newsfeed.constants;

public class Constants {
	
	
	//parser constants
	public static final String TAG_CHANNEL = "channel";
	public static String url = "http://www.melhordovolei.com.br/index.php/noticias/nacional?format=feed";
	public static final String KEY_ITEM = "item";
	public static final String KEY_CHANNEL = "channel";
	public static final String KEY_RSS = "rss";
	public static final String KEY_TITLE = "title";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_PUBDATE = "pubDate";
	
	
	//database constants
	public static final String DATABASE_NAME = "news_database";
	public static final int DATABASE_VERSION = 1;
 
    // Table Names
	public static final String TABLE_CHANNEL = "channel";
    public static final String TABLE_NEWS = "news";
 
    // Common column names
    public static final String KEY_ID = "id";
 
    //channel columns
    public static final String KEY_NAME= "name";
 
    // news columns
    public static final String KEY_HOUR_COLUMN = "hour";
}


