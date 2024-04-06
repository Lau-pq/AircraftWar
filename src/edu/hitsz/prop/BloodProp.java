package edu.hitsz.prop;


import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;

/**
 * Blood
 * recover 30 HP
 *
 * @author Lpq
 */
public class BloodProp extends AbstractProp {
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void bloodSupply(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(30);
        System.out.println("BloodSupply active!");
    }
}
