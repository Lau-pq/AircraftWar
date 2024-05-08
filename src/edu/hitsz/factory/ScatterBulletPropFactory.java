package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.ScatterBulletProp;

public class ScatterBulletPropFactory implements PropFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY) {
        return new ScatterBulletProp(locationX, locationY, 0, 5);
    }
}
