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

import java.util.ArrayList;

/**
 * Created by Alessandro on 5/22/2016.
 */
public class CastAdapter extends ArrayAdapter<ActorList> {

	// Attributi
	private Context context;
	private LayoutInflater mInflater;

	// Costruttore
	public CastAdapter(Context context, ArrayList<ActorList> cast) {
		super(context, 0, cast);
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
		ActorList actor = getItem(position);

		// Prendo al View
		if(convertView == null)
			convertView = mInflater.inflate(R.layout.actor_line, null);

		// Prendo e modifico le TextView e l'immagine
		TextView actorName = (TextView) convertView.findViewById(R.id.actorName);
		TextView character = (TextView) convertView.findViewById(R.id.character);
		ImageView actorPic = (ImageView) convertView.findViewById(R.id.actorPicture);

		// PROVA
		actorPic.setImageBitmap(actor.getProfilePhoto());


		// Assegno il valore alla stringa
		actorName.setText(actor.getName());
		character.setText(actor.getCharacter());

		// Ritorno la convertView
		return convertView;
	}

}
