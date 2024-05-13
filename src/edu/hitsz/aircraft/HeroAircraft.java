package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.StraightShootStrategy;
import org.apache.commons.lang3.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * 采用单例模式实现 双重检查锁定
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    private volatile static HeroAircraft heroAircraft;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.power = 30;
        this.direction = -1;
        this.shootTime = 600;
    }

    public static HeroAircraft getHeroAircraft() {
        if (heroAircraft== null) {
            synchronized (HeroAircraft.class) {
                if (heroAircraft == null) {
                    heroAircraft = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000);
                }
            }
        }
        heroAircraft.setShootStrategy(new StraightShootStrategy());
        return heroAircraft;
    }

    public void increaseHp(int increase) {
        hp += increase;
        if (hp >= maxHp) {
            hp = maxHp;
        }
    }

    public int getLoseHp() {
        return maxHp - hp;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public void reset() {
        heroAircraft = null;
    }

}
