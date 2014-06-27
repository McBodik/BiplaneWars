package com.bw.bot;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.bw.actors.plane.PlaneController;

public class BotSimple extends Bot {

	private PlaneController controoler;
	private boolean isFlying = false;
	private int currentKeyPressed = KEY_UNPRESSED;

	// цей бот літає по еліпсу
	private float aX; // велика піввісь еліпса
	private float bY; // маленька піввісь еліпса

	public BotSimple(PlaneController planeControoler) {
		super();
		controoler = planeControoler;

		controoler.keyDown(Keys.W);
		aX = rightWall - 10;
		bY = topWall - 10;
	}

	public void update() {
		if (!isFlying && controoler.isFlying()) {
			resetPress();
			isFlying = true;
		} else {
			if (isFlying && updateNearestObstacles(controoler.getCurrentPosition())) {
				int press = choiceWayToAvoidObstacles(controoler.getCurrentAngle());
				if (currentKeyPressed != press) {
					currentKeyPressed = press;
					controoler.keyDown(currentKeyPressed);
				}
			} else {
				int press = fly();
				if (currentKeyPressed != press) {
					resetPress();
					currentKeyPressed = press;
					controoler.keyDown(currentKeyPressed);
				}
			}
		}
	}

	private int fly() {
		float rotationAngle = controoler.getCurrentAngle();
		float currentX = controoler.getCurrentPosition().x;
		float currentY = controoler.getCurrentPosition().y;
		float cos = MathUtils.cos(rotationAngle);
		float sin = MathUtils.sin(rotationAngle);
		if (aX < currentX) {
			return Keys.W;
		} else {
			float wayYPoint = (float) Math.sqrt((1 - ((currentX * currentX) / (aX * aX))) * (bY * bY));
			if (currentY > 0) {
				if (Math.abs(cos) > 0.8)
					return KEY_UNPRESSED;
				else if (currentY > wayYPoint)
					return Keys.W;
				else
					return Keys.S;
			} else {
				if (currentY > (-1) * wayYPoint)
					return Keys.S;
				else
					return Keys.W;
			}
		}
	}

	private void resetPress() {
		currentKeyPressed = KEY_UNPRESSED;
		controoler.keyUp(Keys.W);
		controoler.keyUp(Keys.S);
	}
}
