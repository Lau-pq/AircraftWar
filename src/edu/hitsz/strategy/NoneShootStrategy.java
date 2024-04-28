package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class NoneShootStrategy implements ShootStrategy{
    @Override
    public List<BaseBullet> shootAction(AbstractAircraft abstractAircraft) {
        return new LinkedList<>();
    }
}
