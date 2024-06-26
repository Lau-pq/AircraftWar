package edu.hitsz.aircraft;

import edu.hitsz.basic.FlyingsObserver;

public class BossEnemy extends EnemyAircraft implements FlyingsObserver {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 20;
        this.power = 30;
        this.direction = 1;
        this.shootTime = 1600;
        this.score = 100;
        this.propNum = 3;
    }

    @Override
    public void update() {}

}
