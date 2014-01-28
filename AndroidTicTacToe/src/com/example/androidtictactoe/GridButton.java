package com.example.androidtictactoe;

import android.content.Context;
import android.widget.Button;

public class GridButton extends Button {
	
	public GridButton(Context context, int r, int c) {
		super(context);
		this.r = r;
		this.c = c;
	}
	
	int r;
	int c;
}
