package com.example.alessandro.serietvguide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Alessandro on 6/2/2016.
 */
public class FilmList {
	private String title, year;

	public FilmList(String year, String title) {
		this.year = year;
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public static ArrayList<FilmList> fromJson(JSONArray jMovies){
		ArrayList<FilmList> filmography = new ArrayList<>(jMovies.length());

		try{
			for (int i = 0; i < jMovies.length(); i++){
				JSONObject jObj = jMovies.getJSONObject(i);
				FilmList filmList = new FilmList("","");
				filmList.title  = jObj.getString("title");
				filmList.year   = jObj.getString("year");
				filmography.add(filmList);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return filmography;
	}
}
