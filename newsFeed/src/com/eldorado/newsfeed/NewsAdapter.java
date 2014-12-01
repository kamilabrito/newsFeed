package com.eldorado.newsfeed;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eldorado.newsfeed.model.Channel;

public class NewsAdapter extends ArrayAdapter<Channel> {
	

	public NewsAdapter(Context context, int i, ArrayList<Channel> objects) {
		super(context, R.layout.list_item, objects);
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent){
		
		View row = convertView;
		Log.i("StackSites", "getView pos = " + pos);
		if(null == row){
			//No recycled View, we have to inflate one.
			LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.list_item, null);
		}
		
		TextView titleTxt = (TextView)row.findViewById(R.id.title);
		TextView categoryTxt = (TextView)row.findViewById(R.id.category);
		TextView hourTxt = (TextView)row.findViewById(R.id.pub_date);
		
		
		titleTxt.setText(getItem(pos).getItem().getTitle());
		categoryTxt.setText(getItem(pos).getItem().getCategory());
		hourTxt.setText(getItem(pos).getItem().getHour());
		
		return row;
				
	}


}
