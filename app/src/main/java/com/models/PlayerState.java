package com.models;

public class PlayerState {
    private PlayerStateType type;
    private double duration;

    public PlayerState(PlayerStateType type) {
        this.type = type;
        this.duration = 0;
    }

    public void update(double deltaTime) {
        duration += deltaTime;
    }

    public boolean isFinished() {
        return duration >= getMaxDuration();
    }

    private double getMaxDuration() {
        switch (type) {
            case ATTACKING:
                return 0.5; // 0.5 seconds for attack animation
            default:
                return Double.MAX_VALUE; // Infinite duration for other states
        }
    }

    public PlayerStateType getType() {
        return type;
    }
}

