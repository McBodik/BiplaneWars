package com.bw.actors.plane.types;

import com.badlogic.gdx.math.Vector2;

public abstract class PlaneType {

    public final static PlaneCharacteristics GENERAL = PlaneCharacteristics.characteristics()
            .speed(5)
            .motorSpeed(1000)
            .controlForce(300)
            .size(new Vector2(6, 1.4f))
            .position(new Vector2(0, 0))
            .frontWheelRadius(0.25f)
            .backWheelRadius(0.15f)
            .density(20f)
            .friction(0.5f)
            .restitution(0)
            .frequencyHz(15)
            .reloadTime(3)
            .build();

    //TODO check this out =)
    public final static PlaneCharacteristics I_CAN_COPY_AND_MODIFY_EXISTING_TYPES = PlaneCharacteristics.characteristics(GENERAL)
            .speed(100500)
            .restitution(0)
            .frequencyHz(100500)
            .build();

}
