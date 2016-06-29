package com.example.alessandro.serietvguide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
 * Created by Alessandro on 4/3/2016.
 */
public class Film {
	private String title, plot, img, imdbPage, runtime, year,rate;
	private Bitmap poster;

	// Constructor
	public Film(String title, String plot, String img, String imdbPage, String runtime, String year, String rate, Bitmap poster) {
		this.title = title;
		this.plot = plot;
		this.img = img;
		this.imdbPage = imdbPage;
		this.runtime = runtime;
		this.year = year;
		this.rate = rate;
		this.poster = poster;
	}

	// Getter
	public String getTitle() {
		return title;
	}

	public String getPlot() {
		return plot;
	}

	public String getImg() {
		return img;
	}

	public String getImdbPage() {
		return imdbPage;
	}

	public String getRuntime() {
		return runtime;
	}

	public String getYear() {
		return year;
	}

	public String getRate() {
		return rate;
	}

	public Bitmap getPoster(){
		return poster;
	}

	public void setPoster(Bitmap poster){
		this.poster = poster;
	}

	// fromJson
	public static Film fromJson(JSONObject jsonObject){
		HttpURLConnection urlConnection = null;
		Film mov = new Film("","","", "","","","",null);
		// Deserializza JSON into object fields
		try{
			mov.title       = jsonObject.getString("title");
			mov.plot 		= jsonObject.getString("plot");
			mov.img 		= jsonObject.getString("urlPoster");
			mov.imdbPage    = jsonObject.getString("urlIMDB");
			mov.runtime     = jsonObject.getString("runtime");
			mov.year        = jsonObject.getString("year");
			mov.rate        = jsonObject.getString("rating");
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
		// Return the new object
		return mov;
	}



}
