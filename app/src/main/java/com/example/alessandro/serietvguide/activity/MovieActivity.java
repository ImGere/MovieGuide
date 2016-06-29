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
import android.util.Log;
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

import com.example.alessandro.serietvguide.ActorList;
import com.example.alessandro.serietvguide.adapter.CastAdapter;
import com.example.alessandro.serietvguide.Film;
import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.loadPoster.LoadMoviePoster;
import com.example.alessandro.serietvguide.task.MovieTask;
import com.example.alessandro.serietvguide.R;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

	private TextView txtTitle, plot, runtime, year, rate;
	private ProgressDialog dialog;
	private ImageView poster;
	private String url, title;
	private Film film;
	private ListView actorList;
	private CastAdapter actorsAdapter;
	private boolean isImageFitToScreen, settingPoster;
	private ViewGroup.LayoutParams lp;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Global.gStyle);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);

		/* Definizione degli elementi all'interno del layout */
		actorList = (ListView) findViewById(R.id.cast);
		txtTitle = (TextView) findViewById(R.id.txtTitleMovie);
		runtime = (TextView) findViewById(R.id.txtRuntime);
		year = (TextView) findViewById(R.id.txtYear);
		rate = (TextView) findViewById(R.id.txtRate);
		plot = (TextView) findViewById(R.id.txtMoviePlot);
		runtime.setTextColor(plot.getTextColors());
		year.setTextColor(plot.getTextColors());
		rate.setTextColor(plot.getTextColors());
		poster = (ImageView) findViewById(R.id.imgPosterMovie);
		lp = poster.getLayoutParams();
		poster.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isImageFitToScreen) {
					isImageFitToScreen = false;
					poster.setLayoutParams(lp);
					poster.setAdjustViewBounds(true);
				} else {
					isImageFitToScreen = true;
					poster.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
					poster.setScaleType(ImageView.ScaleType.FIT_CENTER);
				}
			}
		});

		// INTENT
		Intent intent = this.getIntent();
		if (!intent.getStringExtra("Title").equals("ChangeStyle")) {
			title = intent.getStringExtra("Title");
			dialog = ProgressDialog.show(this, "", "Wait...", false, true);
			iniziaTask();
		} else {
			setOldFilm();
		}

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

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
		url= "http://www.myapifilms.com/imdb/idIMDB?title=" + title + "&token=" + Global.TOKEN + "&format=json&actors=1&language=en-us&fullSize=1";
		MovieTask mMovieTask = new MovieTask(this, url);
		mMovieTask.execute((Void) null);
		settingPoster = true;
	}

	// post json
	public void post(Film movie, ArrayList<ActorList> cast){

		Global.gCast = cast;
		this.film = movie;
		if(dialog.isShowing())
			dialog.dismiss();
		txtTitle.setText(film.getTitle());
		plot.setText(film.getPlot());

		year.setText("Year: " + film.getYear());
		runtime.setText(" |  Runtime: " + film.getRuntime());
		rate.setText(" | Rate: " + film.getRate());

		// Aggiungo gli attori
		actorsAdapter = new CastAdapter(this, cast);
		actorList.setAdapter(actorsAdapter);
		actorList.setOnItemClickListener(this);
		//poster.setImageBitmap(film.getPoster());
		/*Take the ImageView from layout and set the movie's image*/
		LoadMoviePoster loadImage = new LoadMoviePoster(this, film.getImg());
		loadImage.execute();
	}

	public void movieNotFound(){
		dialog.dismiss();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);

		dialog.setTitle(R.string.error)
				.setIcon(R.mipmap.ic_launcher)
				.setMessage(R.string.movie_not_found)
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

	// set image
	public void setPosterImage(Bitmap result){
		poster.setImageBitmap(result);
		this.film.setPoster(result);
		Global.gFilm = this.film;
		settingPoster = false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		final ActorList actClicked = (ActorList) parent.getItemAtPosition(position);
		Intent intActClicked = new Intent(this, ActorActivity.class);
		intActClicked.putExtra("actName", actClicked.getName());
		startActivity(intActClicked);
	}

	public void setOldFilm(){
		this.film = Global.gFilm;
		txtTitle.setText(Global.gFilm.getTitle());
		plot.setText(Global.gFilm.getPlot());
		year.setText("Year: " + Global.gFilm.getYear());
		runtime.setText(" |  Runtime: " + Global.gFilm.getRuntime());
		rate.setText(" | Rate: " + Global.gFilm.getRate());
		actorList.setAdapter(new CastAdapter(this, Global.gCast));
		actorList.setOnItemClickListener(this);
		poster.setImageBitmap(Global.gFilm.getPoster());
	}


	//-----------------------

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
				poster.setLayoutParams(lp);
				poster.setAdjustViewBounds(true);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
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
				changeStyle.putExtra("Title", "ChangeStyle");
				if (Global.gStyle == Global.DARK) {
					Global.gStyle = Global.LIGHT;
				} else {
					Global.gStyle = Global.DARK;
				}
				startActivity(changeStyle);
				finish();
			}else{
				Toast.makeText(MovieActivity.this, "Waiting for poster", Toast.LENGTH_SHORT).show();
			}
		}else if (id == R.id.action_imdb) {
			try {
				Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(film.getImdbPage()));
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
