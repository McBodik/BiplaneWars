package com.bw.actors.plane;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.bw.actors.plane.PlaneController.IShoot;
import com.bw.actors.plane.types.PlaneType;

public class PlaneActor {
	public static boolean iskilled = false;
	private PlaneBuilder planeBuilder;
	private PlaneController planeController;
	World world;

	public PlaneActor(World world, PlaneType planeType) {
		this.world = world;
		planeBuilder = new PlaneBuilder(world, planeType).build();
		planeController = new PlaneController(planeBuilder.getPlaneBody(),
				planeBuilder.getPlaneWheelJoint(), planeType);
		planeController.setShootCallback(new IShoot() {

			@Override
			public void shoot() {
				PlaneActor.this.shoot();

			}
		});
	}

	public PlaneController getPlaneController() {
		return planeController;
	}

	public void shoot() {
		planeBuilder.getNewBullet().applyForceToCenter(getSpeedVector(), true);
	}

	private Vector2 getSpeedVector() {
		float rot = (float) (planeBuilder.getPlaneBody().getTransform()
				.getRotation());
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return new Vector2(1000 * x, 1000 * y);
	}

	public void update() {
		if (planeBuilder.isPlaneEnable()) {
			planeController.updateMooving();
			if (iskilled) {
				planeBuilder.destroyPlane();
			}
		}
	}
}
