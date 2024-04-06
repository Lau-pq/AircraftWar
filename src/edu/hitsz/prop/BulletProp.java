package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func(AbstractAircraft aircraft) {
    }

    @Override
    public void print_func() {
        System.out.println("FireSupply active!");
    }
}

