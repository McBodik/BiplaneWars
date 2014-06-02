package com.bw.actors.plane;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.bw.actors.plane.types.PlaneType;


public class PlaneBuilder {
	
	private Body plane, frontWheel, backWheel;
	private WheelJoint frontWheelJoint;
	
	public PlaneBuilder build(World world, PlaneType type){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(-20, -13));
		bodyDef.fixedRotation = false;

		// temp shape
		PolygonShape planeCabin = new PolygonShape();
		planeCabin.setAsBox(type.getSise().x / 2, type.getSise().y / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = type.getDensity();
		fixtureDef.friction = type.getFriction();
		fixtureDef.restitution = type.getRestitution();
		fixtureDef.shape = planeCabin;

		plane = world.createBody(bodyDef);
		plane.createFixture(fixtureDef);

		CircleShape wheel = new CircleShape();
		wheel.setRadius(0.25f);

		fixtureDef.shape = wheel;

		frontWheel = world.createBody(bodyDef);
		fixtureDef.friction = 1;
		frontWheel.createFixture(fixtureDef);

		wheel.setRadius(0.15f);
		backWheel = world.createBody(bodyDef);
		backWheel.createFixture(fixtureDef);

		WheelJointDef wheelDef = new WheelJointDef();
		wheelDef.bodyA = plane;
		wheelDef.bodyB = frontWheel;
		wheelDef.localAnchorA.set(new Vector2(1.2f, -1.5f));
		wheelDef.localAxisA.set(Vector2.Y);
		wheelDef.frequencyHz = type.getFrequencyHz();

		wheelDef.maxMotorTorque = type.getMotorSpeed();
		wheelDef.motorSpeed = -type.getMotorSpeed();
		wheelDef.enableMotor = false;

		world.createJoint(wheelDef);

		wheelDef.bodyB = backWheel;
		wheelDef.localAnchorA.set(new Vector2(-1.8f, -1.3f));

		frontWheelJoint = (WheelJoint)world.createJoint(wheelDef);
		plane.setAngularDamping(1f);
		return this;
	}
	
	public Body getPlaneBody(){
		return plane;
	}
	public WheelJoint getPlaneWheelJoint(){
		return frontWheelJoint;
	}
}
