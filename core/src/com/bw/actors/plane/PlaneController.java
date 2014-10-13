package com.bw.actors.plane;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.bw.actors.plane.types.PlaneCharacteristics;
import com.bw.utils.VectorUtils;

public class PlaneController implements InputProcessor {

	private Body plane;
	private float controllForce;
	private float currentControllForce = 0;
	private float speed;
	private boolean isFlying = false;
	private boolean isFall = false;
	private float angularDamping = 1;
	private boolean isMotorEnable = false;
	private Vector2 pointForFoce = new Vector2(2.5f, 0);
	private WheelJoint joint;
	private IShoot shootCallback = null;
	private long lastShoot = System.currentTimeMillis();
	private float reloadTime;
	private VectorUtils vectorUtils;
	private boolean lastForceApplyed = false;

	public PlaneController(Body plane, WheelJoint joint, PlaneCharacteristics planeType) {
		this.plane = plane;
		this.joint = joint;
		this.controllForce = planeType.getControlForce();
		this.speed = planeType.getSpeed();
		this.currentControllForce = planeType.getControlForce();
		this.reloadTime = planeType.getReloadTime();
		vectorUtils = new VectorUtils();
	}

	private float temp = 0;

	public void updateMooving() {
		if (isFall) {
			if (!lastForceApplyed) {
				Vector2 force = vectorUtils.getDirectionUnitVector(getCurrentAngle());
				force.x *= 100;
				force.y *= 100;
				plane.applyForceToCenter(force, true);
			}
			Vector2 normalToPlaneDireaction = vectorUtils.getTopDirectionUnitVector(getCurrentAngle());
			double coefDirection = normalToPlaneDireaction.dot(new Vector2(0, -1)); //1 - same direction, -1 opposite, 0 - perpendicular
			if (Math.abs(coefDirection) > 0.2) {
				float force = controllForce * 10 * (float) Math.signum(coefDirection);
				plane.applyForce(vectorUtils.getTopDirectionUnitVector(getCurrentAngle()).scl(force), plane.getWorldPoint(pointForFoce), true);
			}

		} else if (isFlying) {
			plane.setAngularDamping(angularDamping);
			plane.applyForce(vectorUtils.getTopDirectionUnitVector(getCurrentAngle()).scl(currentControllForce), plane.getWorldPoint(pointForFoce), true);
			plane.setLinearVelocity(vectorUtils.getDirectionUnitVector(getCurrentAngle()).scl(speed));
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
		case Keys.ENTER:
			((PlaneUserData) plane.getUserData()).getPlaneActor().prepareToEjectPilot();
			isFall = true;
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

	public boolean isFlying() {
		return isFlying;
	}

	public Vector2 getCurrentPosition() {
		return plane.getPosition();
	}

	public float getCurrentAngle() {
		return plane.getTransform().getRotation();
	}

	public interface IShoot {

		public void shoot();
	}

}
