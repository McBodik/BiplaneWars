package com.bw.bot;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Bot {
		
	protected short obstacles = 0;

	protected float leftWall = -Gdx.graphics.getWidth() / 20f;
	protected float topWall = Gdx.graphics.getHeight() / 20f - 5;
	protected float rightWall = Gdx.graphics.getWidth() / 20f;
	protected float bottomWall = -23;

	public void updateNearestObstacles(Vector2 botPosition) {
		obstacles = 0x0;
		if ((leftWall - botPosition.x) >= -10) {
			obstacles += LEFT;
		} else if ((rightWall - botPosition.x) <= 10) {
			obstacles += RIGHT;
		}
		if ((topWall - botPosition.y) <= 10) {
			obstacles += TOP;
		} else if ((leftWall - botPosition.x) >= -10) {
			obstacles += BOTTOM;
		}
	}

	public final static short LEFT = 0x1;
	public final static short TOP = 0x2;
	public final static short RIGHT = 0x4;
	public final static short BOTTOM = 0x8;
}
