package com.example.alessandro.serietvguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;
import com.example.alessandro.serietvguide.adapter.EpisodeAdapter;
import com.example.alessandro.serietvguide.serie.Episode;
import com.example.alessandro.serietvguide.serie.Season;

import org.json.JSONException;

public class EpisodeViewerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private ListView episodeList;
	private EpisodeAdapter episodeAdapter;
	private Season season;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Global.gStyle);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_episode_viewer);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		season = SerieActivity.getSeasonClicked();

		setTitle("Season "+season.getNumSeason());

		episodeList = (ListView) findViewById(R.id.episodeList);
		episodeAdapter = new EpisodeAdapter(this, season.getEpisodeList());
		episodeList.setOnItemClickListener(episodeAdapter);
		episodeList.setAdapter(episodeAdapter);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}


	// ----------------------

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_actor, menu);
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
			Intent changeStyle = new Intent(this, this.getClass());
			if(Global.gStyle == Global.DARK){
				Global.gStyle = Global.LIGHT;
			}else {
				Global.gStyle = Global.DARK;
			}
			startActivity(changeStyle);
			finish();
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
