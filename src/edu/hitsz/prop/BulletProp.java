package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

public class BulletProp extends AbstractProp {
    private int shootNum = 1;

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.increaseShootNum(this.shootNum);
        System.out.println("FireSupply active!");
    }

}

