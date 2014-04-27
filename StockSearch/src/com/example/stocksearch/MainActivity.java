package com.example.stocksearch;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	public final static String EXTRA_MESSAGE = "com.example.stocksearch.MESSAGE";
	public JSONObject result;
	public String url="";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	/** Called when the user clicks the search button */
	public void searchStockInfo(View view) {
	    // Do something in response to button
		AutoCompleteTextView text = (AutoCompleteTextView) findViewById(R.id.companySymbol);
		String companySymbol = text.getText().toString().trim();
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText(companySymbol);
		HttpClient httpClient = new DefaultHttpClient();
		url = "http://cs-server.usc.edu:34179/examples/servlet/MyServlet?company_symbol="+companySymbol;
		HttpGet httpget = new HttpGet(url);
		JSONObject json = new JSONObject();
		try {
			HttpResponse response = httpClient.execute(httpget);
			String responseText = EntityUtils.toString(response.getEntity());
			json = new JSONObject(responseText);
			result = json.getJSONObject("result");
			displayStockInfo();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void displayStockInfo() {
		if(result!=null) {
			try {
				JSONObject quote = result.getJSONObject("Quote");
				String previousClose = quote.getString("PreviousClose");
				TextView previousCloseView = (TextView) findViewById(R.id.previousClose);
				previousCloseView.setText(previousClose);
				
				String bid = quote.getString("Bid");
				TextView bidView = (TextView) findViewById(R.id.bid);
				bidView.setText(bid);
				
				String ask = quote.getString("Ask");
				TextView askView = (TextView) findViewById(R.id.ask);
				askView.setText(ask);
				
				String volume = quote.getString("Volume");
				TextView volumeView = (TextView) findViewById(R.id.volume);
				volumeView.setText(volume);
				
				String open = quote.getString("Open");
				TextView openView = (TextView) findViewById(R.id.open);
				openView.setText(open);
				
				String daysLow = quote.getString("DaysLow");
				String daysHigh = quote.getString("DaysHigh");
				TextView dayRangeView = (TextView) findViewById(R.id.dayRange);
				dayRangeView.setText(daysLow + " " + daysHigh);
				
				String yearLow = quote.getString("YearLow");
				String yearHigh = quote.getString("YearHigh");
				TextView yearRangeView = (TextView) findViewById(R.id.yearRange);
				yearRangeView.setText(yearLow + " " + yearHigh);
				
				String oneYearTargetPrice = quote.getString("OneYearTargetPrice");
				TextView oneYearTargetPriceView = (TextView) findViewById(R.id.oneYearTargetPrice);
				oneYearTargetPriceView.setText(oneYearTargetPrice);
				
				String marketCapitalization = quote.getString("MarketCapitalization");
				TextView marketCapitalizationView = (TextView) findViewById(R.id.marketCapitalization);
				marketCapitalizationView.setText(marketCapitalization);
				
				String averageDailyVolume = quote.getString("AverageDailyVolume");
				TextView averageDailyVolumeView = (TextView) findViewById(R.id.averageDailyVolume);
				averageDailyVolumeView.setText(averageDailyVolume);
				
				ImageView imageView = (ImageView) findViewById(R.id.stockImage);
				String stockImage = result.getString("StockChartImageURL");
				URL imageURL = new URL(stockImage);
				Bitmap image = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
				imageView.setImageBitmap(image);
				TableLayout stockInfo = (TableLayout) findViewById(R.id.stockInfo);
				stockInfo.setVisibility(android.view.View.VISIBLE);
				imageView.setVisibility(android.view.View.VISIBLE);
				Button newsHeadlinesBtn = (Button) findViewById(R.id.news);
				newsHeadlinesBtn.setVisibility(android.view.View.VISIBLE);
				Button facebookBtn = (Button) findViewById(R.id.facebook);
				facebookBtn.setVisibility(android.view.View.VISIBLE);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
//	public void executeQuery(String companySymbol) {
//		HttpClient httpClient = new DefaultHttpClient();
//		String url = "http://cs-server.usc.edu:34179/examples/servlet/MyServlet?company_symbol="+companySymbol;
//		HttpGet httpget = new HttpGet(url);
//		try {
//			HttpResponse response = httpClient.execute(httpget);
//			String result = response.toString();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	@SuppressLint("NewApi")
	public void displayNews(View view) {
		if(!url.isEmpty()) {
			Intent intent = new Intent(this, DisplayNewsHeadlinesActivity.class);
			intent.putExtra(EXTRA_MESSAGE, url);
			startActivity(intent);
		}
//		startActivity(intent);
	}

}
