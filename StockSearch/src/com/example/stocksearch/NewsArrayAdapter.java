package com.example.stocksearch;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsArrayAdapter extends ArrayAdapter<JSONObject>{
	int resource;
	String response;
	Context context;
	public NewsArrayAdapter(Context context, int resource, List<JSONObject> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		LinearLayout newsView;
		JSONObject news = getItem(position);
		String title = "";
		String link = "";
		try {
			link = news.getString("Link");
			
			title= news.getString("Title");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//inflate the view
		if(convertView == null) {
			newsView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(resource, newsView, true);
        }
        else {
        	newsView = (LinearLayout) convertView;
        }
		
		TextView newsTitle = new TextView(getContext());
		newsTitle.setText(Html.fromHtml("<a href=\"" + link + "\">" + title + "</a>"));
		newsTitle.setMovementMethod(LinkMovementMethod.getInstance());
//		newsView.addView(newsTitle);
		//TODO set setOnclick
		newsTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		newsView.addView(newsTitle);
		return newsView;
	}

}
