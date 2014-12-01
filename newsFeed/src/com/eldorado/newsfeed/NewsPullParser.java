package com.eldorado.newsfeed;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.eldorado.newsfeed.model.Channel;
import com.eldorado.newsfeed.model.News;

public class NewsPullParser {
	static final String KEY_ITEM = "item";
	static final String KEY_CHANNEL = "channel";
	static final String KEY_RSS = "rss";
	static final String KEY_TITLE = "title";
	static final String KEY_CATEGORY = "category";
	static final String KEY_PUBDATE = "pubDate";
	private static final String ns = null;

	public ArrayList<Channel> parse(InputStream in) throws XmlPullParserException,
			IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readRss(parser);
		} finally {
			in.close();
		}
	}

	private ArrayList<Channel> readRss(final XmlPullParser parser)
			throws XmlPullParserException, IOException {
		ArrayList<Channel> channel = new ArrayList<>();
		parser.require(XmlPullParser.START_TAG, ns, KEY_RSS);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equalsIgnoreCase(KEY_CHANNEL)) {
				channel.addAll(makeChannel(readChannel(parser)));
			} else {
				skip(parser);
			}
		}
		return channel;
	}
	
	private ArrayList <Channel> makeChannel (ArrayList <News> news) {
		ArrayList<Channel> channel = new ArrayList<>();
		for (int i = 0; i < news.size(); i++) {
			News myNews = new News(news.get(i).getTitle(), news.get(i).getCategory(), news.get(i).getHour());
			Channel myChannel = new Channel();
			myChannel.setItem(myNews);
			channel.add(myChannel);
		}
		return channel;
		
	}
	
	private ArrayList<News> readChannel(final XmlPullParser parser)
			throws XmlPullParserException, IOException {
		ArrayList<News> entries = new ArrayList<News>();
		parser.require(XmlPullParser.START_TAG, ns, KEY_CHANNEL);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equalsIgnoreCase(KEY_ITEM)) {
				entries.add(readItem(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	private News readItem(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		parser.require(XmlPullParser.START_TAG, ns, KEY_ITEM);
		String title = null;
		String category = null;
		String hour = null;
		
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equalsIgnoreCase(KEY_TITLE)) {
				title = readTag(parser, KEY_TITLE);
			} else if (name.equalsIgnoreCase(KEY_CATEGORY)) {
				category = readTag(parser, KEY_CATEGORY);
			} else if (name.equalsIgnoreCase(KEY_PUBDATE)) {
				hour = readTag(parser, KEY_PUBDATE);
			} else {
				skip(parser);
			}
		}
		return new News(title, category, hour);
	}

	// Processes title tags in the feed.
	private String readTag(XmlPullParser parser, String Key)
			throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, Key);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, Key);
		return title;
	}

	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}
