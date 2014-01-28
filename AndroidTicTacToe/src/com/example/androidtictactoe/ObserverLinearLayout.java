package com.example.androidtictactoe;

import java.util.Observer;

public interface ObserverLinearLayout extends  Observer {
	public void onDestroy();
	public void onResume();
}
