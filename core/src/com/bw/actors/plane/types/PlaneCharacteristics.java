package com.bw.actors.plane.types;

import com.badlogic.gdx.math.Vector2;

public class PlaneCharacteristics {
    protected float speed;
    protected float motorSpeed;
    protected float controlForce;
    protected Vector2 size;
    protected Vector2 position;
    protected float frontWheelRadius, backWheelRadius;
    protected float density;
    protected float friction;
    protected float restitution;
    protected float frequencyHz;
    protected float reloadTime;

    private PlaneCharacteristics() {

    }

    public static CharacteristicsBuilder characteristics() {

        return new CharacteristicsBuilder();
    }

    public static CharacteristicsBuilder characteristics(PlaneCharacteristics template) {

        CharacteristicsBuilder builder = null;
        try {
            builder = new CharacteristicsBuilder(template);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return builder;
    }

    public static class CharacteristicsBuilder {

        private PlaneCharacteristics instance = new PlaneCharacteristics();

        private CharacteristicsBuilder() {

        }

        private CharacteristicsBuilder(PlaneCharacteristics planeCharacteristics) throws CloneNotSupportedException {

            instance= (PlaneCharacteristics) planeCharacteristics.clone();

        }

        public CharacteristicsBuilder speed(float speed) {
            instance.speed = speed;
            return this;
        }

        public CharacteristicsBuilder motorSpeed(float motorSpeed) {
            instance.motorSpeed = motorSpeed;
            return this;
        }

        public CharacteristicsBuilder controlForce(float controlForce) {
            instance.controlForce = controlForce;
            return this;
        }

        public CharacteristicsBuilder position(Vector2 position) {
            instance.position = position;
            return this;
        }

        public CharacteristicsBuilder size(Vector2 size) {
            instance.size = size;
            return this;
        }

        public CharacteristicsBuilder frontWheelRadius(float frontWheelRadius) {
            instance.frontWheelRadius = frontWheelRadius;
            return this;
        }

        public CharacteristicsBuilder backWheelRadius(float backWheelRadius) {
            instance.backWheelRadius = backWheelRadius;
            return this;
        }

        public CharacteristicsBuilder density(float density) {
            instance.density = density;
            return this;
        }

        public CharacteristicsBuilder friction(float friction) {
            instance.friction = friction;
            return this;
        }

        public CharacteristicsBuilder restitution(float restitution) {
            instance.restitution = restitution;
            return this;
        }

        public CharacteristicsBuilder frequencyHz(float frequencyHz) {
            instance.frequencyHz = frequencyHz;
            return this;
        }
        public CharacteristicsBuilder reloadTime(float reloadTime) {
            instance.reloadTime = reloadTime;
            return this;
        }

        public PlaneCharacteristics build() {
            return instance;
        }


    }


    public float getSpeed() {
        return speed;
    }


    public float getMotorSpeed() {
        return motorSpeed;
    }


    public float getControlForce() {
        return controlForce;
    }


    public Vector2 getSize() {
        return size;
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

    public float getReloadTime() {
        return reloadTime;
    }

}
