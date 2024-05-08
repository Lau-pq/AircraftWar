package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

public abstract class EnemyAircraft extends AbstractAircraft{
    // 得分
    protected int score;

    // 道具生成数量
    protected int propNum;

    public EnemyAircraft(int locationX, int locationY, double speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    public int getScore() {
        return score;
    }

    public List<AbstractProp> dropProp() {
        List<AbstractProp> props = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY();
        PropFactory propFactory = null;
        for (int i = 0; i < propNum; i++) {
            if (Math.random() <= 0.2) {
                propFactory = new BloodPropFactory();
            } else if (Math.random() <= 0.4) {
                propFactory = new BombPropFactory();
            } else if (Math.random() <= 0.65) {
                propFactory = new ScatterBulletPropFactory();
            } else if (Math.random() <= 0.9) {
                propFactory = new RingBulletPropFactory();
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
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }
}
