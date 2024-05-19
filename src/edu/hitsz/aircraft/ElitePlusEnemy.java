package edu.hitsz.aircraft;

import edu.hitsz.application.Main;

public class ElitePlusEnemy extends EnemyAircraft {

    public ElitePlusEnemy(int locationX, int locationY, double speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 3;
        this.power = 30;
        this.direction = 1;
        this.shootTime = 1200;
        this.score = 50;
        this.propNum = 1;
    }

    @Override
    public void update() {
        this.decreaseHp(this.getHp() - 1);
    }
}
