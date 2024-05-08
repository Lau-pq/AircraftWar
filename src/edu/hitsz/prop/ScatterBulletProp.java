package edu.hitsz.prop;

import edu.hitsz.strategy.ScatterShootStrategy;

public class ScatterBulletProp extends BulletProp {

    public ScatterBulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.shootNum = 3;
        this.shootStrategy = new ScatterShootStrategy();
    }

}

