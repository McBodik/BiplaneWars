package com.bw.bot;

import com.badlogic.gdx.Input.Keys;
import com.bw.actors.plane.PlaneController;

public class BotSimple extends Bot {

	private PlaneController controoler;
	private boolean isFlying = false;
	private boolean keyDown = false;

	public BotSimple(PlaneController planeControoler) {
		controoler = planeControoler;

		controoler.keyDown(Keys.W);
	}

	public void update() {
		if (!isFlying && controoler.isFlying()) {
			controoler.keyUp(Keys.W);
			isFlying = true;
		}

		if (isFlying && updateNearestObstacles(controoler.getCurrentPosition())) {
			if (!keyDown) {
				controoler.keyDown(choiceWayToAvoidObstacles(controoler.getCurrentAngle()) ? Keys.W : Keys.S);
				keyDown = true;
			}
		} else {
			resetPress();
		}
	}

	private void resetPress() {
		keyDown = false;
		controoler.keyUp(Keys.W);
		controoler.keyUp(Keys.S);
	}

}
