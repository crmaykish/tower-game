package com.cmaykish.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MyInputProcessor implements InputProcessor {

	private OrthographicCamera camera;
	private Renderer renderer;
	private Simulation simulation;
	
	public MyInputProcessor(OrthographicCamera camera, Renderer renderer, Simulation simulation) {
		this.camera = camera;
		this.renderer = renderer;
		this.simulation = simulation;
	}
	
	// TODO: cleanup all the references to renderer. whatever
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 temp = camera.unproject(new Vector3(screenX, screenY, 0));

		renderer.sx = temp.x;
		renderer.sy = temp.y;
		renderer.drawingShape = true;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		float compHeight = renderer.sh;

		if (compHeight < 0) {
			compHeight = Math.abs(compHeight);
			renderer.sy -= compHeight;
		}

		simulation.addRectangle(renderer.sx, renderer.sy, renderer.sw, compHeight);
		renderer.stopDrawingOutline();

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 drag = camera.unproject(new Vector3(screenX, screenY, 0));

		float height = drag.y - renderer.sy;
		renderer.sh = (float) (height > 0.0f ? Math.ceil(height) : Math.floor(height));
		renderer.sw = (float) Math.ceil(drag.x - renderer.sx);

		renderer.drawingShape = renderer.sw > 0 && renderer.sh != 0f;

		return false;
	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		}

		if (keycode == Keys.RIGHT) {
			camera.position.x++;
		}

		if (keycode == Keys.LEFT) {
			camera.position.x--;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
