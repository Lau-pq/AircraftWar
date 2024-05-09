package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;

public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    private static Thread currentThread;
    protected int shootNum;
    protected ShootStrategy shootStrategy;
    protected int activeTime;

    public void recoverStraightShoot (HeroAircraft heroAircraft) {
        // 恢复单射
        Runnable r = () -> {
            try {
                Thread.sleep(activeTime);
                heroAircraft.setShootNum(1);
                heroAircraft.setShootStrategy(new StraightShootStrategy());
            } catch (InterruptedException ignored) {
            }
        };
        if (currentThread != null) {
            currentThread.interrupt();
        }
        currentThread = new Thread(r);
        currentThread.start();
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        heroAircraft.setShootNum(shootNum);
        heroAircraft.setShootStrategy(shootStrategy);
        recoverStraightShoot(heroAircraft);
        System.out.println("FireSupply active!");
        MusicManager.action("supply");
    }


}
