package com.bw.actors.pilot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class PilotBuilder {

	World world;

	Body pilot;
	Body parachute;
	
	WeldJoint parachuteJoint;

	public PilotBuilder(World world) {
		this.world = world;
	}

	public PilotBuilder build(Vector2 position, float angle) {
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

	public Body getPilot() {
		return pilot;
	}

	public Body createParachute() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pilot.getWorldPoint(new Vector2(0, 1.5f)));
		bodyDef.angle = pilot.getAngle();
		bodyDef.fixedRotation = false;

		ChainShape parachuteShape = new ChainShape();
		parachuteShape.createChain(new Vector2[] { new Vector2(-0.5f, 0), new Vector2(0.5f, 0) });

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 0;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.shape = parachuteShape;

		parachute = world.createBody(bodyDef);
		parachute.createFixture(fixtureDef);

		WeldJointDef wjd = new WeldJointDef();
		wjd.bodyA = pilot;
		wjd.bodyB = parachute;
		wjd.localAnchorA.set(new Vector2(0, 1.5f));
				
		parachuteJoint = (WeldJoint)world.createJoint(wjd);
		return parachute;
	}

	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 0.5f;
	private static final float RESTITUTION = 0.5f;

	private static final float SIZE_X = 0.4f;
	private static final float SIZE_Y = 1.2f;
}
