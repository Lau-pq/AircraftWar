package edu.hitsz.factory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.StraightShootStrategy;

public class EliteEnemyFactory implements AircraftFactory {
    @Override
    public EnemyAircraft createAircraft(int hp, int shootTime) {
        EnemyAircraft eliteEnemy = new EliteEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int) (Math.random() * 11) - 5, 7, hp);
        eliteEnemy.setShootStrategy(new StraightShootStrategy());
        eliteEnemy.setShootTime(shootTime);
        return eliteEnemy;
    }
}
