package com.example.alessandro.serietvguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.example.alessandro.serietvguide.Global;
import com.example.alessandro.serietvguide.R;

public class SearchActorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
	private EditText edtActName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(Global.gStyle);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_actor);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		edtActName = (EditText) findViewById(R.id.edtActName);

		edtActName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					controlName();
					handled = true;
				}
				return handled;
			}
		});
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
	}


	public void actorSearch(View v){
		controlName();
	}
	public void controlName(){
		if(edtActName.getText().toString().isEmpty()){
			String errTitle = "Missing title";
			Toast toast = Toast.makeText(this,errTitle,Toast.LENGTH_SHORT);
			toast.show();
		}else {
			createActorIntent();
		}
	}

	public void createActorIntent() {
		Intent i = new Intent(this, ActorActivity.class);
		i.putExtra("actName", edtActName.getText().toString());
		startActivity(i);
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
			/* Already in searchActorActivity */
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
