package com.example.alessandro.serietvguide.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessandro.serietvguide.Actor;
import com.example.alessandro.serietvguide.adapter.FilmAdapter;
import com.example.alessandro.serietvguide.FilmList;
import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.loadPoster.LoadActorProfilePicture;
import com.example.alessandro.serietvguide.task.ActorTask;

import java.util.ArrayList;


public class ActorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

	private TextView actName, actDesc;
	private String url, name;
	private ProgressDialog dialog;
	private Actor actor;
	private ImageView profilePicture;
	private ListView filmographyList;
	private FilmAdapter filmAdapter;
	private boolean isImageFitToScreen, settingPhoto;
	private ViewGroup.LayoutParams lp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Global.gStyle);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actor);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		/* Inizializzazione elementi del layout */
		actName = (TextView) findViewById(R.id.txtActorName);
		actDesc = (TextView) findViewById(R.id.txtActorDesc);
		profilePicture = (ImageView) findViewById(R.id.actorImage);
		lp = profilePicture.getLayoutParams();
		profilePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isImageFitToScreen) {
					isImageFitToScreen = false;
					profilePicture.setLayoutParams(lp);
					profilePicture.setAdjustViewBounds(true);
				} else {
					isImageFitToScreen = true;
					profilePicture.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
					profilePicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
				}
			}
		});
		filmographyList = (ListView) findViewById(R.id.filmography);

		// INTENT
		Intent intent = getIntent();
		if (!intent.getStringExtra("actName").equals("ChangeStyle")) {
			name = intent.getStringExtra("actName");
			dialog = ProgressDialog.show(this, "", "Wait...", false, true);
			iniziaTask();
		}else{
			setOldActor();
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}

	// Inizia task async per json
	private void iniziaTask() {
		url = "http://www.myapifilms.com/imdb/idIMDB?name=" + name + "&token=" + Global.TOKEN + "&format=json&filmography=1&actorTrivia=1&fullSize=1";
		ActorTask aTask = new ActorTask(this, url);
		aTask.execute();
		settingPhoto = true;
	}


	public void postExe(Actor act, ArrayList<FilmList> filmography) {
		Global.gFilmography = filmography;
		this.actor = act;
		if (dialog.isShowing())
			dialog.dismiss();
		actName.setText(actor.getName());
		actDesc.setText(actor.getBio());

		// Addatto la filmography nella listView
		filmAdapter = new FilmAdapter(this, filmography);
		filmographyList.setAdapter(filmAdapter);
		filmographyList.setOnItemClickListener(this);

		LoadActorProfilePicture loadActorProfilePicture = new LoadActorProfilePicture(this, actor.getUrlPhoto());
		loadActorProfilePicture.execute();
	}

	public void setPosterImage(Bitmap result) {
		profilePicture.setImageBitmap(result);
		this.actor.setProfilePhoto(result);
		Global.gActor = this.actor;
		settingPhoto = false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final FilmList filmClicked = (FilmList) parent.getItemAtPosition(position);
		Intent intFilmClicked = new Intent(this, MovieActivity.class);
		intFilmClicked.putExtra("Title", filmClicked.getTitle());
		startActivity(intFilmClicked);
	}

	public void actorNotFound() {
		dialog.dismiss();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		dialog.setTitle(R.string.error)
				.setIcon(R.mipmap.ic_launcher)
				.setMessage(R.string.actor_not_found)
//  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
				.setPositiveButton("Close", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialoginterface, int i) {
						finish();
					}
				}).show();
	}

	public void setOldActor(){
		actName.setText(Global.gActor.getName());
		actDesc.setText(Global.gActor.getBio());

		// Addatto la filmography nella listView
		filmAdapter = new FilmAdapter(this, Global.gFilmography);
		filmographyList.setAdapter(filmAdapter);
		filmographyList.setOnItemClickListener(this);
		profilePicture.setImageBitmap(Global.gActor.getProfilePhoto());
	}


	//--------------
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			if(!isImageFitToScreen)
				super.onBackPressed();
			else if(isImageFitToScreen){
				isImageFitToScreen=false;
				profilePicture.setLayoutParams(lp);
				profilePicture.setAdjustViewBounds(true);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_change_theme) {
			if(!settingPhoto) {
				Intent changeStyle = new Intent(this, this.getClass());
				changeStyle.putExtra("actName", "ChangeStyle");
				if (Global.gStyle == Global.DARK) {
					Global.gStyle = Global.LIGHT;
				} else {
					Global.gStyle = Global.DARK;
				}
				startActivity(changeStyle);
				finish();
			}else{
				Toast.makeText(ActorActivity.this, "Waiting for images", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.action_imdb) {
			try {
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(actor.getUrlIMDB()));
				startActivity(myIntent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, "No application can handle this request."
						+ " Please install a webbrowser", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_search_serie) {
			Intent intent = new Intent(this, SearchSerieActivity.class);
			startActivity(intent);
		} else if (id == R.id.nav_search_movie) {
			Intent intent = new Intent(this, SearchMovieActivity.class);
			startActivity(intent);
		} else if (id == R.id.nav_search_actor) {
			Intent intent = new Intent(this, SearchActorActivity.class);
			startActivity(intent);
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}