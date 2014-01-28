package com.example.androidtictactoe;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToolBarView extends LinearLayout implements ObserverLinearLayout {

	private TextView msg;
	private Button newGameButton;
	
	
	public ToolBarView(Context context) {
		super(context);
		//this.setContentView(R.layout.toolbar);
		View.inflate(context, R.layout.toolbarlayout, this);
		newGameButton = (Button) findViewById(R.id.restart_button);
		newGameButton.setText("New Game");
		newGameButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				MyApplication.model.newGame();
			}
			
		});
//		//this.addView(newGameButton);
		
		msg = (TextView) findViewById(R.id.feed_back_msg);
		int turns = MyApplication.model.getTurn();
		if (turns > 0) msg.setText(turns+" moves");
		else msg.setText("");
	
		//MyApplication.model.addObserver(this);
	}


	
	@Override
	public void update(Observable arg0, Object arg1) {
		GameState state = MyApplication.model.getState();
		if (state.getState() == GameState.X_WINS){
			msg.setText("X Wins");
		}
		else if (state.getState() == GameState.O_WINS){
			msg.setText("O Wins");
		}
		else if (state.getState() == GameState.TIE){
			msg.setText("Game over, no winner");
		}
		else if (state.getState() == GameState.INVALID_MOVE){
			msg.setText("Illegal move");
		}
		else if (state.getState() == GameState.RESTART){
			msg.setText("Restarted the game");
		}
		//else if (state.getState() == GameState.SELECTED_PLAYER){
		//	msg.setText("Error Should not Show Ths!");
		//}
		else if(state.getState() == GameState.VALID_MOVE){
			msg.setText(MyApplication.model.getTurn()+" moves");
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
