package com.example.alessandro.serietvguide.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.loadPoster.LoadEpisodePicture;
import com.example.alessandro.serietvguide.serie.Episode;

import java.util.ArrayList;

/**
 * Created by Alessandro on 6/11/2016.
 */
public class EpisodeAdapter extends ArrayAdapter<Episode> implements AdapterView.OnItemClickListener {
	// Attributi
	private Context context;
	private LayoutInflater mInflater;
	private boolean[] clicked;

	// Costruttore
	public EpisodeAdapter(Context context, ArrayList<Episode> episodes) {
		super(context, 0, episodes);
		mInflater = LayoutInflater.from(context);
		clicked = new boolean[episodes.size()];
		for (int i = 0; i < episodes.size(); i++) {
			clicked[i] = false;
		}
	}
	/**
	 * getView
	 * Adatto la getView preconfezionata in modo da farla
	 * corrispondere ai miei campi
	 */

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Prendo un item della lista
		Episode episode = getItem(position);

		// Prendo al View
		if(convertView == null)
			convertView = mInflater.inflate(R.layout.episode_line, null);

		// Prendo e modifico le TextView e l'immagine
		TextView episodePlot = (TextView) convertView.findViewById(R.id.episodePlot);
		TextView episodeTitle = (TextView) convertView.findViewById(R.id.episodeTitle);
		ImageView episodeImage = (ImageView) convertView.findViewById(R.id.episodeImage);

		episodeImage.setImageBitmap(episode.getEpImage());
		// Assegno il valore alla stringa
		episodePlot.setText(episode.getPlot());
		episodeTitle.setText(episode.getTitle());

		// Ritorno la convertView
		return convertView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TextView epPlot = (TextView) view.findViewById(R.id.episodePlot);
		TextView epTitle = (TextView) view.findViewById(R.id.episodeTitle);
		if(!clicked[position]) {
			epPlot.setMaxLines(Integer.MAX_VALUE);
			epTitle.setMaxLines(Integer.MAX_VALUE);
			clicked[position] = true;
		}
		else {
			epPlot.setMaxLines(3);
			epTitle.setMaxLines(1);
			clicked[position] = false;
		}
	}
}

