package edu.hitsz.prop;

import edu.hitsz.strategy.RingShootStrategy;

public class RingBulletProp extends BulletProp {

    public RingBulletProp(int locationX, int locationY, double speedX, double speedY) {
        super(locationX, locationY, speedX, speedY);
        this.shootNum = 20;
        this.shootStrategy = new RingShootStrategy();
        this.activeTime = 5000;
    }

}