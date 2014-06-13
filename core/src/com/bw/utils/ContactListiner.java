package com.bw.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.bw.actors.plane.PlaneUserData;

public class ContactListiner implements ContactListener {

	private Array<Body> bulletRecycleBin;
	
	public ContactListiner(Array<Body> bulletRecycleBin) {
		this.bulletRecycleBin = bulletRecycleBin;
	}

	@Override
	public void beginContact(Contact contact) {
		
		if(contact.getFixtureA().getFilterData().categoryBits == Category.BULLET){
			bulletRecycleBin.add(contact.getFixtureA().getBody());
		}
		
		if(contact.getFixtureB().getFilterData().categoryBits == Category.BULLET){
			bulletRecycleBin.add(contact.getFixtureB().getBody());
		}
		
		if ((contact.getFixtureA().getFilterData().categoryBits == Category.BULLET && contact.getFixtureB().getFilterData().categoryBits == Category.PLANE)
				|| (contact.getFixtureB().getFilterData().categoryBits == Category.BULLET && contact.getFixtureA().getFilterData().categoryBits == Category.PLANE)) {
			if (contact.getFixtureA().getFilterData().categoryBits == Category.PLANE) {
				((PlaneUserData) contact.getFixtureA().getBody().getUserData()).getPlaneActor().killPlane();
			} else if (contact.getFixtureB().getFilterData().categoryBits == Category.PLANE) {
				((PlaneUserData) contact.getFixtureB().getBody().getUserData()).getPlaneActor().killPlane();
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
