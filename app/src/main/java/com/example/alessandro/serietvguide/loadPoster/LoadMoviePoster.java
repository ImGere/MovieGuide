package com.example.alessandro.serietvguide.loadPoster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.activity.MovieActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Classe per scaricare un'immagine dal link dell'API
 * @author Alessandro
 *
 */
public class LoadMoviePoster extends AsyncTask<String, Void, Bitmap> {

	private String img;
	private MovieActivity m;
	private Bitmap poster;

	public LoadMoviePoster(MovieActivity m, String img){
		super();
		this.m = m;
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
					poster = BitmapFactory.decodeStream(inputStream);
				} else {
					poster = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			poster = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
		}
		return poster;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		m.setPosterImage(result);
	}
}
