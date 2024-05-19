package edu.hitsz.Game;

import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.ElitePlusEnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;


public class EasyGame extends Game {
    public EasyGame() {
        super();
        this.background = ImageManager.BACKGROUND_IMAGE1;
        this.level = 1;
        this.enemyMaxNumber = 3;
    }

    @Override
    protected void generateBoss() {}

    @Override
    protected void updateAction() {}

    @Override
    protected void printDifficulty() {}
}
