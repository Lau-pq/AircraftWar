package edu.hitsz.prop;


import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;

/**
 * Blood
 * recover 30 HP
 *
 * @author Lpq
 */
public class BloodProp extends AbstractProp {
    private int recoverHp = 30;

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(this.recoverHp);
        System.out.println("BloodSupply active!");
        MusicManager.action("supply");
    }
}
