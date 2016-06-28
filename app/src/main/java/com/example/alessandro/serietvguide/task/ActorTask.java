package com.example.alessandro.serietvguide.task;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.alessandro.serietvguide.Actor;
import com.example.alessandro.serietvguide.ActorList;
import com.example.alessandro.serietvguide.Film;
import com.example.alessandro.serietvguide.FilmList;
import com.example.alessandro.serietvguide.activity.ActorActivity;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Alessandro on 6/1/2016.
 */
public class ActorTask extends AsyncTask<Void, Void, Boolean> {

	private ActorActivity actorActivity;
	private String url;
	private Actor act;
	private ArrayList<FilmList> filmography;

	public ActorTask(ActorActivity actorActivity, String url) {
		this.actorActivity = actorActivity;
		this.url = url;
		this.act = new Actor("","","","","", null);
	}

	protected Boolean doInBackground(Void... params){
		url = url.replace(' ', '+');
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
			JSONArray jNames = jData.getJSONArray("names");
			JSONObject jAct = jNames.getJSONObject(0);
			act = Actor.fromJson(jAct);
			JSONArray jMovies = jAct.getJSONArray("filmographies");
			JSONObject jObj = jMovies.getJSONObject(0);
			JSONArray jFilmography = jObj.getJSONArray("filmography");
			filmography = FilmList.fromJson(jFilmography);
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
			actorActivity.postExe(act,filmography);
		else
			actorActivity.actorNotFound();
	}

	@Override
	protected void onCancelled() {
	}
}
