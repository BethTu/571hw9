package com.example.stocksearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DisplayNewsHeadlinesActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_news_headlines);

		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		
		Intent intent = getIntent();
		String url = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpget);
			String responseText = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(responseText);
			JSONObject result = json.getJSONObject("result");
			JSONArray news = result.getJSONObject("News").getJSONArray("Item");
			List<JSONObject> newsArr = new ArrayList<JSONObject>();
			
			Log.e("checkpoint: ","test test");
////			Activity act = g
//			TextView t = (TextView) this.findViewById(R.id.bidTxt);
//			String str = t.toString() +  "test";
//			Log.e("checkpoint: ", str);
			ListView newsList = (ListView) findViewById(R.id.newsHeadlinesList);
			//Log.e("checkpoint: ", "after instantation: listView - is null");
			NewsArrayAdapter adapter = new NewsArrayAdapter(this, R.layout.temptextview, newsArr);
			newsList.setAdapter(adapter);
			for(int i=0; i < news.length(); i++) {
				newsArr.add(news.getJSONObject(i));
			}
			adapter.notifyDataSetChanged();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_news_headlines, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_display_news_headlines, container, false);
			return rootView;
		}
	}

}
