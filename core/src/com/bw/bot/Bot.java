package com.bw.bot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Bot {

	protected short obstacles = 0;

	protected float leftWall = -Gdx.graphics.getWidth() / 20f;
	protected float topWall = Gdx.graphics.getHeight() / 20f - 5;
	protected float rightWall = Gdx.graphics.getWidth() / 20f;
	protected float bottomWall = -23;

	/**
	 * @param botPosition current bot position
	 * @return is any obstacles nearest to bot
	 */
	public boolean updateNearestObstacles(Vector2 botPosition) {
		obstacles = 0x0;
		if ((leftWall - botPosition.x) >= -10) {
			obstacles += LEFT;
		} else if ((rightWall - botPosition.x) <= 10) {
			obstacles += RIGHT;
		}
		if ((topWall - botPosition.y) <= 10) {
			obstacles += TOP;
		} else if ((bottomWall - botPosition.y) >= -10) {
			obstacles += BOTTOM;
		}
		return obstacles == 0x0 ? false : true;
	}

	/**
	 * TODO more intelligent way to avoid obstacles :) 
	 * @return way: false - down, true - up
	 */
	public int choiceWayToAvoidObstacles(float rotationAngle) {
		int key = KEY_UNPRESSED;
		float cos = MathUtils.cos(rotationAngle);
		float sin = MathUtils.sin(rotationAngle);
		switch (obstacles) {

		case LEFT:
			if (sin > 0) {
				key = KEY_DOWN;
			} else {
				key = KEY_UP;
			}
			break;
		case RIGHT:
//			if (Math.abs(cos) > 0.9) {
//				key = new Random().nextBoolean() ? KEY_UP : KEY_DOWN;
//			} else 
				if (sin > 0) {
				key = KEY_UP;
			} else {
				key = KEY_DOWN;
			}
			break;

		case TOP:
//			if (sin > 0.9) {
//				key = new Random().nextBoolean() ? KEY_UP : KEY_DOWN;
//			} else 
				if (cos > 0) {
				key = KEY_DOWN;
			} else {
				key = KEY_UP;
			}
			break;

		case BOTTOM:
//			if (sin < -0.9) {
//				key = new Random().nextBoolean() ? KEY_UP : KEY_DOWN;
//			} else 
				if (cos > 0) {
				key = KEY_UP;
			} else {
				key = KEY_DOWN;
			}
			break;

		case LEFT + TOP:
		case LEFT + BOTTOM:
		case RIGHT + TOP:
		case RIGHT + BOTTOM:
			//eject (move it to default)
			break;

		default:
			key = new Random().nextBoolean() ? KEY_UP : KEY_DOWN;
			break;
		}
		return key;
	}

	public final static short LEFT = 0x1;
	public final static short TOP = 0x2;
	public final static short RIGHT = 0x4;
	public final static short BOTTOM = 0x8;
	
	public final static int KEY_UNPRESSED = -1;
	public final static int KEY_UP = Keys.W;
	public final static int KEY_DOWN = Keys.S;
	public final static int KEY_SHOOT = Keys.SPACE;
}
