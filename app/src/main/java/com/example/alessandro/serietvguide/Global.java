package com.example.alessandro.serietvguide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import com.example.alessandro.serietvguide.serie.Serie;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Alessandro on 6/5/2016.
 */
public class Global {

	public static final int LIGHT 	= android.R.style.ThemeOverlay_Material_Light;
	public static final int DARK   = android.R.style.ThemeOverlay_Material_Dark;
	public static final String TOKEN = "3af4468f-441c-402a-bbf1-d1f7b82402af";
	public static Context context = null;
	public static int gStyle = LIGHT;
	public static Film gFilm;
	public static ArrayList<ActorList> gCast;
	public static Actor gActor;
	public static ArrayList<FilmList> gFilmography;
	public static Serie gSerie;
}
