package com.example.shooter;

public class ThrowValues {
    public float firstSpeed;
    public float angle;
    public boolean isCollisionCharacter;
    public boolean isCollisionBuilding;

    public ThrowValues() {

    }

    public ThrowValues(float firstSpeed, float angle, boolean isCollisionCharacter, boolean isCollisionBuilding) {
        this.firstSpeed = firstSpeed;
        this.angle = angle;
        this.isCollisionCharacter = isCollisionCharacter;
        this.isCollisionBuilding = isCollisionBuilding;
    }
}
