package com.example.alessandro.serietvguide.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.alessandro.serietvguide.activity.SerieActivity;
import com.example.alessandro.serietvguide.serie.Serie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alessandro on 6/1/2016.
 */
public class SerieTask extends AsyncTask<Void, Void, Boolean> {

	private SerieActivity serieActivity;
	private String url;
	private Serie serie;

	public SerieTask(SerieActivity serieActivity, String url) {
		this.serieActivity = serieActivity;
		this.url = url;
		this.serie = new Serie("","","","","","","",null,null);
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
			JSONArray jMovies= jData.getJSONArray("movies");
			JSONObject jSerie = jMovies.getJSONObject(0);
			serie = Serie.getSerie(jSerie);

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
			serieActivity.postExe(serie);
		else
			serieActivity.serieNotFound();
	}

	@Override
	protected void onCancelled() {

	}


}
