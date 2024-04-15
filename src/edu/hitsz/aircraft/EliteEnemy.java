package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.BulletPropFactory;
import edu.hitsz.factory.PropFactory;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends EnemyAircraft{
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    // 子弹发射频率
    private int shootTime = 800;

    // 得分
    private int score = 30;

    // 道具生成数量
    private int propNum = 1;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction*5;
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public List<AbstractProp> dropProp() {
        List<AbstractProp> props = new LinkedList<AbstractProp>();
        int x = this.getLocationX();
        int y = this.getLocationY();
        PropFactory propFactory = null;
        for (int i = 0; i < propNum; i++) {
            if (Math.random() <= 0.3) {
                propFactory = new BloodPropFactory();
            } else if (Math.random() <= 0.6) {
                propFactory = new BombPropFactory();
            } else if (Math.random() <= 0.9) {
                propFactory =new BulletPropFactory();
            }
            if (propFactory != null) {
                props.add(propFactory.createProp(
                        x + (i*2 - propNum + 1)*30,
                        y));
            }
        }
        return props;
    }

    @Override
    public int getShootTime() {
        return shootTime;
    }
}
