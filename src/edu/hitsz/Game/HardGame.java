package edu.hitsz.Game;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.MusicManager;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.ElitePlusEnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;

public class HardGame extends Game {
    public HardGame() {
        super();
        this.background = ImageManager.BACKGROUND_IMAGE3;
        this.level = 3;
        this.updateCycleDuration = 5000; // 5s
        this.enemyMaxNumber = 7;
        this.elitePlusEnemyShootTime = 800;
        this.bossEnemyShootTime = 800;
    }

    private int bossNum = 0;

    @Override
    protected void generateBoss() {
        bossNum += 1;
        bossEnemyHP += 100;
        EnemyAircraft bossEnemy = new BossEnemyFactory().createAircraft(bossEnemyHP, bossEnemyShootTime);
        enemyAircrafts.add(bossEnemy);
        System.out.printf("第%d只 Boss, Boss 血量: %d\n", bossNum, bossEnemyHP);
    }

    @Override
    protected void updateAction() {
        mobEnemyHP = updateParam(mobEnemyHP, 90, 10);
        eliteEnemyHP = updateParam(eliteEnemyHP, 120, 10);
        elitePlusEnemyHP = updateParam(elitePlusEnemyHP, 120, 10);
        eliteProbability = updateParam(eliteProbability, 0.4, 0.02);
        enemyCycleDuration = updateParam(enemyCycleDuration, 560, -40);
        heroShootTime = updateParam(heroShootTime, 400, -40);
        eliteEnemyShootTime= updateParam(eliteEnemyShootTime, 600, -40);
        elitePlusEnemyShootTime = updateParam(elitePlusEnemyShootTime, 600, -40);
        bossEnemyShootTime = updateParam(bossEnemyShootTime, 600, -40);
        printDifficulty();
  }

    @Override
    protected void printDifficulty() {
        System.out.println("*****************提高难度*******************");
        System.out.printf("血量: 普通机:%d, 精英机: %d, 超级精英机: %d\n", mobEnemyHP, eliteEnemyHP, elitePlusEnemyHP);
        System.out.printf("精英机与超级精英机出现概率: %.2f\n", eliteProbability);
        System.out.printf("敌机出现周期: %d\n", enemyCycleDuration);
        System.out.printf("射击周期: 英雄机: %d, 精英机: %d, 超级精英机: %d, boss机: %d\n",
                heroShootTime, eliteEnemyShootTime, elitePlusEnemyShootTime, bossEnemyShootTime);
        System.out.println("******************************************");
    }
}
