package com.bw.actors.plane;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bw.actors.plane.types.PlaneType;

public class PlaneActor {

	private PlaneBuilder planeBuilder;
	private PlaneController planeController;
	World world;

	public PlaneActor(World world, PlaneType planeType) {
		this.world = world;
		planeBuilder = new PlaneBuilder().build(world, planeType);
		planeController = new PlaneController(planeBuilder.getPlaneBody(), planeBuilder.getPlaneWheelJoint(), planeType);
	}
	
	public PlaneController getPlaneController(){
		return planeController;
	}
	
	public void shoot(){
		if(planeController.shoot){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(planeBuilder.getPlaneBody().getWorldPoint(new Vector2(3, 0)));
		bodyDef.fixedRotation = false;
		
		CircleShape wheel = new CircleShape();
		wheel.setRadius(0.25f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 1;
		fixtureDef.restitution = 1;
		fixtureDef.shape = wheel;
		
		Body pulya;
		pulya = world.createBody(bodyDef);
		pulya.createFixture(fixtureDef);
		pulya.applyForceToCenter(getSpeedVector(), true);
		planeController.shoot = false;
	}
		
	}
	
	private Vector2 getSpeedVector() {
		float rot = (float) (planeBuilder.getPlaneBody().getTransform().getRotation());
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return new Vector2(1000 * x, 1000 * y);
	}

	public void update() {
		planeController.updateMooving();
		shoot();
	}
}
