package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.strategy.StraightShootStrategy;

public abstract class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    protected int shootNum;
    protected ShootStrategy shootStrategy;
    protected int activeTime;

    private static int bulletPropNum = 0;

    private static synchronized void changeBulletProp(String event) {
        switch (event) {
            case "increase" -> bulletPropNum += 1;
            case "decrease" -> bulletPropNum -= 1;
            default -> {}
        }
    }

    public void recoverStraightShoot (HeroAircraft heroAircraft) {
        // 恢复单射
        Runnable r = () -> {
            try {
                Thread.sleep(activeTime);
                if (bulletPropNum == 1) {
                    heroAircraft.setShootNum(1);
                    heroAircraft.setShootStrategy(new StraightShootStrategy());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                changeBulletProp("decrease");
            }
        };
        new Thread(r).start();
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        changeBulletProp("increase");
        heroAircraft.setShootNum(shootNum);
        heroAircraft.setShootStrategy(shootStrategy);
        recoverStraightShoot(heroAircraft);
        System.out.println("FireSupply active!");
        MusicManager.action("supply");
    }

}
