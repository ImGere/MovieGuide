package com.example.alessandro.serietvguide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.example.alessandro.serietvguide.loadPoster.LoadActorPicture;
import com.example.alessandro.serietvguide.loadPoster.LoadActorPictureList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Alessandro on 4/3/2016.
 */
public class ActorList {
	private String name, character, urlCharacter, urlProfile, urlPhoto, actorId;
	private Bitmap profilePhoto;

	public ActorList(String name, String character, String urlCharacter, String urlProfile, String urlPhoto, String actorId, Bitmap profilePhoto){
		this.name = name;
		this.character = character;
		this.urlCharacter = urlCharacter;
		this.urlProfile = urlProfile;
		this.urlPhoto = urlPhoto;
		this.actorId = actorId;
		this.profilePhoto = profilePhoto;
	}
	
	// Getter
	public String getName(){
		return name;
	}
	
	public String getCharacter(){
		return character;
	}
	
	public String getUrlCharacter(){
		return urlCharacter;
	}
	
	public String getUrlProfile(){
		return urlProfile;
	}
	
	public String getUrlPhoto(){
		return urlPhoto;
	}
	
	public String getActorId(){
		return actorId;
	}

	public Bitmap getProfilePhoto(){
		return profilePhoto;
	}

	// CAST
	public static ArrayList<ActorList> getActors(JSONArray jsonArray){
		HttpURLConnection urlConnection = null;
		ArrayList<ActorList> cast = new ArrayList<>(jsonArray.length());
		try{
			for (int i = 0; i < jsonArray.length(); i++){
				JSONObject actor = jsonArray.getJSONObject(i);
				ActorList act = new ActorList("","","","","","",null);
				act.name            = actor.getString("actorName");
				act.character       = actor.getString("character");
				act.urlCharacter    = actor.getString("urlCharacter");
				act.urlProfile      = actor.getString("urlProfile");
				act.urlPhoto        = actor.getString("urlPhoto");
				act.actorId         = actor.getString("actorId");
				if(!act.urlPhoto.equals("")) {
					try {
						URL uri = new URL(act.urlPhoto);
						urlConnection = (HttpURLConnection) uri.openConnection();
						InputStream inputStream = urlConnection.getInputStream();
						if (inputStream != null) {
							act.profilePhoto = BitmapFactory.decodeStream(inputStream);
						} else {
							act.profilePhoto = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
						}

					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}else{
					act.profilePhoto = BitmapFactory.decodeResource(Global.context.getResources(), R.mipmap.ic_photo);
				}
				cast.add(act);
			}
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
		return cast;
	}
}