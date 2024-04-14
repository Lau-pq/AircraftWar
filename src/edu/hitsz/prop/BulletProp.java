package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

public class BulletProp extends AbstractProp {

    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
    }

}

