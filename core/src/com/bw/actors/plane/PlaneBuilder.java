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
import com.bw.utils.Category;
import com.bw.utils.Mask;


public class PlaneBuilder {
	
	private World world;
	private PlaneType type;
	
	private Body plane, frontWheel, backWheel;
	private WheelJoint frontWheelJoint;
	
	private Vector2 respawn;
	
	public PlaneBuilder(World world, PlaneType type, Vector2 position){
		this.world = world;
		this.type = type; 
		this.respawn = position;
	}
	
	public PlaneBuilder build(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(respawn);
		bodyDef.fixedRotation = false;

		// temp shape
		PolygonShape planeCabin = new PolygonShape();
		planeCabin.setAsBox(type.getSise().x / 2, type.getSise().y / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = type.getDensity();
		fixtureDef.friction = type.getFriction();
		fixtureDef.restitution = type.getRestitution();
		fixtureDef.shape = planeCabin;
		fixtureDef.filter.categoryBits = Category.PLANE;
		fixtureDef.filter.maskBits = Mask.PLANE; 

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
	
	public void destroyPlane(){
		world.destroyBody(plane);
		world.destroyBody(backWheel);
		world.destroyBody(frontWheel);
	}
	
	public Body getNewBullet(){
		Body bullet;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(plane.getWorldPoint(new Vector2(3.5f, 0)));
		bodyDef.fixedRotation = false;

		CircleShape wheel = new CircleShape();
		wheel.setRadius(0.25f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 1;
		fixtureDef.restitution = 1;
		fixtureDef.shape = wheel;
		
		fixtureDef.filter.categoryBits = Category.BULLET;
		fixtureDef.filter.maskBits = Mask.BULLET; 
		
		bullet = world.createBody(bodyDef);
		bullet.createFixture(fixtureDef);
		return bullet;
	}
}
