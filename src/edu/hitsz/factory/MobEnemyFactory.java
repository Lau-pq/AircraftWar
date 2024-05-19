package edu.hitsz.factory;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.NoneShootStrategy;

public class MobEnemyFactory implements AircraftFactory {
    @Override
    public EnemyAircraft createAircraft(int hp, int shootTime) {
        EnemyAircraft mobEnemy = new MobEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0, 10, hp);
        mobEnemy.setShootStrategy(new NoneShootStrategy());
        return mobEnemy;
    }
}
