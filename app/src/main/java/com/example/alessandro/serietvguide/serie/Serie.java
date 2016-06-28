package com.example.alessandro.serietvguide.serie;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Alessandro on 6/11/2016.
 */
public class Serie {
	private String title;
	private String year;
	private String plot;
	private String runtime;
	private String urlPoster;
	private String rating;
	private String urlIMDb;
	private Bitmap poster;
	private ArrayList<Season> season;

	public Serie(String title, String year, String plot, String runtime, String urlPoster, String rating, String urlIMDb, ArrayList<Season> season, Bitmap poster) {
		this.title = title;
		this.year = year;
		this.plot = plot;
		this.runtime = runtime;
		this.urlPoster = urlPoster;
		this.rating = rating;
		this.urlIMDb = urlIMDb;
		this.season = season;
		this.poster = poster;
	}

	public static Serie getSerie(JSONObject jsonObject){
		Serie s = new Serie("","","","","","","",null,null);
		try{
			s.title     = jsonObject.getString("title");
			s.year      = jsonObject.getString("year");
			s.plot      = jsonObject.getString("plot");
			s.runtime   = jsonObject.getString("runtime");
			s.urlPoster = jsonObject.getString("urlPoster");
			s.rating    = jsonObject.getString("rating");
			s.urlIMDb   = jsonObject.getString("urlIMDB");
			s.season    = Season.getSeasons(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getPlot() {
		return plot;
	}

	public String getRuntime() {
		return runtime;
	}

	public String getUrlPoster() {
		return urlPoster;
	}

	public String getRating() {
		return rating;
	}

	public String getUrlIMDb() {
		return urlIMDb;
	}

	public ArrayList<Season> getSeason() {
		return season;
	}

	public Bitmap getPoster() {
		return poster;
	}

	public void setPoster(Bitmap poster) {
		this.poster = poster;
	}

}
