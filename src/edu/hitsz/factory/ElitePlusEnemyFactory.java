package edu.hitsz.factory;

import edu.hitsz.aircraft.ElitePlusEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

public class ElitePlusEnemyFactory implements AircraftFactory{
    @Override
    public EnemyAircraft createAircraft() {
        return new ElitePlusEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(Math.random()*11) -5, 7, 30);
    }
}
