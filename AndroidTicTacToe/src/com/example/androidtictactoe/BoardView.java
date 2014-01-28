package com.example.androidtictactoe;

import java.util.Observable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class BoardView extends LinearLayout implements ObserverLinearLayout {

	private GridButton boardButtons[][];
	private Context context;
	
	

	public BoardView(Context context) {
		super(context);
		View.inflate(context, R.layout.boardlayout, this);
		setupBoard(context);
		this.context = context;
		repaint();
		
	}
    
    public void setupBoard(Context context){
    	LinearLayout board = (LinearLayout)findViewById(R.id.board);
        board.removeAllViews();
        int boardSize = MyApplication.model.getBoardSize();
        
        setupButtons(boardSize,context,board);
    	
    }
    
    
    
    private void setupButtons(int size,Context context, LinearLayout board){
    	boardButtons = new GridButton[size][size];
    	
    	WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	int width = 0;
    	int height = 0;
    	
    	if (android.os.Build.VERSION.SDK_INT >= 13){
    		Point screen = new Point();
			display.getSize(screen);
			width = screen.x;
			height = screen.y;

    	}else{
    		width = display.getWidth();
    		height = display.getHeight();
    	}
    	
    	int orientation = getResources().getConfiguration().orientation;
    	
		int padding = 5;
    	int btw = 0;
    	int bth = height/(size+padding);
    	if (orientation == Configuration.ORIENTATION_PORTRAIT){
    		btw = width/(size+padding/4);
    	}else{
    		btw = width/(size+padding);
    	}
    	
    	
    	
    	RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(btw, bth);
    	
    	for (int r= 0; r < size; ++r){
    		LinearLayout rows = new LinearLayout(context);
    		//rows.setLayoutParams(fieldparams);
    		rows.setOrientation(LinearLayout.HORIZONTAL);
    		
    		//rows.setWeightSum(size);
    		for (int c=0; c < size; ++c){
    			final GridButton button = new GridButton(context,r,c);
    			//button.setLayoutParams(buttonParams);
    			button.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View arg0) {
						MyApplication.model.actionPerformedAtIndex(button.r, button.c);
					}
    				
    			});
    			button.setLayoutParams(params);
    			button.setPadding(padding, padding, padding, padding);
    			//float btSize = button.getTextSize();
    			int btnTextSize= Math.min(bth, btw) / 4;
    			button.setTextSize(btnTextSize);
    			boardButtons[r][c] = button;
    			//boardButtons[r][c].setLayoutParams(params);
    			rows.addView(button);
    		
    		}
    		
    		//params.addRule(RelativeLayout.BELOW, r);
    		
    		//board.addView(rows,params);
    		board.addView(rows, r);
    		//board.setColumnShrinkable(r, true);
    		//board.setColumnStretchable(r, true);
    	}
    
    }

    
    
	@Override
	public void update(Observable arg0, Object arg1) {
		Model model =  MyApplication.model;
		GameState state = model.getState();
		//check which state we in , and update the view
		if (state.getState() == GameState.VALID_MOVE){
			boardButtons[model.getRow()][model.getCol()].setText(model.getSymbolByTurn());
		}
		else if (state.getState() == GameState.RESTART){
			resetButtonText();
		}
		else if (state.getState() == GameState.RESIZE_BOARD){
			setupBoard(context);
		}
		else if (state.isGameOver()){
			handleGameOverState(state,model.getRow(),model.getCol(),model.getwinConstraint());
		}
		
	}
	
	private void resetButtonText(){
		for (int i=0; i< boardButtons.length; ++i ){
			for (int j=0; j< boardButtons.length; ++j ){
				boardButtons[i][j].setText("");
				boardButtons[i][j].getBackground().setColorFilter(Color.GRAY ,PorterDuff.Mode.MULTIPLY);
				boardButtons[i][j].setEnabled(true);
			}
		}
	}
	

	
	private void handleGameOverState(GameState state, int r, int c, int wc) {
		for (int i = 0; i < boardButtons.length; ++i) {
			if (state.getWiningLine().size() > 0 && i < wc) {
				Cell cell = state.getWiningLine().get(i);
				boardButtons[cell.row][cell.col].getBackground()
						.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
			}
			for (int j = 0; j < boardButtons.length; ++j) {
				boardButtons[i][j].setEnabled(false);
			}
		}
	}
	
	public void repaint(){
		Model model =  MyApplication.model;
		for (int i = 0; i < boardButtons.length; ++i) {
			for (int j = 0; j < boardButtons.length; ++j) {
				int piece = model.getBoardPiece(i, j);
				switch (piece){
				case Util.O:
					boardButtons[i][j].setText("O");
					break;
				case Util.X:
					boardButtons[i][j].setText("X");
					break;
				}
				//boardButtons[i][j].setEnabled(false);
			}
		}
		if (model.getState().isGameOver()) {
			handleGameOverState(model.getState(), model.getRow(), model.getCol(),
					model.getwinConstraint());
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
