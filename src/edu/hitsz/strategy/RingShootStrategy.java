package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class RingShootStrategy implements ShootStrategy{

    /**
     * 环射策略射击
     */
    @Override
    public List<BaseBullet> shootAction(AbstractAircraft abstractAircraft) {
        int shootNum = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        int direction = abstractAircraft.getDirection();
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + direction*2;
        double speed = 10;
        double r = Main.WINDOW_WIDTH * 0.2;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            double theta = 2 * Math.PI * i / shootNum;
            if (abstractAircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(
                        (int) (x + r * Math.cos(theta)),
                        (int) (y + r * Math.sin(theta)),
                        (int) (speed * Math.cos(theta)),
                        (int) (speed * Math.sin(theta)),
                        power);
            } else {
                bullet = new EnemyBullet(
                        (int) (x + r * Math.cos(theta)),
                        (int) (y + r * Math.sin(theta)),
                        (int) (speed * Math.cos(theta)),
                        (int) (speed * Math.sin(theta)),
                        power);
            }

            res.add(bullet);
        }
        return res;
    }
}
