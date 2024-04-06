package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;

public class BombProp extends AbstractProp {
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void func(AbstractAircraft aircraft) {
        aircraft.vanish();
    }

    @Override
    public void print_func() {
        System.out.println("BombSupply active!");
    }
}

