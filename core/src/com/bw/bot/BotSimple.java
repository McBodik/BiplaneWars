package com.bw.bot;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.bw.actors.plane.PlaneController;
import com.bw.utils.VectorUtils;

public class BotSimple extends Bot {

	private PlaneController controoler;
	private boolean isFlying = false;
	private int currentKeyPressed = KEY_UNPRESSED;
	private VectorUtils vectorUtils;

	// цей бот літає по еліпсу
	private float aX; // велика піввісь еліпса
	private float bY; // маленька піввісь еліпса

	public BotSimple(PlaneController planeControoler) {
		super();
		controoler = planeControoler;

		controoler.keyDown(Keys.W);
		aX = rightWall - 10;
		bY = topWall - 10;
		vectorUtils = new VectorUtils();
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
		Vector2 currentPosition = controoler.getCurrentPosition();
		float currentX = currentPosition.x;
		float currentY = currentPosition.y;
		Vector2 direction = vectorUtils.getDirectionUnitVector(controoler.getCurrentAngle());
		
		Vector2 condConn1, condConn2;	//conditional connection between ellipce and bot
		
		if(aX > Math.abs(currentX) && bY < Math.abs(currentY)){
			condConn1 = VectorUtils.getVectorFromTwoPoints(currentPosition, new Vector2(-aX, 0));
			condConn2 = VectorUtils.getVectorFromTwoPoints(currentPosition, new Vector2(aX, 0));
		} else if(aX < Math.abs(currentX) && bY > Math.abs(currentY)){
			condConn1 = VectorUtils.getVectorFromTwoPoints(currentPosition, new Vector2(0, -bY));
			condConn2 = VectorUtils.getVectorFromTwoPoints(currentPosition, new Vector2(0, bY));
		} else {
			condConn1 = VectorUtils.getVectorFromTwoPoints(currentPosition, new Vector2(0, Math.signum(currentY) * bY));
			condConn2 = VectorUtils.getVectorFromTwoPoints(currentPosition, new Vector2(Math.signum(currentX) * aX, 0));
		}
		
		Vector2 condSum = condConn1.add(condConn2);
		double condAngle = VectorUtils.angleBetweenTwoVectors(condConn1, condConn2);
		double condDirAngle = VectorUtils.angleBetweenTwoVectors(condSum, direction);
		if(condDirAngle > condAngle / 2){
			return Keys.W;
		} else return KEY_UNPRESSED;
	}
	
	private double temp = 0;

	private void resetPress() {
		currentKeyPressed = KEY_UNPRESSED;
		controoler.keyUp(Keys.W);
		controoler.keyUp(Keys.S);
	}
}
