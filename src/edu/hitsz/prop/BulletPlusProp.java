package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.strategy.RingShootStrategy;

public class BulletPlusProp extends AbstractProp {

    public BulletPlusProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.setShootNum(20);
        heroAircraft.setShootStrategy(new RingShootStrategy());
        System.out.println("FireSupply active!");
    }

}