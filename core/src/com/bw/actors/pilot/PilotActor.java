package com.bw.actors.pilot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


public class PilotActor {

	private PilotBuilder pilotBuilder;
	private PilotController pilotController;
	public PilotActor(World world){
		pilotBuilder = new PilotBuilder(world);
	}
	
	public void ejectPilot(Vector2 position, float angle, Vector2 force){
		pilotBuilder.build(position, angle).getPilot().applyForceToCenter(force, true);
		pilotBuilder.getPilot().setUserData(new PilotUserData(this));
	}
	
	/** 
	 * @return null if the pilot isn't ejected otherwise pilot controller
	 */
	public PilotController getPilotController(){
		if(pilotController == null) {
			if(pilotBuilder.getPilot() != null) 
				pilotController = new PilotController(pilotBuilder.getPilot());
		}
		return pilotController;
	}
	
	public void openParachute(){
		pilotBuilder.createParachute();
	}
	
}
