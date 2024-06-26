package edu.hitsz.factory;

import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.ScatterShootStrategy;

public class ElitePlusEnemyFactory implements AircraftFactory {
    @Override
    public EnemyAircraft createAircraft(int hp, int shootTime) {
        EnemyAircraft elitePlusEnemy = new ElitePlusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int) (Math.random() * 11) - 5, 7, hp);
        elitePlusEnemy.setShootStrategy(new ScatterShootStrategy());
        elitePlusEnemy.setShootTime(shootTime);
        return elitePlusEnemy;
    }
}
