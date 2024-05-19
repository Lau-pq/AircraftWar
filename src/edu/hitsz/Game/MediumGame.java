package edu.hitsz.Game;

import edu.hitsz.application.ImageManager;
import edu.hitsz.factory.BossEnemyFactory;

public class MediumGame extends Game {
    public MediumGame() {
        super();
        this.background = ImageManager.BACKGROUND_IMAGE2;
        this.level = 2;
        this.updateCycleDuration = 10000; // 10s
        this.enemyMaxNumber = 5;
        this.elitePlusEnemyShootTime = 800;
        this.bossEnemyShootTime = 1200;
    }

    @Override
    protected void generateBoss() {
        enemyAircrafts.add(new BossEnemyFactory().createAircraft(bossEnemyHP, bossEnemyShootTime));
    }


    @Override
    protected void updateAction() {
        mobEnemyHP = updateParam(mobEnemyHP, 90, 10);
        eliteEnemyHP = updateParam(eliteEnemyHP, 120, 10);
        elitePlusEnemyHP = updateParam(elitePlusEnemyHP, 120, 10);
        eliteProbability = updateParam(eliteProbability, 0.4, 0.02);
        enemyCycleDuration = updateParam(enemyCycleDuration, 560, -40);
        printDifficulty();
    }

    @Override
    protected void printDifficulty() {
        System.out.println("*****************提高难度*******************");
        System.out.printf("血量: 普通机:%d, 精英机: %d, 超级精英机: %d\n", mobEnemyHP, eliteEnemyHP, elitePlusEnemyHP);
        System.out.printf("精英机与超级精英机出现概率: %.2f\n", eliteProbability);
        System.out.printf("敌机出现周期: %d\n", enemyCycleDuration);
        System.out.println("******************************************");
    }
}
