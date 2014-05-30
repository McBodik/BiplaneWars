package com.bw.actors;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;

public class Plane extends InputAdapter {

	public Body plane, frontWheel, backWheel;
	public WheelJoint join;
	private boolean isFlight = false;

	public Plane(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(-20, -13));
		bodyDef.fixedRotation = false;

		// temp shape
		PolygonShape planeCabin = new PolygonShape();
		planeCabin.setAsBox(3f, 0.7f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0f;
		fixtureDef.shape = planeCabin;

		plane = world.createBody(bodyDef);
		plane.createFixture(fixtureDef);

		MassData md = plane.getMassData();
		//md.center.x = -0.5f;

		plane.setMassData(md);

		CircleShape wheel = new CircleShape();
		wheel.setRadius(0.25f);

		fixtureDef.shape = wheel;

		frontWheel = world.createBody(bodyDef);
		frontWheel.createFixture(fixtureDef);

		//wheel.setRadius(0.15f);
		backWheel = world.createBody(bodyDef);
		backWheel.createFixture(fixtureDef);

		WheelJointDef wheelDef = new WheelJointDef();
		wheelDef.bodyA = plane;
		wheelDef.bodyB = frontWheel;
		wheelDef.localAnchorA.set(new Vector2(1.5f, -1.5f));
		wheelDef.localAxisA.set(Vector2.Y);
		wheelDef.frequencyHz = 10;

		wheelDef.maxMotorTorque = 1000;
		wheelDef.motorSpeed = -100;
		wheelDef.enableMotor = false;

		world.createJoint(wheelDef);

		wheelDef.bodyA = plane;
		wheelDef.bodyB = backWheel;
		wheelDef.localAnchorA.set(new Vector2(-1.5f, -1.5f));
		wheelDef.localAxisA.set(Vector2.Y);
		wheelDef.frequencyHz = 10;

		join = (WheelJoint) world.createJoint(wheelDef);

	}

	Vector2 tmp = new Vector2(), tmp2 = new Vector2();
	private float acc = 20, leftAcc, rightAcc;

	public void update() {
		float rot = (float) (plane.getTransform().getRotation() + Math.PI / 2);
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);

		plane.applyForce(tmp.set(leftAcc * x, leftAcc * y), plane.getWorldPoint(tmp2.set(-3, 0)), true);
		plane.applyForce(tmp.set(rightAcc * x, rightAcc * y), plane.getWorldPoint(tmp2.set(3, 0)), true);
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
			if (isFlight)
				rightAcc = acc;
			else{
				join.enableMotor(true);
				isFlight = true;
			}
			break;
		case Keys.S:
			if (isFlight)
				rightAcc = -acc;
			else{
				join.enableMotor(true);
				isFlight = true;
			}
			break;

		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.Q || keycode == Keys.A)
			leftAcc = 0;
		else if (keycode == Keys.E || keycode == Keys.D)
			rightAcc = 0;
		else if (keycode == Keys.W || keycode == Keys.S) {
		} else
			return false;
		return true;
	}

}
