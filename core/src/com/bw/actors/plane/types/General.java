package com.bw.actors.plane.types;

import com.badlogic.gdx.math.Vector2;

public class General extends PlaneType {

	public General() {
		speed = 5;
		motorSpeed = 1000;
		controllForce = 300;
		sise = new Vector2(6, 1.4f);
		position = new Vector2(0, 0);
		frontWheelRadius = 0.25f;
		backWheelRadius = 0.15f;
		density = 20f;
		friction = 0.5f;
		restitution = 0;
		frequencyHz = 15;
		reloadTime = 3;
	}
}
