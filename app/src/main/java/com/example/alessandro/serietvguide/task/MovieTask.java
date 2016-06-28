package com.example.alessandro.serietvguide.task;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.alessandro.serietvguide.ActorList;
import com.example.alessandro.serietvguide.Film;
import com.example.alessandro.serietvguide.activity.MovieActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MovieTask extends AsyncTask<Void, Void, Boolean> {
	// attributi
	private MovieActivity m;
	private Film mov;
	private ArrayList<ActorList> cast;

	private static String url="";
	// costruttore
	public MovieTask(MovieActivity m, String url) {
		super();
		this.m = m;
		MovieTask.url = url;
		this.mov = new Film("","","","","","","",null);
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		url=url.replace(' ','+');
		//ESEGUIRE OPERAZIONI IN BACKGROUND
		try {
			URL uri = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) uri.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			// creare una stringa adatta per il jsonobject da un buffered reader
			StringBuilder sb = new StringBuilder(); // creatore di stringhe
			String line; // linea
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			JSONObject jsonObj = new JSONObject(sb.toString());
			JSONObject jData = jsonObj.getJSONObject("data");
			JSONArray jMovies = jData.getJSONArray("movies");
			JSONObject jObj = jMovies.getJSONObject(0);
			mov = Film.fromJson(jObj);
			JSONArray jCast = jObj.getJSONArray("actors");
			cast = ActorList.getActors(jCast);
			return true;
		}
		catch (Exception e) {
			Log.v("Errore task",e.getMessage());
			return false;
		}

	}

	@Override
	protected void onPostExecute(final Boolean success) {
		if(success)
			m.post(mov,cast);
		else
			m.movieNotFound();

	}

	@Override
	protected void onCancelled() {
	}
}
