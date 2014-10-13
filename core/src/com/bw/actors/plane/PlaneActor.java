package com.bw.actors.plane;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bw.actors.pilot.PilotActor;
import com.bw.actors.plane.PlaneController.IShoot;
import com.bw.actors.plane.types.PlaneCharacteristics;
import com.bw.utils.VectorUtils;

public class PlaneActor {


	World world;

    private short planeStatus;
    private PlaneBuilder planeBuilder;
    private PlaneController planeController;
    private PilotActor pilot;

    private final short STATUS_IS_ALIVE = -1;
    private final short STATUS_PREPARE_TO_KILL = 0;
    private final short STATUS_KILLED = 1;
    private final short STATUS_PREPARE_TO_EJECT = 2;
    private final short STATUS_PILOT_EJECTED = 3;

	public PlaneActor(World world, PlaneCharacteristics planeType, Vector2 position) {
		this.world = world;
		planeBuilder = new PlaneBuilder(world, planeType, position).build();
		planeController = new PlaneController(planeBuilder.getPlaneBody(), planeBuilder.getPlaneWheelJoint(), planeType);
		planeController.setShootCallback(new IShoot() {

			@Override
			public void shoot() {
				PlaneActor.this.shoot();

			}
		});
		planeBuilder.getPlaneBody().setUserData(new PlaneUserData(this));
		planeStatus = STATUS_IS_ALIVE;
		pilot = new PilotActor(world);
	}

	public PlaneController getPlaneController() {
		return planeController;
	}

	public void shoot() {
		planeBuilder.getNewBullet().applyForceToCenter(getSpeedForBulletVector(), true);
	}

	private Vector2 getSpeedForBulletVector() {
		float rot = planeBuilder.getPlaneBody().getTransform().getRotation();
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return new Vector2(1000 * x, 1000 * y);
	}

	public void update() {
		if (planeStatus == STATUS_PREPARE_TO_KILL) {
			killingPlane();	
		}
		if (planeStatus == STATUS_PREPARE_TO_EJECT){
			ejectPilot();
		}
		if (planeStatus == STATUS_IS_ALIVE || planeStatus == STATUS_PILOT_EJECTED) {
			planeController.updateMooving();
		}
	}

	public void prepareToKillPlane() {
		planeStatus = STATUS_PREPARE_TO_KILL;
	}
	
	private void killingPlane(){
		planeBuilder.destroyPlane();
		planeStatus = STATUS_KILLED;
	}
	
	public void prepareToEjectPilot(){
		planeStatus = STATUS_PREPARE_TO_EJECT;
	}
	
	private void ejectPilot(){
		//TODO less speed
		planeStatus = STATUS_PILOT_EJECTED;
		Vector2 force = new Vector2(new VectorUtils().getTopDirectionUnitVector(planeBuilder.getPlaneBody().getTransform().getRotation()));
		force.x *= 50;
		force.y *= 50;
		Vector2 position = planeBuilder.getPlaneBody().getPosition();
		position.y+=2; 
		pilot.ejectPilot(position, planeBuilder.getPlaneBody().getTransform().getRotation(), force);
		
	}
}
