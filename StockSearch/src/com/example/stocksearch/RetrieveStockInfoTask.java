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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class RetrieveStockInfoTask extends AsyncTask<String, Void, JSONObject>{

	MainActivity caller;
	public RetrieveStockInfoTask(MainActivity activity) {
		// TODO Auto-generated constructor stub
		this.caller = activity;
	}
	@Override
	protected JSONObject doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
		String url = "http://cs-server.usc.edu:34179/examples/servlet/MyServlet?company_symbol="+params[0];
		HttpGet httpget = new HttpGet(url);
		JSONObject json = new JSONObject();
		try {
			HttpResponse response = httpClient.execute(httpget);
			String responseText = EntityUtils.toString(response.getEntity());
			json = new JSONObject(responseText);
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}
	
	protected void onPostExecute(JSONObject json) {
		JSONObject result;
		Bitmap image;
		try {
			result = json.getJSONObject("result");
			String stockImage = result.getString("StockChartImageURL");
			URL imageURL = new URL(stockImage);
			image = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
//			caller.displayStockInfo(result,image);
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
