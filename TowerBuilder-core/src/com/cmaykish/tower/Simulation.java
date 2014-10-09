package com.cmaykish.tower;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Simulation {
	public static final float SCALE_FACTOR = 40f;
	public static final int WIDTH = (int) (Gdx.graphics.getWidth() / SCALE_FACTOR);
	public static final int HEIGHT = (int) (Gdx.graphics.getHeight() / SCALE_FACTOR);
	
	private World world;
	
	private Body groundBody;
	
	private List<Body> balls;
	private List<RectangleWrapper> rectangles;
	
	private FixtureDef fixtureDef;
	
	public Simulation() {
		world = new World(new Vector2(0,  -9.8f), true);
		
		defineStuff();
		
		balls = new ArrayList<Body>();
		rectangles = new ArrayList<RectangleWrapper>();
		
		addGround();
	}
	
	public void update(){
		world.step(1/45f, 6, 2); //TODO: look these up
	}
	
	private void defineStuff(){
		CircleShape circle = new CircleShape();
		circle.setRadius(32f / SCALE_FACTOR);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 6.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.3f;
	}
	
	private void addGround(){
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(WIDTH / 2, -1));
		
		groundBody = world.createBody(groundBodyDef);
		
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(12000f, 1f);
		groundBody.createFixture(groundBox, 0f);
	}

	public void addRectangle(float x, float y, float w, float h) {
		if (w > 0 && h > 0) {
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			bodyDef.position.set(x + w / 2, y + h / 2);

			Body square = world.createBody(bodyDef);
			PolygonShape ss = new PolygonShape();
			ss.setAsBox(w / 2, h / 2);

			FixtureDef lolDef = new FixtureDef();
			lolDef.density = 0.5f;
			lolDef.friction = 0.7f;
			lolDef.restitution = 0.2f;
			lolDef.shape = ss;

			square.createFixture(lolDef);

			RectangleWrapper wrapper = new RectangleWrapper();
			wrapper.body = square;
			wrapper.width = w;
			wrapper.height = h;

			rectangles.add(wrapper);
		}
	}
	
	public void addBall(float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		body.applyLinearImpulse(new Vector2(0.0f, 0.0f), body.getPosition(), true);

		balls.add(body);
	}

	public List<Body> getBalls() {
		return balls;
	}

	public List<RectangleWrapper> getRectangles() {
		return rectangles;
	}

	public World getWorld() {
		return world;
	}
	
}
