package com.bw.actors.pilot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class PilotBuilder {

	World world;

	Body pilot;
	Body parachute;

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

	public void createParachute() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pilot.getWorldPoint(new Vector2(0, 3)));
		bodyDef.angle = pilot.getAngle();
		bodyDef.fixedRotation = false;

		ChainShape parachuteShape = new ChainShape();
		parachuteShape.createChain(new Vector2[] { new Vector2(-1, 0), new Vector2(1, 0) });

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = DENSITY;
		fixtureDef.friction = FRICTION;
		fixtureDef.restitution = RESTITUTION;
		fixtureDef.shape = parachuteShape;

		parachute = world.createBody(bodyDef);
		parachute.createFixture(fixtureDef);

		//		RevoluteJointDef rjd = new RevoluteJointDef();
		//		rjd.bodyA = pilot;
		//		rjd.bodyB = parachute;
		//		rjd.localAnchorA.set(new Vector2(0, 3));
		//		rjd.upperAngle = 0.1f;
		//		rjd.lowerAngle= 0.1f;
		//		
		//		
		//		RevoluteJoint rj = (RevoluteJoint)world.createJoint(rjd);

		DistanceJointDef djd = new DistanceJointDef();
		djd.bodyA = pilot;
		djd.bodyB = parachute;
		djd.localAnchorA.set(new Vector2(0, 3));
		djd.localAnchorB.set(new Vector2(0, -3));
		
		world.createJoint(djd);
	}

	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 0.5f;
	private static final float RESTITUTION = 0.5f;

	private static final float SIZE_X = 0.4f;
	private static final float SIZE_Y = 1.8f;
}
