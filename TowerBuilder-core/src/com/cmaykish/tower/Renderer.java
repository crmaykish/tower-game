package com.cmaykish.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Renderer {
	private Simulation simulation;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Box2DDebugRenderer debugRenderer;

	TextureRegion ball;
	TextureRegion bg;
	TextureRegion wood;

	public boolean drawingShape = false;
	public float sx, sy, sw, sh;

	public Renderer(Simulation simulation) {
		this.simulation = simulation;
		
		initializeCamera();
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		debugRenderer = new Box2DDebugRenderer();
		
		loadTextures();
	}

	private void loadTextures() {
		ball = new TextureRegion(new Texture(Gdx.files.internal("ball.png")));
		bg = new TextureRegion(new Texture(Gdx.files.internal("background.jpeg")));
		wood = new TextureRegion(new Texture(Gdx.files.internal("wood.png")));
	}
	
	private void initializeCamera(){
		camera = new OrthographicCamera(Simulation.WIDTH, Simulation.HEIGHT);
		camera.position.x = Simulation.WIDTH / 2;
		camera.position.y = Simulation.HEIGHT / 2;
	}
	
	public void render(){
		camera.update();
		renderSpriteBatch();
		renderShapes();
		
		if (TowerGame.DEBUG){
			debugRenderer.render(simulation.getWorld(), camera.combined);
		}
	}
	
	//TODO: this needs some serious cleanup
	private void renderSpriteBatch(){
		Gdx.gl.glClearColor(0.467f, 0.533f, 0.667f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(bg, 0, 0, 2560/Simulation.SCALE_FACTOR, 1440/Simulation.SCALE_FACTOR);
		
		for (Body b : simulation.getBalls()){
			batch.draw(ball, b.getPosition().x - 32f/Simulation.SCALE_FACTOR, b.getPosition().y - 32f/Simulation.SCALE_FACTOR, 32f/Simulation.SCALE_FACTOR, 32f/Simulation.SCALE_FACTOR, 64f/Simulation.SCALE_FACTOR, 64f/Simulation.SCALE_FACTOR, 1, 1, (float) Math.toDegrees(b.getAngle()));
		}
		
		for (RectangleWrapper r : simulation.getRectangles()){
			batch.draw(wood, r.body.getPosition().x - r.width/2, r.body.getPosition().y - r.height/2, r.width/2, r.height/2, r.width, r.height, 1, 1, (float) Math.toDegrees(r.body.getAngle()));
		}
		
		batch.end();
	}
	
	private void renderShapes(){
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		if (drawingShape){
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(1, 1, 1, 0.3f);
			shapeRenderer.rect(sx, sy, sw, sh);
			shapeRenderer.end();
		}
		
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	public void stopDrawingOutline() {
		sx = 0;
		sy = 0;
		sw = 0;
		sh = 0;
		drawingShape = false;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
	
}
