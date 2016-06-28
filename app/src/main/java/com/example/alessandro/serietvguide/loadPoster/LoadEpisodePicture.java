package com.example.alessandro.serietvguide.loadPoster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.adapter.EpisodeAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alessandro on 5/30/2016.
 */
public class LoadEpisodePicture extends AsyncTask<String, Void, Bitmap> {

	private String urlImg;
	private EpisodeAdapter ea;
	private static Bitmap result;
	private ImageView episodePic;

	public LoadEpisodePicture(EpisodeAdapter ea, ImageView episodePic, String urlImg){
		super();
		this.ea = ea;
		this.urlImg = urlImg;
		this.episodePic = episodePic;
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
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.w("doInBackground", "Error downloading image from " + urlImg);
		}
		return null;
	}
	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if (episodePic != null) {
			ImageView imageView = episodePic;
			if (imageView != null) {
				if (result != null) {
					imageView.setImageBitmap(result);
				} else {
					Drawable placeholder = imageView.getContext().getResources().getDrawable(R.mipmap.ic_photo);
					imageView.setImageDrawable(placeholder);
				}
			}
		}
	}

	public Bitmap getImage(){
		return result;
	}

}
