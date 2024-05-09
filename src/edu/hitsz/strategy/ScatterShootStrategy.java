package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterShootStrategy implements ShootStrategy{

    /**
     * 散射策略射击
     */
    @Override
    public List<BaseBullet> shootAction(AbstractAircraft abstractAircraft) {
        int shootNum = abstractAircraft.getShootNum();
        int power = abstractAircraft.getPower();
        int direction = abstractAircraft.getDirection();
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + direction*2;
        double speedx = 4;
        double speedy = abstractAircraft.getSpeedY() + direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            if (abstractAircraft instanceof HeroAircraft) {
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedx * (i - 1), speedy, power);
            } else {
                bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedx * (i - 1), speedy, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
