package com.example.androidtictactoe;

public interface ComputerCallBack {
	public int getPoint(int r, int c);
	public int getWinConstaint();
	public void finishLookAhead();
}
