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

    @Override
    public void func(AbstractAircraft aircraft) {
        aircraft.increaseHp(30);
    }

    @Override
    public void print_func() {
        System.out.println("BloodSupply active!");
    }
}
