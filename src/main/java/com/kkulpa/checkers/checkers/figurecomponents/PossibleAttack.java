package com.kkulpa.checkers.checkers.figurecomponents;

public class PossibleAttack {

    private Figure attacker;
    private Figure enemy;
    private Coordinates afterAttackCoordinates;

    public PossibleAttack(Figure attacker, Figure enemy, Coordinates afterAttackCoordinates) {
        this.attacker = attacker;
        this.enemy = enemy;
        this.afterAttackCoordinates = afterAttackCoordinates;
    }

    public Figure getEnemy() {
        return enemy;
    }

    public Figure getAttacker() {
        return attacker;
    }

    @Override
    public String toString() {
        return "PossibleAttack{" +
                "attacker=" + attacker.getId() +
                ", enemy=" + enemy.getId() +
                ", afterAttackCoordinates=" + afterAttackCoordinates +
                '}';
    }

    public Coordinates getAfterAttackCoordinates() {
        return afterAttackCoordinates;
    }
}
