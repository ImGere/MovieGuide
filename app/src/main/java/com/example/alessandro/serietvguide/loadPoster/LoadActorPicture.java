package com.example.alessandro.serietvguide.loadPoster;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.alessandro.serietvguide.adapter.CastAdapter;
import com.example.alessandro.serietvguide.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alessandro on 5/30/2016.
 */
public class LoadActorPicture extends AsyncTask<String, Void, Bitmap> {

	String urlImg;
	CastAdapter ca;
	static Bitmap result;
	ImageView actorPic;

	public LoadActorPicture(CastAdapter ca, ImageView actorPic, String urlImg){
		super();
		this.ca = ca;
		this.urlImg = urlImg;
		this.actorPic = actorPic;
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
		if (actorPic != null) {
			ImageView imageView = actorPic;
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
