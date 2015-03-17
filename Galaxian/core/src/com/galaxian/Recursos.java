package com.galaxian;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Recursos {
	
	
	public static TextureRegion jugador;
	
	
	public static void load() {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sheet.xml"));
		jugador = atlas.findRegion("enemyBlack1.png");	
	}
}
