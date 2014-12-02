package com.eldorado.newsfeed;

/**
 * Class to determinate news card layout
 * 
 * @author kamilabrito
 */


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eldorado.newsfeed.model.News;

public class NewsAdapter extends ArrayAdapter<News> {
	
	public NewsAdapter(Context context, int i, ArrayList<News> objects) {
		super(context, R.layout.list_item, objects);
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {

		View row = convertView;
		Log.i("StackSites", "getView pos = " + pos);
		if (null == row) {
			LayoutInflater inflater = (LayoutInflater) parent.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.list_item, null);
		}

		TextView titleTxt = (TextView) row.findViewById(R.id.title);
		TextView categoryTxt = (TextView) row.findViewById(R.id.category);
		TextView hourTxt = (TextView) row.findViewById(R.id.pub_date);

		titleTxt.setText(getItem(pos).getTitle());
		categoryTxt.setText("#" + getItem(pos).getCategory());
		hourTxt.setText(calculateTimeDifference(getItem(pos).getHour(), parent.getContext()));

		return row;

	}
	
	private String calculateTimeDifference(String pub_date, Context context) {
		
		String timeSincePublished = "";
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss zzzz", Locale.ENGLISH);
		String currentDateandTime = format.format(new Date());

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(pub_date);
			d2 = format.parse(currentDateandTime);

			long diff = d2.getTime() - d1.getTime();

			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			if (diffDays>0) {
				timeSincePublished = timeSincePublished +" "+ diffDays +" "+ context.getString(R.string.days);
			}
			if (diffHours>=0) {
				timeSincePublished = timeSincePublished +" "+ diffHours +" "+ context.getString(R.string.hours);
			}
			if (diffMinutes>=0) {
				timeSincePublished = timeSincePublished +" "+ diffMinutes +" "+ context.getString(R.string.minutes);
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}

		return timeSincePublished;
	}

}
