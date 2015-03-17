package com.galaxian;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



public class Joc extends Inici {
	
	private MyGame game;
	private SpriteBatch batch;
	private World galaxy;
	
	Image ship;
	float x;
	private void nau() {
		BodyDef bd = new BodyDef();
		bd.position.set(4, 2);
		bd.type = BodyType.DynamicBody;
		/*Body oBody = oWorld.createBody(bd);
		oBody.createFixture(fixDef);*/
	}
	
	
	

	
	public Joc(MyGame game) {
		super();
		this.game = game;
		ship = new Image(Recursos.jugador);
		

	    
	    batch = new SpriteBatch();
	    
		x = Gdx.graphics.getWidth() / 2.0f;
	}
	
	public void create() {
		
	}
	
	private void refresh_ship() {
		int x_coord = Gdx.input.getX();
		if(x_coord < x) --x;
		else ++x;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(Gdx.input.isTouched()){
			refresh_ship();
		}
		
		batch.begin();
		
		batch.end();
		
		
	}
	
	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
