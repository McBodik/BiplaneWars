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
import com.bw.utils.Category;
import com.bw.utils.Mask;

public class PilotBuilder {

	private World world;

	private Body pilot;
	private Body parachute;

	private WeldJoint parachuteJoint;

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
		fixtureDef.filter.categoryBits = Category.PILOT;
		fixtureDef.filter.maskBits = Mask.PILOT;

		pilot = world.createBody(bodyDef);
		pilot.createFixture(fixtureDef);

		return this;
	}

	public void killPilot() {
		unhookParachute();
		if (pilot != null) {
			world.destroyBody(pilot);
			pilot = null;
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

		parachuteJoint = (WeldJoint) world.createJoint(wjd);

		return parachute;
	}

	public void unhookParachute() {
		if (parachuteJoint != null) {
			world.destroyJoint(parachuteJoint);
			parachuteJoint = null;
		}
		if (parachute != null) {
			world.destroyBody(parachute);
			parachute = null;
		}
		pilot.setTransform(pilot.getPosition(), 0);
		pilot.setFixedRotation(true);
	}

	private static final float DENSITY = 0.1f;
	private static final float FRICTION = 0.5f;
	private static final float RESTITUTION = 0.5f;

	private static final float SIZE_X = 0.4f;
	private static final float SIZE_Y = 1.2f;
}
