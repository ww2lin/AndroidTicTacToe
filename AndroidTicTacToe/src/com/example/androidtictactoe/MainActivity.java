package com.example.androidtictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.ViewGroup;

public class MainActivity extends Activity{

	boolean gotoSettings = true;
	private ToolBarView toolbar;
	private BoardView board;
	private HeaderView headerView ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view1, menu);
		
		// get the menu item to add a listener
		MenuItem item = menu.findItem(R.id.menu_view1_gotoview2);
		
		// create the menu item controller to change views
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// create intent to request the view2 activity to be shown
				gotoOptions();

				return true;
			}
		});
		return true;
	}

    
    
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// can only get widgets by id in onPostCreate for activity xml res

		// create the views and add them to the main activity
		toolbar = new ToolBarView(this);
		ViewGroup v1 = (ViewGroup) findViewById(R.id.ToolbarView);
		v1.addView(toolbar);

		
		board = new BoardView(this);
		ViewGroup v2 = (ViewGroup) findViewById(R.id.BoardView);
		v2.addView(board);
		
		headerView = new HeaderView(this);
		ViewGroup v3 = (ViewGroup) findViewById(R.id.HeaderView);
		v3.addView(headerView);

		if(gotoSettings){
			gotoOptions();
			gotoSettings=false;
		}
		
		
	}
	

    private void gotoOptions(){
		Intent intent = new Intent(this, Options.class);
		startActivity(intent);
    }
    
	// save and restore state (need to do this to support orientation change)
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("gotoSettings", gotoSettings);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		gotoSettings = savedInstanceState.getBoolean("gotoSettings");
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    toolbar.onDestroy();
	    board.onDestroy();
	    headerView.onDestroy();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    toolbar.onResume();
	    board.onResume();
	    headerView.onResume();
	}

}
