package com.bw.actors.plane.types;

import com.badlogic.gdx.math.Vector2;

public class PlaneType {
	protected float speed;
	protected float motorSpeed;
	protected float controllForce;
	protected Vector2 sise;
	protected Vector2 position;
	protected float frontWheelRadius, backWheelRadius;
	protected float density;
	protected float friction;
	protected float restitution;
	protected float frequencyHz;
	protected float reloadTime;

	public float getReloadTime() {
		return reloadTime;
	}

	public float getSpeed() {
		return speed;
	}

	public float getMotorSpeed() {
		return motorSpeed;
	}

	public float getControllForce() {
		return controllForce;
	}

	public Vector2 getSise() {
		return sise;
	}

	public Vector2 getPosition() {
		return position;
	}

	public float getFrontWheelRadius() {
		return frontWheelRadius;
	}

	public float getBackWheelRadius() {
		return backWheelRadius;
	}

	public float getDensity() {
		return density;
	}

	public float getFriction() {
		return friction;
	}

	public float getRestitution() {
		return restitution;
	}

	public float getFrequencyHz() {
		return frequencyHz;
	}
}
