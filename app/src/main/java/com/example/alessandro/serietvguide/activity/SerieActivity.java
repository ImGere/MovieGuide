package com.example.alessandro.serietvguide.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.loadPoster.LoadSeriePoster;
import com.example.alessandro.serietvguide.serie.Season;
import com.example.alessandro.serietvguide.adapter.SeasonAdapter;
import com.example.alessandro.serietvguide.serie.Serie;
import com.example.alessandro.serietvguide.task.SerieTask;

public class SerieActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

	private TextView seriePlot, serieTitle, serieYear, serieRuntime, serieRate;
	private String sTitle, url;
	private ImageView seriePoster;
	private ListView serieSeasonsList;
	private ViewGroup.LayoutParams lp;
	private boolean isImageFitToScreen, settingPoster;
	private ProgressDialog dialog;
	private SeasonAdapter seasonAdapter;
	private Serie serie;
	private static Season seasonClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Global.gStyle);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serie);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		/* Istanzio gli elementi del layout */
		seriePlot = (TextView) findViewById(R.id.seriePlot);
		serieTitle = (TextView) findViewById(R.id.serieTitle);
		serieYear = (TextView) findViewById(R.id.serieYear);
		serieRuntime = (TextView) findViewById(R.id.serieRuntime);
		serieRate = (TextView) findViewById(R.id.serieRate);
		seriePoster = (ImageView) findViewById(R.id.seriePosterImage);
		serieSeasonsList = (ListView) findViewById(R.id.serieSeasonList);
		serieRuntime.setTextColor(seriePlot.getTextColors());
		serieYear.setTextColor(seriePlot.getTextColors());
		serieRate.setTextColor(seriePlot.getTextColors());

		// Fullscreen image
		lp = seriePoster.getLayoutParams();
		seriePoster.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isImageFitToScreen) {
					isImageFitToScreen = false;
					seriePoster.setLayoutParams(lp);
					seriePoster.setAdjustViewBounds(true);
				} else {
					isImageFitToScreen = true;
					seriePoster.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
					seriePoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
				}
			}
		});
		Intent i = getIntent();
		if(!i.getStringExtra("serieTitle").equals("ChangeStyle")) {
			sTitle = i.getStringExtra("serieTitle");
			dialog = ProgressDialog.show(this, "", "Wait...", false, true);
			iniziaTask();
		}else{
			setOldSerie();
		}


		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}

	public void iniziaTask(){
		url = "http://www.myapifilms.com/imdb/idIMDB?title=" + sTitle + "&token=" + Global.TOKEN +"&format=json&language=en-us&seasons=1&filter=2&limit=1";
		SerieTask sTask = new SerieTask(this, url);
		sTask.execute();
		settingPoster = true;
	}


	public void postExe(Serie serie){
		this.serie = serie;
		if(dialog.isShowing())
			dialog.dismiss();

		seriePlot.setText(serie.getPlot());
		serieTitle.setText(serie.getTitle());
		serieYear.setText("Year: " + serie.getYear());
		serieRuntime.setText(" |  Runtime: " + serie.getRuntime());
		serieRate.setText(" | Rate: " + serie.getRating());

		seasonAdapter = new SeasonAdapter(this, serie.getSeason());
		serieSeasonsList.setAdapter(seasonAdapter);
		serieSeasonsList.setOnItemClickListener(this);
		LoadSeriePoster loadPoster = new LoadSeriePoster(this, serie.getUrlPoster());
		loadPoster.execute();
	}


	public void setPosterImage(Bitmap result) {
		seriePoster.setImageBitmap(result);
		this.serie.setPoster(result);
		Global.gSerie = this.serie;
		settingPoster = false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		seasonClicked = (Season) parent.getItemAtPosition(position);
		Intent intSeaClicked = new Intent(this, EpisodeViewerActivity.class);
		startActivity(intSeaClicked);

	}

	public static Season getSeasonClicked(){
		return seasonClicked;
	}

	public void serieNotFound() {
		dialog.dismiss();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		dialog.setTitle(R.string.error)
				.setIcon(R.mipmap.ic_launcher)
				.setMessage(R.string.serie_not_found)
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

	public void setOldSerie(){
		seriePlot.setText(Global.gSerie.getPlot());
		serieTitle.setText(Global.gSerie.getTitle());
		serieYear.setText("Year: " + Global.gSerie.getYear());
		serieRuntime.setText(" |  Runtime: " + Global.gSerie.getRuntime());
		serieRate.setText(" | Rate: " + Global.gSerie.getRating());
		this.serie = Global.gSerie;
		seasonAdapter = new SeasonAdapter(this, Global.gSerie.getSeason());
		serieSeasonsList.setAdapter(seasonAdapter);
		serieSeasonsList.setOnItemClickListener(this);
		seriePoster.setImageBitmap(Global.gSerie.getPoster());
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
			else{
				isImageFitToScreen=false;
				seriePoster.setLayoutParams(lp);
				seriePoster.setAdjustViewBounds(true);
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
			if(!settingPoster) {
				Intent changeStyle = new Intent(this, this.getClass());
				changeStyle.putExtra("serieTitle", "ChangeStyle");
				if (Global.gStyle == Global.DARK) {
					Global.gStyle = Global.LIGHT;
				} else {
					Global.gStyle = Global.DARK;
				}
				startActivity(changeStyle);
				finish();
			}else{
				Toast.makeText(SerieActivity.this, "Waiting for images", Toast.LENGTH_SHORT).show();
			}
		} else if (id == R.id.action_imdb) {
			try {
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(serie.getUrlIMDb()));
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