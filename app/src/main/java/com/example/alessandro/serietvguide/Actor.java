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
		} catch(JSONException ex){
			act.dateOfBirth     = "N/A";
		}
		try{
			act.bio             = jsonObject.getString("bio");
		} catch(JSONException ex){
			act.bio             = "N/A";
		}
		try{
			act.urlPhoto        = jsonObject.getString("urlPhoto");
		} catch(JSONException ex){
			act.urlPhoto        = "N/A";
		}
		try{
			act.urlIMDB         = jsonObject.getString("urlIMDB");
		} catch(JSONException ex){
			act.urlIMDB         = "N/A";
		}try{
			act.name            = jsonObject.getString("name");
		} catch(JSONException ex){
			act.name            = "N/A";
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
