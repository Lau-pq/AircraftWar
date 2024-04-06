package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void fireSupply(HeroAircraft heroAircraft) {
        heroAircraft.increaseShootNum(1);
        System.out.println("FireSupply active!");
    }

}

