package com.bw.actors.plane;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.bw.actors.plane.types.PlaneType;

public class PlaneActor {

	public Body plane, frontWheel, backWheel;
	private PlaneController planeController;

	public PlaneActor(World world, PlaneType planeType) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(-20, -13));
		bodyDef.fixedRotation = false;

		// temp shape
		PolygonShape planeCabin = new PolygonShape();
		planeCabin.setAsBox(3f, 0.7f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 20f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0f;
		fixtureDef.shape = planeCabin;

		plane = world.createBody(bodyDef);
		plane.createFixture(fixtureDef);

		CircleShape wheel = new CircleShape();
		wheel.setRadius(0.25f);

		fixtureDef.shape = wheel;

		frontWheel = world.createBody(bodyDef);
		frontWheel.createFixture(fixtureDef);

		wheel.setRadius(0.15f);
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

		WheelJoint joint = (WheelJoint) world.createJoint(wheelDef);
		plane.setAngularDamping(1f);
		
		planeController = new PlaneController(plane, joint, planeType); 
	}
	
	public PlaneController getPlaneController(){
		return planeController;
	}

	public void update() {
		planeController.updateMooving();
	}
}
