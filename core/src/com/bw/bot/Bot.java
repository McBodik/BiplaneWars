package com.bw.bot;

import com.badlogic.gdx.Gdx;
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
	public boolean choiceWayToAvoidObstacles(float rotationAngle) {
		boolean key = false;
		float cos = MathUtils.cos(rotationAngle);
		float sin = MathUtils.sin(rotationAngle);
		switch (obstacles) {

		case LEFT:
			if (cos < -0.9) {
				key = new Random().nextBoolean();
			} else if (sin > 0.9) {
				key = true;
			} else if (sin < -0.9) {
				key = false;
			}
			break;

		case RIGHT:
			if (cos > 0.9) {
				key = new Random().nextBoolean();
			} else if (sin > 0.9) {
				key = true;
			} else if (sin < -0.9) {
				key = false;
			}
			break;

		case TOP:
			if (sin == 1) {
				key = new Random().nextBoolean();
			} else if (cos > 0.1) {
				key = false;
			} else if (cos < -0.1) {
				key = true;
			}
			break;

		case BOTTOM:
			if (sin < -0.9) {
				key = new Random().nextBoolean();
			} else if (cos > 0.1) {
				key = true;
			} else if (cos < -0.1) {
				key = false;
			}
			break;

		case LEFT + TOP:

			break;

		case LEFT + BOTTOM:

			break;

		case RIGHT + TOP:

			break;

		case RIGHT + BOTTOM:

			break;

		default:
			key = new Random().nextBoolean();
			break;
		}
		return key;
	}

	public final static short LEFT = 0x1;
	public final static short TOP = 0x2;
	public final static short RIGHT = 0x4;
	public final static short BOTTOM = 0x8;
}
