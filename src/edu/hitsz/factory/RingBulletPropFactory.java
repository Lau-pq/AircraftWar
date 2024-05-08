package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.RingBulletProp;

public class RingBulletPropFactory implements PropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new RingBulletProp(locationX, locationY, 0, 5);
    }
}
