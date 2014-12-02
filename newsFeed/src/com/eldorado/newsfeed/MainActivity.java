package com.eldorado.newsfeed;

/**
 * Main activity
 * 
 * @author kamilabrito
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.eldorado.newsfeed.constants.Constants;
import com.eldorado.newsfeed.dao.ChannelDAO;
import com.eldorado.newsfeed.dao.NewsDAO;
import com.eldorado.newsfeed.model.Channel;
import com.eldorado.newsfeed.model.News;

public class MainActivity extends Activity {

	private SwipeRefreshLayout swipeLayout;
	private NewsAdapter mAdapter;
	private ListView listView;
	private ArrayList<Channel> channel;
	
	private ChannelDAO channelDao;
	private NewsDAO newsDao;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setTitle(R.string.news); 
		
		channelDao = new ChannelDAO(this);
		channelDao.open();
		
		newsDao = new NewsDAO(this);
		newsDao.open();

		listView = (ListView) findViewById(R.id.list);
		listView.setDividerHeight(0);
		listView.setDivider(null);
		
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(onRefreshListener);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		
		//load content from database
		mAdapter = new NewsAdapter(MainActivity.this, -1,readFromDataBase());
		listView.setAdapter(mAdapter);
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (swipeLayout.isRefreshing()) {
			if (swipeLayout.isRefreshing()) {
				swipeLayout.setRefreshing(false);
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//load content from database
		mAdapter = new NewsAdapter(MainActivity.this, -1,readFromDataBase());
		listView.setAdapter(mAdapter);
	}
	
	private OnRefreshListener onRefreshListener = new OnRefreshListener() {

		@Override
		public void onRefresh() {

			if (isNetworkAvailable()) {
				new GetNews().execute(Constants.url);

			} else {
				 mAdapter = new NewsAdapter(MainActivity.this, -1,readFromDataBase());
				 listView.setAdapter(mAdapter);
				 Toast.makeText(getApplicationContext(), getApplication().getString(R.string.no_internet),
						   Toast.LENGTH_LONG).show();
				 if (swipeLayout.isRefreshing()) {
						swipeLayout.setRefreshing(false);
				}
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetNews extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... url) {
			return DownloadFromUrl(url[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			saveToDataBase();
			if (swipeLayout.isRefreshing()) {
				swipeLayout.setRefreshing(false);
			}
			 mAdapter = new NewsAdapter(MainActivity.this, -1,readFromDataBase());
			 listView.setAdapter(mAdapter);
		}

	}
	
	//retrieve database content
	private ArrayList<News> readFromDataBase() {
		ArrayList<News> newss = new ArrayList<>(); 
		newss = newsDao.getAllNews();
		return newss;
	}
	
	//update database content
	private void saveToDataBase() {
		newsDao.deleteTableContent();
		channelDao.deleteTableContent();
		for (Channel chnl: channel) {
			chnl.setName("channel1");
			channelDao.createChannel(chnl.getName());
			newsDao.createNews(chnl.getItem().getTitle(), chnl.getItem().getCategory(), chnl.getItem().getHour());
		}
	}

	//check internet connection
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private String DownloadFromUrl(String urlString) { // this
		// method
		InputStream stream = null;
		// Instantiate the parser
		NewsPullParser newsParser = new NewsPullParser();
		channel = new ArrayList<>();

		try {
			try {
				stream = downloadUrl(urlString);
			} catch (IOException e) {
				e.printStackTrace();
			}
			channel = newsParser.parse(stream);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return " ";
	}

	private InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000);
		conn.setConnectTimeout(15000);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		return conn.getInputStream();
	}

}
