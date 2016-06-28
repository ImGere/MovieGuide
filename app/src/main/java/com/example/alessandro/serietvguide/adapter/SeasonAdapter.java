package com.example.alessandro.serietvguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alessandro.serietvguide.ActorList;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.loadPoster.LoadActorPicture;
import com.example.alessandro.serietvguide.serie.Season;

import java.util.ArrayList;

/**
 * Created by Alessandro on 5/22/2016.
 */
public class SeasonAdapter extends ArrayAdapter<Season> {

	// Attributi
	private Context context;
	private LayoutInflater mInflater;

	// Costruttore
	public SeasonAdapter(Context context, ArrayList<Season> seasons) {
		super(context, 0, seasons);
		mInflater = LayoutInflater.from(context);
	}
	/**
	 * getView
	 * Adatto la getView preconfezionata in modo da farla
	 * corrispondere ai miei campi
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Prendo un item della lista
		Season season = getItem(position);

		// Prendo al View
		if(convertView == null)
			convertView = mInflater.inflate(R.layout.season_line, null);

		// Prendo e modifico le TextView
		TextView seasonNumber = (TextView) convertView.findViewById(R.id.serieSeasonListNumber);

		seasonNumber.setText("Season " + season.getNumSeason());

		// Ritorno la convertView
		return convertView;
	}

}
