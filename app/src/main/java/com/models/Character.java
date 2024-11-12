package com.models;
import com.models.Ability;
import java.util.*;
import java.lang.String;

public class Character {
    private final int id;
    private final String name;
    private final double ap;        // Attack power
    private double hp;        // Health points
    private final double attackCooldown;      // Attack speed
    private final double range;     // Attack range
    private final double speed;     // Movement speed
    private final Ability ability;

    public static class Builder {
        private int id;
        private String name;
        private double ap, hp, speed, attackCooldown, range;
        private Ability ability;
        public Builder(int id, String name, double ap, double hp, double speed, double attackCooldown, double range){
            this.id = id;
            this.name = name;
            this.ap = ap;
            this.hp = hp;
            this.speed = speed;
            this.attackCooldown = attackCooldown;
            this.range = range;
        }
        public Builder(String name, double ap, double hp, double speed){
            this.id = 1;
            this.name = name;
            this.ap = ap;
            this.speed = speed;
            this.attackCooldown = 1.5;
            this.range = 30;
        }
        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAp(double ap) {
            this.ap = ap;
            return this;
        }
        public Builder setAttackCD(double attackCooldown){
            this.attackCooldown = attackCooldown;
            return this;
        }
        public Builder setRange(double range){
            this.range = range;
            return this;
        }
        public Builder setHp(double hp) {
            this.hp = hp;
            return this;
        }

        public Builder setSpeed(double speed) {
            this.speed = speed;
            return this;
        }
        public Builder setAbility(Ability ability){
            this.ability = ability;
            return this;
        }
        public Character build() {
            return new Character(this);
        }
    }

    public Character(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.ap = builder.ap;
        this.hp = builder.hp;
        this.speed = builder.speed;
        this.attackCooldown = builder.attackCooldown;
        this.range = builder.range;
        this.ability = builder.ability;
    }

    // Combat methods
    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(double damage) {
        this.hp = Math.max(0, this.hp - damage);
    }

    public double calculateAttackDamage() {
        return ap;
    }

    public boolean isInRange(Vector2D targetPosition, Vector2D currentPosition) {
        return currentPosition.distance(targetPosition) <= range;
    }
    public void useAbility(Character target) {
        if (ability.isReady()) {
            double damage = ability.use(this);
            target.takeDamage(damage);
        }
    }
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getAp() { return ap; }
    public double getHp() { return hp; }
    public double getAttackCooldown() { return attackCooldown; }
    public double getRange() { return range; }
    public double getSpeed() { return speed; }
    public Ability getAbility() {
        return ability;
    }
}