package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EnemyAircraft;

import java.util.List;

public class BombProp extends AbstractProp {
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    
    public void bombSupply(List<EnemyAircraft> enemyAircrafts) {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.vanish();
        }
        System.out.println("BombSupply active!");
    }
    
}

