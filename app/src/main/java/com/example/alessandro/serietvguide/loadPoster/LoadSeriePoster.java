package com.example.alessandro.serietvguide.loadPoster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.activity.SerieActivity;

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
public class LoadSeriePoster extends AsyncTask<String, Void, Bitmap> {

	private String img;
	private SerieActivity s;
	private Bitmap epImage;

	public LoadSeriePoster(SerieActivity s, String img){
		super();
		this.s = s;
		this.img=img;
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
					epImage = BitmapFactory.decodeStream(inputStream);
				} else {
					epImage = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else{
			epImage = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
		}
		return epImage;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		s.setPosterImage(result);
	}
}
