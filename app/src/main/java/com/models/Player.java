package com.models;

import static com.utils.Constants.ATTACK_COOLDOWN;

public class Player {
    private final int id;
    private final Character character;
    private Vector2D position;
    private PlayerState state;
    private boolean facingRight;
    private double verticalVelocity;
    private boolean isJumping;
    private static final double GRAVITY = 0.8;
    private static final double JUMP_FORCE = -20.0;
    private static final double ATTACK_COOLDOWN = 0.5;
    private double attackCooldown = 0;

    public Player(int id, Character character, double x, double y) {
        this.id = id;
        this.character = character;
        this.position = new Vector2D(x, y);
        this.state = new PlayerState(PlayerStateType.IDLE);
        this.facingRight = true;
        this.verticalVelocity = 0;
        this.isJumping = false;
    }

    public void update(double deltaTime) {
        // Update attack cooldown
        if (attackCooldown > 0) {
            attackCooldown = Math.max(0, attackCooldown - deltaTime);
        }

        // Update ability cooldown
        character.getAbility().updateCooldown(deltaTime);

        // Update player state
        state.update(deltaTime);
        if (state.isFinished()) {
            if (state.getType() == PlayerStateType.ATTACKING) {
                state = new PlayerState(PlayerStateType.IDLE);
            }
        }

        // Update position with gravity
        if (isJumping) {
            verticalVelocity += GRAVITY * deltaTime;
            position = position.add(new Vector2D(0, verticalVelocity));

            // Check ground collision
            if (position.y >= 500) {  // Ground level
                position = new Vector2D(position.x, 500);
                verticalVelocity = 0;
                isJumping = false;
            }
        }
    }

    public boolean canAttack(Player target) {
        if (attackCooldown > 0) return false;
        double distance = position.distance(target.getPosition());
        return distance <= character.getRange();
    }

    public void attack(Player target) {
        if (!canAttack(target)) return;

        state = new PlayerState(PlayerStateType.ATTACKING);
        attackCooldown = ATTACK_COOLDOWN;

        character.useAbility(target.getCharacter());
    }
    public void jump() {
        if (!isJumping) {
            verticalVelocity = JUMP_FORCE;
            isJumping = true;
            state = new PlayerState(PlayerStateType.MOVING);
        }
    }

    public void move(double dx, double dy) {
        position = position.add(new Vector2D(dx * character.getSpeed(), dy));
        facingRight = dx > 0;
        state = new PlayerState(PlayerStateType.MOVING);
    }

    public void keepInBounds(double minX, double maxX) {
        double x = Math.min(Math.max(position.x, minX + 25), maxX - 25);
        position = new Vector2D(x, position.y);
    }
    // Getters
    public Vector2D getPosition() { return position; }
    public PlayerState getState() { return state; }
    public boolean getFacingRight() { return facingRight; }
    public Character getCharacter() { return character; }
}
