package com.bw.actors.pilot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class PilotActor {

	private short pilotStatus;

	private PilotBuilder pilotBuilder;
	private PilotController pilotController;

	public PilotActor(World world) {
		pilotStatus = STATUS_IS_ALIVE;
		pilotBuilder = new PilotBuilder(world);
	}

	public void ejectPilot(Vector2 position, float angle, Vector2 force) {
		pilotBuilder.build(position, angle).getPilot().applyForceToCenter(force, true);
		pilotBuilder.getPilot().setUserData(new PilotUserData(this));
	}

	/** 
	 * @return null if the pilot isn't ejected otherwise pilot controller
	 */
	public PilotController getPilotController() {
		if (pilotController == null) {
			if (pilotBuilder.getPilot() != null)
				pilotController = new PilotController(pilotBuilder.getPilot());
		}
		return pilotController;
	}

	public void update() {
		pilotController.update();
		if (pilotStatus == STATUS_PREPARE_TO_KILL) {
			killPilot();
		}
		if (pilotStatus == STATUS_PREPARE_TO_LANDING) {
			landing();
		}

	}

	private void killPilot() {
		pilotBuilder.killPilot();
		pilotStatus = STATUS_KILLED;
		Gdx.input.setInputProcessor(null);
	}
	
	private void landing(){
		pilotBuilder.unhookParachute();
	}

	public Body openParachute() {
		return pilotBuilder.createParachute();
	}

	public void prepareToKillPilot() {
		pilotStatus = STATUS_PREPARE_TO_KILL;
	}

	public void prepareToLanding() {
		if (pilotController.canLand())
			pilotStatus = STATUS_PREPARE_TO_LANDING;
		else {
			pilotStatus = STATUS_PREPARE_TO_KILL;
		}
	}

	private final short STATUS_IS_ALIVE = -1;
	private final short STATUS_PREPARE_TO_KILL = 0;
	private final short STATUS_KILLED = 1;
	private final short STATUS_PREPARE_TO_LANDING = 2;

}
