package com.galaxian;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
 
	private Joc joc; 
	
	@Override
	public void create () {
		joc = new Joc(this);
		setScreen(joc);
	}
	
	
}
