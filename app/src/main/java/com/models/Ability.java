package com.models;

public class Ability {
    private final int id;
    private final double multiplier;
    private double cooldown;
    private double currentCooldown;

    public Ability(int id, double multiplier, double cooldown) {
        this.id = id;
        this.multiplier = multiplier;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }

    public boolean isReady() {
        return currentCooldown <= 0;
    }

    public void updateCooldown(double deltaTime) {
        currentCooldown = Math.max(0, currentCooldown - deltaTime);
    }

    public double use(Character character) {
        if (!isReady()) {
            return 0;
        }
        currentCooldown = cooldown;
        return character.getAp() * multiplier;
    }

    // Getters
    public int getId() { return id; }
    public double getMultiplier() { return multiplier; }
    public double getCooldown() { return cooldown; }
}
