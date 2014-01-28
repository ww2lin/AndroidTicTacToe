package com.example.androidtictactoe;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeaderView extends LinearLayout implements ObserverLinearLayout{
	
	private TextView player1;
	private TextView player2;
	
	public HeaderView(Context context) {
		super(context);
		View.inflate(context, R.layout.headerlayout, this);
		
		player1 = (TextView)findViewById(R.id.player1);
		player2 = (TextView)findViewById(R.id.player2);
		
		player1.setText(MyApplication.model.getPlayer1()+": X");
		player2.setText(MyApplication.model.getPlayer2()+": O");
		
		//MyApplication.model.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Model model =  MyApplication.model;
		GameState state = model.getState();
		if (state.getState() == GameState.NAME_CHANGE){
			player1.setText(model.getPlayer1()+": X");
			player2.setText(model.getPlayer2()+": O");
		}
		
	}
	@Override
	public void onDestroy() {
		MyApplication.model.deleteObserver(this);
	}
    @Override
	public void onResume() {
		MyApplication.model.addObserver(this);
	}
}
