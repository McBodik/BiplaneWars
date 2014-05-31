package com.bw.actors.plane;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.InputProcessor;
import com.bw.actors.plane.types.PlaneType;

public class PlaneController implements InputProcessor {
	private Body plane;
	private float controllForce;
	private float currentControllForce = 0;
	private float speed;
	private float motorSpeed;
	private boolean isFlying = false;
	private float angularDamping = 1;
	private boolean isMotorEnable = false;
	private Vector2 pointForFoce = new Vector2(2.5f, 0);
	private Vector2 forceVector = new Vector2();
	private Vector2 speedVector = new Vector2();
	public WheelJoint joint;

	public PlaneController(Body plane, WheelJoint joint, PlaneType planeType) {
		this.plane = plane;
		this.joint = joint;
		this.controllForce = planeType.getControllForce();
		this.speed = planeType.getSpeed();
		this.motorSpeed = planeType.getMotorSpeed();
		this.currentControllForce = planeType.getControllForce();
	}

	public void updateMooving() {
		if (isFlying) {
			plane.setAngularDamping(angularDamping);
			plane.applyForce(getCotrollForceVector(),
					plane.getWorldPoint(pointForFoce), true);
			plane.setLinearVelocity(getSpeedVector());
		} else if (isMotorEnable) {
			if (!joint.isMotorEnabled()) {
				joint.enableMotor(true);
				joint.setMaxMotorTorque(motorSpeed);
				joint.setMotorSpeed(-motorSpeed);
			}
			plane.setLinearVelocity(plane.getLinearVelocity().x,
					Math.abs(plane.getLinearVelocity().x) * 0.1f);
			if (plane.getLinearVelocity().y > 0.9) {
				isFlying = true;
				joint.enableMotor(false);
			}
		}
	}

	private Vector2 getSpeedVector() {
		float rot = (float) (plane.getTransform().getRotation());
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return speedVector.set(speed * x, speed * y);
	}

	private Vector2 getCotrollForceVector() {
		float rot = (float) (plane.getTransform().getRotation() + Math.PI / 2);
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return forceVector.set(currentControllForce * x, currentControllForce
				* y);
	}

	private void start() {
		if (Math.abs(plane.getLinearVelocity().y) < 1)
			isMotorEnable = true;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
			if (isFlying) {
				currentControllForce = controllForce;
				angularDamping = 1;
			} else {
				start();
			}
			break;
		case Keys.S:
			if (isFlying) {
				currentControllForce = -controllForce;
				angularDamping = 1;
			} else {
				start();
			}
			break;

		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.W || keycode == Keys.S) {
			currentControllForce = 0;
			angularDamping = 10;
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

}
