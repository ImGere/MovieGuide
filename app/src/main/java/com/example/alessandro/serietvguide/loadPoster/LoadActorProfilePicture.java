package com.example.alessandro.serietvguide.loadPoster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.activity.ActorActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alessandro on 6/1/2016.
 */
public class LoadActorProfilePicture extends AsyncTask<String, Void, Bitmap> {

	String img;
	ActorActivity a;
	Bitmap profilePhoto;

	public LoadActorProfilePicture(ActorActivity a, String img){
		super();
		this.a = a;
		this.img = img;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		HttpURLConnection urlConnection = null;
		if(!img.equals("")) {
			try {
				URL uri = new URL(img);
				urlConnection = (HttpURLConnection) uri.openConnection();
				InputStream inputStream = urlConnection.getInputStream();
				if (inputStream != null) {
					profilePhoto = BitmapFactory.decodeStream(inputStream);
				} else {
					profilePhoto = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			profilePhoto = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
		}
		return profilePhoto;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		a.setPosterImage(result);
	}
}
