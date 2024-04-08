package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory implements PropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new BulletProp(locationX, locationY, 0, 5);
    }
}
