package com.example.alessandro.serietvguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alessandro.serietvguide.FilmList;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.loadPoster.LoadActorPicture;

import java.util.ArrayList;

/**
 * Created by Alessandro on 6/2/2016.
 */
public class FilmAdapter extends ArrayAdapter<FilmList> {

	// Attributi
	private Context context;
	private LayoutInflater mInflater;

	// Costruttore
	public FilmAdapter(Context context, ArrayList<FilmList> cast) {
		super(context, 0, cast);
		mInflater = LayoutInflater.from(context);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// Prendo un item della lista
		FilmList filmList = getItem(position);

		// Prendo al View
		if(convertView == null)
			convertView = mInflater.inflate(R.layout.film_line, null);

		// Prendo e modifico le TextView
		TextView filmTitle = (TextView) convertView.findViewById(R.id.filmTitle);
		TextView filmYear = (TextView) convertView.findViewById(R.id.filmYear);
		// Assegno il valore alla stringa
		filmTitle.setText(filmList.getTitle());
		filmYear.setText(filmList.getYear());

		// Ritorno la convertView
		return convertView;
	}
}
