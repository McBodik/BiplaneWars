package com.bw.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class VectorUtils {
	//instances for better performance
	private Vector2 forceVector = new Vector2();
	private Vector2 speedVector = new Vector2();
	
	public Vector2 getDirectionUnitVector(float angle) {
		float x = MathUtils.cos(angle);
		float y = MathUtils.sin(angle);
		return speedVector.set(x, y);
	}

	public Vector2 getTopDirectionUnitVector(float angle) {
		float rot = (float) (angle + Math.PI / 2);
		float x = MathUtils.cos(rot);
		float y = MathUtils.sin(rot);
		return forceVector.set(x, y);
	}
	
	/**
	 * sp - scalar product 
	 */
	public static int SP_SAME_DIRECTION = 1;
	public static int SP_PERPENDICULAR_DIRECTION = 0;
	public static int SP_REVERS_DIRECTION = -1;
}
