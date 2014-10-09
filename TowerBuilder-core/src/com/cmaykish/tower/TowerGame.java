package com.cmaykish.tower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class TowerGame extends ApplicationAdapter {
	public static final boolean DEBUG = false;

	private Simulation simulation;
	private Renderer renderer;

	@Override
	public void create() {
		simulation = new Simulation();
		renderer = new Renderer(simulation);

		MyInputProcessor inputProcessor = new MyInputProcessor(renderer.getCamera(), renderer, simulation);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render() {
		simulation.update();
		renderer.render();
	}

}
