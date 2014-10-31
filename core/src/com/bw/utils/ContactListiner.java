package com.bw.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.bw.actors.pilot.PilotUserData;
import com.bw.actors.plane.PlaneUserData;

public class ContactListiner implements ContactListener {

	private Array<Body> bulletRecycleBin;

	public ContactListiner(Array<Body> bulletRecycleBin) {
		this.bulletRecycleBin = bulletRecycleBin;
	}

	@Override
	public void beginContact(Contact contact) {

		if (contact.getFixtureA().getFilterData().categoryBits == Category.BULLET) {
			bulletRecycleBin.add(contact.getFixtureA().getBody());
		}

		if (contact.getFixtureB().getFilterData().categoryBits == Category.BULLET) {
			bulletRecycleBin.add(contact.getFixtureB().getBody());
		}

		if (contact.getFixtureB().getFilterData().categoryBits == Category.PLANE || contact.getFixtureA().getFilterData().categoryBits == Category.PLANE) {
			//bullet
			if (contact.getFixtureA().getFilterData().categoryBits == Category.BULLET || contact.getFixtureB().getFilterData().categoryBits == Category.BULLET) {
				if (contact.getFixtureA().getFilterData().categoryBits == Category.PLANE) {
					((PlaneUserData) contact.getFixtureA().getBody().getUserData()).getPlaneActor().prepareToKillPlane();
				} else if (contact.getFixtureB().getFilterData().categoryBits == Category.PLANE) {
					((PlaneUserData) contact.getFixtureB().getBody().getUserData()).getPlaneActor().prepareToKillPlane();
				}
			}

			if (contact.getFixtureA().getFilterData().categoryBits == Category.GROUND || contact.getFixtureB().getFilterData().categoryBits == Category.GROUND) {
				if (contact.getFixtureA().getFilterData().categoryBits == Category.PLANE) {
					if ((PlaneUserData) contact.getFixtureA().getBody().getUserData() != null)
						((PlaneUserData) contact.getFixtureA().getBody().getUserData()).getPlaneActor().prepareToKillPlane();
				} else if (contact.getFixtureB().getFilterData().categoryBits == Category.PLANE) {
					if ((PlaneUserData) contact.getFixtureB().getBody().getUserData() != null)
						((PlaneUserData) contact.getFixtureB().getBody().getUserData()).getPlaneActor().prepareToKillPlane();
				}
			}
		}

		if (contact.getFixtureA().getFilterData().categoryBits == Category.PILOT || contact.getFixtureB().getFilterData().categoryBits == Category.PILOT) {
			if (contact.getFixtureA().getFilterData().categoryBits == Category.PILOT) {
				if (contact.getFixtureB().getFilterData().categoryBits == Category.GROUND) {
					((PilotUserData) contact.getFixtureA().getBody().getUserData()).getPilotActor().prepareToLanding();
				} else {
					((PilotUserData) contact.getFixtureA().getBody().getUserData()).getPilotActor().prepareToKillPilot();
				}
			}
			if (contact.getFixtureB().getFilterData().categoryBits == Category.PILOT) {
				if (contact.getFixtureA().getFilterData().categoryBits == Category.GROUND) {
					((PilotUserData) contact.getFixtureB().getBody().getUserData()).getPilotActor().prepareToLanding();
				} else {
					((PilotUserData) contact.getFixtureB().getBody().getUserData()).getPilotActor().prepareToKillPilot();
				}
			}
		}

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
