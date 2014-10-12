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
	 * vector direction from point1 to point2
	 */
	public static Vector2 getVectorFromTwoPoints(Vector2 point1, Vector2 point2){
		return new Vector2(point2.x - point1.x, point2.y - point1.y);
	}
	
	public static double angleBetweenTwoVectors(Vector2 vector1, Vector2 vector2){
		return Math.acos(vector1.dot(vector2)/(vector1.len() * vector2.len()));
	}
	
	/**
	 * sp - scalar product 
	 */
	public static int SP_SAME_DIRECTION = 1;
	public static int SP_PERPENDICULAR_DIRECTION = 0;
	public static int SP_REVERS_DIRECTION = -1;
}
