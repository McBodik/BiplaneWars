package com.bw.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;


public class Plane {

	public Body plane, frontWheel, backWheel;

	public Plane(World world){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(new Vector2(-20, -10));
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
		md.center.x = 0.08f;
		
		plane.setMassData(md);
		
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
		//wheelDef.enableMotor = true;
		
		world.createJoint(wheelDef);
		
		wheelDef.bodyA = plane;
		wheelDef.bodyB = backWheel;
		wheelDef.localAnchorA.set(new Vector2(-2, -1.3f));
		wheelDef.localAxisA.set(Vector2.Y);
		wheelDef.frequencyHz = 10;
		
		world.createJoint(wheelDef);
	}
}
