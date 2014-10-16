package com.bw.actors.pilot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class PilotBuilder {
	
	World world;
	
	Body pilot;
	
	public PilotBuilder(World world){
		this.world = world;
	}
	
	public PilotBuilder build(Vector2 position, float angle){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.angle = angle;
		bodyDef.fixedRotation = false;
		
		PolygonShape pilotShape = new PolygonShape();
		pilotShape.setAsBox(SIZE_X / 2, SIZE_Y / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = DENSITY;
		fixtureDef.friction = FRICTION;
		fixtureDef.restitution = RESTITUTION;
		fixtureDef.shape = pilotShape;
		//TODO add filters
		
		pilot = world.createBody(bodyDef);
		pilot.createFixture(fixtureDef);
		
		return this;
	}
	
	public void killPilot() {
		if (pilot != null) {
			pilot = null;
			world.destroyBody(pilot);
		}
	}

	public Body getPilot(){
		return pilot;
	}
	
	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 0.5f;
	private static final float RESTITUTION = 0.5f;
	
	private static final float SIZE_X = 0.4f;
	private static final float SIZE_Y = 1.8f;
}
