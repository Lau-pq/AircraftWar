package edu.hitsz.factory;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.RingShootStrategy;

public class BossEnemyFactory implements AircraftFactory {
    @Override
    public EnemyAircraft createAircraft(int hp, int shootTime) {
        EnemyAircraft bossEnemy = new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Main.WINDOW_HEIGHT * 0.2),
                5, 0, hp);
        bossEnemy.setShootStrategy(new RingShootStrategy());
        bossEnemy.setShootTime(shootTime);
        return bossEnemy;
    }
}
