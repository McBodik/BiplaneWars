package com.bw.actors.plane;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.bw.actors.plane.types.PlaneType;

public class PlaneController implements InputProcessor {

	private Body plane;
	private float controllForce;
	private float currentControllForce = 0;
	private float speed;
	private boolean isFlying = false;
	private float angularDamping = 1;
	private boolean isMotorEnable = false;
	private Vector2 pointForFoce = new Vector2(2.5f, 0);
	private Vector2 forceVector = new Vector2();
	private Vector2 speedVector = new Vector2();
	private WheelJoint joint;
	private IShoot shootCallback = null;
	private long lastShoot = System.currentTimeMillis();
	private float reloadTime;

	public PlaneController(Body plane, WheelJoint joint, PlaneType planeType) {
		this.plane = plane;
		this.joint = joint;
		this.controllForce = planeType.getControllForce();
		this.speed = planeType.getSpeed();
		this.currentControllForce = planeType.getControllForce();
		this.reloadTime = planeType.getReloadTime();
	}

	private float temp = 0;

	public void updateMooving() {
		if (isFlying) {
			plane.setAngularDamping(angularDamping);
			plane.applyForce(getCotrollForceVector(), plane.getWorldPoint(pointForFoce), true);
			plane.setLinearVelocity(getSpeedVector());
		} else if (isMotorEnable) {
			if (!joint.isMotorEnabled()) {
				joint.getBodyB().setFixedRotation(false);
				joint.enableMotor(true);
				temp = plane.getPosition().y;
			}
			plane.setLinearVelocity(plane.getLinearVelocity().x, Math.abs(plane.getLinearVelocity().x) * 0.05f);
			plane.setAngularVelocity(0);

			if (plane.getPosition().y > temp + 0.2f) {
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
		return forceVector.set(currentControllForce * x, currentControllForce * y);
	}

	private void start() {
		if (Math.abs(plane.getLinearVelocity().y) < 1)
			isMotorEnable = true;
	}

	private boolean isReloaded() {
		if (lastShoot + (int) reloadTime * 1000 < System.currentTimeMillis())
			return true;
		return false;
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

		case Keys.SPACE:
			if (shootCallback != null && isReloaded()) {
				lastShoot = System.currentTimeMillis();
				shootCallback.shoot();
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

	public void setShootCallback(IShoot shoot) {
		shootCallback = shoot;
	}
	
	public boolean isFlying(){
		return isFlying;
	}
	
	public Vector2 getCurrentPosition(){
		return plane.getPosition();
	}

	public interface IShoot {

		public void shoot();
	}

}
