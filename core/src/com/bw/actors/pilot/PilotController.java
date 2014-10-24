package com.bw.actors.pilot;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class PilotController implements InputProcessor {

	private boolean isParachuteOpen = false;
	private float currentForce = 0;

	private Body pilot;
	private Body parachute;

	public PilotController(Body pilot) {
		this.pilot = pilot;
	}

	public void update() {
		pilot.applyForceToCenter(new Vector2(currentForce, 0), true);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			openParachute();
			break;
		case Keys.A:
			if (isParachuteOpen) {
				currentForce = -FORCE;
			}
			break;
		case Keys.D:
			if (isParachuteOpen) {
				currentForce = FORCE;
			}
			break;

		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.A || keycode == Keys.D) {
			currentForce = 0;
		} else
			return false;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void openParachute() {
		isParachuteOpen = true;
		pilot.setLinearDamping(PARACHUTE_SPEED);
		((PilotUserData) pilot.getUserData()).getPilotActor().openParachute();
	}

	private final static float FORCE = 0.7f;
	private final static float PARACHUTE_SPEED = 1f;
}
