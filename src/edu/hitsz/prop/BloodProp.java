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
    private int recoverHp = 0;

    public BloodProp(int locationX, int locationY, double speedX, double speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        recoverHp = (int)(heroAircraft.getLoseHp() * 0.2); // 已损生命值 20%
        heroAircraft.increaseHp(this.recoverHp);
        System.out.println("BloodSupply active!");
        MusicManager.action("supply");
    }
}
