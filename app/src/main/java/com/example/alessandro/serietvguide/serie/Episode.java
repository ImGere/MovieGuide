package com.example.alessandro.serietvguide.serie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Alessandro on 6/11/2016.
 */
public class Episode {
	private String title, plot, date, urlPoster;
	private Bitmap epImage;

	public Episode(String title, String plot, String date, String urlPoster, Bitmap epImage) {
		this.title = title;
		this.plot = plot;
		this.date = date;
		this.urlPoster = urlPoster;
		this.epImage = epImage;
	}

	public static ArrayList<Episode> getEpisodes(JSONArray jEpisodes){
		HttpURLConnection urlConnection = null;
		ArrayList<Episode> episodes = new ArrayList<>(jEpisodes.length());
		try{
			for (int i = 0; i < jEpisodes.length() ; i++) {
				JSONObject jEpisode = jEpisodes.getJSONObject(i);
				Episode singleEpisode = new Episode("","","","", null);
				singleEpisode.title = jEpisode.getString("title");
				singleEpisode.plot  = jEpisode.getString("plot");
				singleEpisode.date  = jEpisode.getString("date");
				singleEpisode.urlPoster = jEpisode.getString("urlPoster");
				if(!singleEpisode.urlPoster.equals("")) {
					try {
						URL uri = new URL(singleEpisode.urlPoster);
						urlConnection = (HttpURLConnection) uri.openConnection();
						InputStream inputStream = urlConnection.getInputStream();
						if (inputStream != null) {
							singleEpisode.epImage = BitmapFactory.decodeStream(inputStream);
						} else {
							singleEpisode.epImage = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
						}

					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}else{
					singleEpisode.epImage = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
				}
				episodes.add(singleEpisode);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return episodes;
	}

	public String getTitle() {
		return title;
	}

	public String getPlot() {
		return plot;
	}

	public Bitmap getEpImage() {
		return epImage;
	}

	public String getUrlPoster() {
		return urlPoster;
	}
}
