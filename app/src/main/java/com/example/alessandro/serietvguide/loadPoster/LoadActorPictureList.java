package com.example.alessandro.serietvguide.loadPoster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alessandro on 5/30/2016.
 */
public class LoadActorPictureList extends AsyncTask<String, Void, Bitmap> {

	String urlImg;
	static Bitmap result;
	Bitmap act;

	public LoadActorPictureList(Bitmap act, String urlImg){
		super();
		this.act = act;
		this.urlImg = urlImg;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		HttpURLConnection urlConnection = null;
		try {
			URL uri = new URL(urlImg);
			urlConnection = (HttpURLConnection) uri.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			if (inputStream != null) {
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				result = bitmap;
				Log.v("Download ","downloading act img");
				return bitmap;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.w("doInBackground", "Error downloading image from " + urlImg);
		}
		return result;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		act = result;
	}

	public Bitmap getImage(){
		return result;
	}

}
