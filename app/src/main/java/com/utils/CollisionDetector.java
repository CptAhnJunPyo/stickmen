package com.utils;
import com.models.Vector2D;
import com.models.Player;
public class CollisionDetector {
    public static boolean checkAttackCollisions(Player attacker, Player defender) {
        Vector2D attackerPos = attacker.getPosition();
        Vector2D defenderPos = defender.getPosition();

        double distance = attackerPos.distance(defenderPos);
        return distance <= Constants.ATTACK_RANGE;
    }
}
