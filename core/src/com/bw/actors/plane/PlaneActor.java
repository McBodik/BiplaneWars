package com.bw.actors.plane;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bw.actors.plane.PlaneController.IShoot;
import com.bw.actors.plane.types.PlaneType;

public class PlaneActor {

	private short planeStatus;
	private PlaneBuilder planeBuilder;
	private PlaneController planeController;
	World world;

	public PlaneActor(World world, PlaneType planeType, Vector2 position) {
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
	}

	public PlaneController getPlaneController() {
		return planeController;
	}

	public void shoot() {
		planeBuilder.getNewBullet().applyForceToCenter(getSpeedVector(), true);
	}

	private Vector2 getSpeedVector() {
		float rot = (float) (planeBuilder.getPlaneBody().getTransform().getRotation());
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return new Vector2(1000 * x, 1000 * y);
	}

	public void update() {
		if (planeStatus == STATUS_PREPARE_TO_KILL) {
			planeBuilder.destroyPlane();
			planeStatus = STATUS_KILLED;
		}
		if (planeStatus == STATUS_IS_ALIVE) {
			planeController.updateMooving();
		}
	}

	public void killPlane() {
		planeStatus = STATUS_PREPARE_TO_KILL;
	}
	
	private final short STATUS_IS_ALIVE = -1;
	private final short STATUS_PREPARE_TO_KILL = 0;
	private final short STATUS_KILLED = 1;
}
