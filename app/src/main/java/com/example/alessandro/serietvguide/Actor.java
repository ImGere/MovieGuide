package com.example.alessandro.serietvguide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alessandro on 6/1/2016.
 */
public class Actor {
	String dateOfBirth, bio, urlPhoto, urlIMDB, name;
	private Bitmap profilePhoto;

	public Actor(String dateOfBirth, String bio, String urlPhoto, String urlIMDB, String name, Bitmap profilePhoto) {
		this.dateOfBirth = dateOfBirth;
		this.bio = bio;
		this.urlPhoto = urlPhoto;
		this.urlIMDB = urlIMDB;
		this.name = name;
		this.profilePhoto = profilePhoto;
	}



	public static Actor fromJson(JSONObject jsonObject){
		Actor act = new Actor("","","","","", null);
		try{
			act.dateOfBirth     = jsonObject.getString("dateOfBirth");
			act.bio             = jsonObject.getString("bio");
			act.urlPhoto        = jsonObject.getString("urlPhoto");
			act.urlIMDB         = jsonObject.getString("urlIMDB");
			act.name            = jsonObject.getString("name");

		} catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
		return act;
	}

	public Bitmap getProfilePhoto() {
		return profilePhoto;
	}


	public String getBio() {
		return bio;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setProfilePhoto(Bitmap profilePhoto){
		this.profilePhoto = profilePhoto;
	}

	public String getUrlIMDB() {
		return urlIMDB;
	}

	public String getName() {
		return name;
	}
}
