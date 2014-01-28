package com.example.androidtictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Options extends Activity{

	private boolean xfirst = true;
	private int changeInBoardSize = 0;
	private int changeInWinConstraint = 0;
	
	private EditText p1;
	private EditText p2;
	
	private RadioButton xFirst;
	private RadioButton oFirst;
	
	private Button confirm;
	private Button cancel; 
	
	private SeekBar boardSizeSeekBar ;
	private SeekBar winConstraintSeekBar ;
	
	private int AiMode;
	private RadioButton none;
	private RadioButton random;
	private RadioButton greedy;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionslayout);
        
        addPlayerName();
        
        addFirstTurn();
        
        addSeekBars();
        
        addAI();
        
        addButtons();
    }
    
	
    private  void addPlayerName(){
    	Model model = MyApplication.model;
        p1 = (EditText) findViewById(R.id.playerone);
        p2 = (EditText) findViewById(R.id.playertwo);
        
        p1.setText(model.getPlayer1());
        p2.setText(model.getPlayer2());
    }


    
    private void addFirstTurn(){
    	Model model = MyApplication.model;
        xFirst = (RadioButton) findViewById(R.id.radioX);
        oFirst = (RadioButton) findViewById(R.id.radioO);
        
        switch(model.getFirstTurn()){
        	case Util.O_INDEX:
        		oFirst.setChecked(true);
        		xfirst=false;
        	break;
        	default:
        		xFirst.setChecked(true);
        	break;
        }
        
        xFirst.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				xfirst=arg1;
			}
        	
        });
        
    }
    
    private void addSeekBars(){
    	Model model = MyApplication.model;
        final TextView BoardSizeTextView = (TextView) findViewById(R.id.boardValue);
        final TextView winConstraintTextView = (TextView) findViewById(R.id.winConstranitValue);
        
        boardSizeSeekBar = (SeekBar) findViewById(R.id.boardSizeSeekBar);
        winConstraintSeekBar = (SeekBar) findViewById(R.id.winConstranitSeekBar);
        
        
        int boardSize = model.getBoardSize();
        int winConstraint = model.getWinConstaint();
        
        changeInBoardSize = boardSize;
        changeInWinConstraint = winConstraint;
        
        BoardSizeTextView.setText("Board Size: "+boardSize);
        winConstraintTextView.setText("Win Constaint: "+winConstraint);
        
        boardSizeSeekBar.setMax(Util.maxBoardSize - Util.minBoardSize);
        winConstraintSeekBar.setMax(Util.maxBoardSize - Util.minBoardSize);
         
        boardSizeSeekBar.setProgress(boardSize - Util.minBoardSize);
        winConstraintSeekBar.setProgress(winConstraint - Util.minBoardSize);
        
        boardSizeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				changeInBoardSize = arg1+Util.minBoardSize;
				BoardSizeTextView.setText("Board Size: "+changeInBoardSize);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
        	
        	
        });
        
        
        winConstraintSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				changeInWinConstraint = arg1+Util.minBoardSize;
				winConstraintTextView.setText("Win Constraint: "+changeInWinConstraint);
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
        	
        	
        });
        
    }
    
    private void addAI(){
    	final Model model = MyApplication.model;
    	
    	AiMode = model.getAiMode();
  
    	none = (RadioButton)findViewById(R.id.NoneRadio);
    	random = (RadioButton)findViewById(R.id.RandomRadio);
    	greedy = (RadioButton)findViewById(R.id.GreedyRadio);
    	 
    	
		switch (AiMode) {
			case Computer.RANDOM:
				random.setChecked(true);
				break;
			case Computer.GREEDY:
				greedy.setChecked(true);
				break;
			default:
				none.setChecked(true);
		}
		
		none.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) AiMode = Computer.NONE;
			}
        	
        });
		
		random.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1)  AiMode = Computer.RANDOM;
			}
        	
        });
		
		greedy.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1)  AiMode = Computer.GREEDY;
			}
        	
        });
    	
    }
    
    private void addButtons(){
    	final Model model = MyApplication.model;
        confirm = (Button) findViewById(R.id.confrim);
        cancel = (Button) findViewById(R.id.cancel);
        
        cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				finish();
			}
        	
        });
        
        confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(xfirst)model.setStartingX();
				else model.setStartingO();
				model.setPlayer1(p1.getText().toString());
				model.setPlayer2(p2.getText().toString());
				model.updateNameChange();
				model.setAiMode(AiMode);
				model.newGame();
				model.boardSizeSlider(changeInBoardSize);
				model.BoardWiningSlider(changeInWinConstraint);
				finish();
			}
        	
        });
    }
   
    
}
