package com.bw.bot;

import java.util.Date;
import java.util.Random;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.bw.actors.plane.PlaneController;
import com.bw.utils.VectorUtils;

public class BotSimple extends Bot {

	private PlaneController controoler;
	private boolean isFlying = false;
	private int currentKeyPressed = KEY_UNPRESSED;
	private VectorUtils vectorUtils;
	private Date nextActionChange;

	// цей бот літає по еліпсу
	private float aX; // велика піввісь еліпса
	private float bY; // маленька піввісь еліпса

	public BotSimple(PlaneController planeControoler) {
		super();
		controoler = planeControoler;

		controoler.keyDown(Keys.W);
		aX = rightWall - 15;
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
		if(Math.abs(currentPosition.x) > aX || Math.abs(currentPosition.y) > bY){
			return outOfEllipse(currentPosition);
		}
		return inEllipse();
	}
	
	private int inEllipse(){
		int key = currentKeyPressed;
		if(nextActionChange == null){
			nextActionChange = new Date();
		}
		if ((new Date()).getTime() >= nextActionChange.getTime()){
			nextActionChange = new Date(new Date().getTime() + (new Random().nextInt(4)) * 1000);
			System.out.println(nextActionChange);
			switch (new Random().nextInt(2)) {
			case 0:
				key = KEY_UNPRESSED;
				break;
			case 1:
				key = KEY_DOWN;
				break;
			case 2:
				key = KEY_UP;
				break;
			}
			
		}
		return key;
	}
	
	private int outOfEllipse(Vector2 currentPosition) {
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
		
		Vector2 condSum = new Vector2(condConn1);
		condSum.add(condConn2);
		double condAngle = VectorUtils.angleBetweenTwoVectors(condConn1, condConn2);
		double condDirAngle = VectorUtils.angleBetweenTwoVectors(condSum, direction);
		if(condDirAngle > condAngle / 2){
			Vector2 directionNormal = vectorUtils.getTopDirectionUnitVector(controoler.getCurrentAngle());
			double coefDirection = directionNormal.dot(condSum);	//1 - same direction, -1 opposite, 0 - perpendicular
			if(coefDirection < 0) {
				return KEY_DOWN;
			} else if (coefDirection > 0){
				return KEY_UP;
			} else return new Random().nextBoolean() ? KEY_UP : KEY_DOWN; 
		} else return KEY_UNPRESSED;
	}

	private void resetPress() {
		currentKeyPressed = KEY_UNPRESSED;
		controoler.keyUp(Keys.W);
		controoler.keyUp(Keys.S);
	}
}
