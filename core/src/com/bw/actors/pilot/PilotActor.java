package com.bw.actors.pilot;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bw.actors.plane.PlaneController;


public class PilotActor {

	private PilotBuilder pilotBuilder;
	private PlaneController planeController;
	public PilotActor(World world){
		pilotBuilder = new PilotBuilder(world);
	}
	
	public void ejectPilot(Vector2 position, float angle, Vector2 force){
		pilotBuilder.build(position, angle).getPilot().applyForceToCenter(force, true);
	}
	
}
