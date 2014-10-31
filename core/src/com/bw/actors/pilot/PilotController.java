package com.bw.actors.pilot;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PilotController implements InputProcessor {

	private boolean isParachuteOpen = false;
	private boolean isParachuteControlled = false;
	private float currentForce = 0;

	private Body pilot;
	private Body parachute;

	public PilotController(Body pilot) {
		this.pilot = pilot;
	}

	public void update() {
		if (isParachuteOpen) {
			if (Math.abs(parachute.getAngularVelocity()) > 0 && Math.abs(parachute.getTransform().getRotation()) < 0.1f) {
				parachute.setAngularVelocity(0);
				parachute.setTransform(parachute.getTransform().getPosition(), 0f);
				isParachuteControlled = true;
			}
			if (isParachuteControlled) {
				pilot.applyForceToCenter(new Vector2(currentForce, 0), true);
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			if (!isParachuteOpen)
				openParachute();
			break;
		case Keys.A:
		case Keys.D:
			if (isParachuteOpen && isParachuteControlled) {
				short sing = keycode == Keys.A ? (short)-1 : 1;
				parachute.setTransform(parachute.getTransform().getPosition(), sing * PARACHUTE_CONTROL_ANGLE);
				currentForce = sing * PARACHUTE_CONTROL_SPEED;
			}
			break;

		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if ((keycode == Keys.A || keycode == Keys.D) && isParachuteOpen && isParachuteControlled) {
			currentForce = 0;
			parachute.setTransform(parachute.getTransform().getPosition(), 0);
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
		pilot.setLinearDamping(PARACHUTE_FALL_DAMPING);
		parachute = ((PilotUserData) pilot.getUserData()).getPilotActor().openParachute();

		if (Math.sin(pilot.getAngle()) > 0) {
			parachute.setAngularVelocity(-2);
		} else {
			parachute.setAngularVelocity(2);
		}
	}
	
	public boolean canLand(){
		return isParachuteControlled && isParachuteOpen;
	}

	private final static float PARACHUTE_CONTROL_SPEED = 10.0f;
	private final static float PARACHUTE_FALL_DAMPING = 100.f;
	private final static float PARACHUTE_CONTROL_ANGLE = 0.2f;
}
