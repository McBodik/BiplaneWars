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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getMotorSpeed() {
		return motorSpeed;
	}

	public void setMotorSpeed(float motorSpeed) {
		this.motorSpeed = motorSpeed;
	}

	public float getControllForce() {
		return controllForce;
	}

	public void setControllForce(float controllForce) {
		this.controllForce = controllForce;
	}

	public Vector2 getSise() {
		return sise;
	}

	public void setSise(Vector2 sise) {
		this.sise = sise;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getFrontWheelRadius() {
		return frontWheelRadius;
	}

	public void setFrontWheelRadius(float frontWheelRadius) {
		this.frontWheelRadius = frontWheelRadius;
	}

	public float getBackWheelRadius() {
		return backWheelRadius;
	}

	public void setBackWheelRadius(float backWheelRadius) {
		this.backWheelRadius = backWheelRadius;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public float getFrequencyHz() {
		return frequencyHz;
	}

	public void setFrequencyHz(float frequencyHz) {
		this.frequencyHz = frequencyHz;
	}
}
