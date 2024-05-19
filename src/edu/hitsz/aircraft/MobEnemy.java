package edu.hitsz.aircraft;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends EnemyAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.score = 10;
        this.shootNum = 0;
        this.shootTime = 600;
    }

    @Override
    public void update() {
        setIsNaturalDeath(false);
        this.vanish();
    }
}
