package com.example.alessandro.serietvguide.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alessandro.serietvguide.BuildConfig;
import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;

public class SearchMovieActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	private EditText edtTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Global.context = this.getApplicationContext();
		setTheme(Global.gStyle);
		setTitle(R.string.title_activity_search_movie);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search_movie);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);


		edtTitle = (EditText) findViewById(R.id.edtMovieTitle);

		edtTitle.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					controlTitle();
					handled = true;
				}
				return handled;
			}
		});

	}

	public void movieSearch(View v){
		controlTitle();
	}

	public void controlTitle(){
		if(edtTitle.getText().toString().isEmpty()){
			String errTitle = "Missing title";
			Toast toast = Toast.makeText(this,errTitle,Toast.LENGTH_SHORT);
			toast.show();
		}else {
			createMovieIntent();
		}
	}

	private void createMovieIntent() {
		Intent i = new Intent(this,MovieActivity.class);
		i.putExtra("Title", edtTitle.getText().toString());
		startActivity(i);
	}



	//----------------
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
		getMenuInflater().inflate(R.menu.search_movie, menu);
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
			/* Already in searchMovieActivity */
		} else if (id == R.id.nav_search_actor) {
			Intent intent = new Intent(this, SearchActorActivity.class);
			startActivity(intent);
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
