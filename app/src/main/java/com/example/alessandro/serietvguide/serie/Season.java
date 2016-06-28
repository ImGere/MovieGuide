package com.example.alessandro.serietvguide.serie;

import com.example.alessandro.serietvguide.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Alessandro on 6/11/2016.
 */
public class Season {
	private ArrayList<Episode> episodeList;
	private int numSeason;

	public Season(ArrayList<Episode> episodeList, int numSeason) {
		this.episodeList = episodeList;
		this.numSeason = numSeason;
	}

	public static ArrayList<Season> getSeasons(JSONObject object){
		try {
			JSONArray jSeasons = object.getJSONArray("seasons");
			ArrayList<Season> seasons = new ArrayList<>(jSeasons.length());
			// Creao un array di stagioni
			for (int i = 0; i < jSeasons.length(); i++) {
				// Oggetto stagione singola
				JSONObject jSingleSeason = jSeasons.getJSONObject(i);
				Season singleSeason = new Season(null,0);
				singleSeason.numSeason = jSingleSeason.getInt("numSeason");
				singleSeason.episodeList = Episode.getEpisodes(jSingleSeason.getJSONArray("episodes"));
				seasons.add(singleSeason);
			}
			return seasons;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Episode> getEpisodeList() {
		return episodeList;
	}

	public int getNumSeason() {
		return numSeason;
	}


}
