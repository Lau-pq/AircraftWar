package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.MusicManager;
import edu.hitsz.basic.FlyingsObserver;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends AbstractProp {
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    private List<FlyingsObserver> flyingsObserverList = new ArrayList<>();

    public void addflyingsObserver(FlyingsObserver flyingsObserver) {
        flyingsObserverList.add(flyingsObserver);
    }

    public void notifyAllFlyings() {
        for (FlyingsObserver flyingsObserver : flyingsObserverList) {
            flyingsObserver.update();
        }
    }

    @Override
    public void activate(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
        notifyAllFlyings();
        MusicManager.action("bomb");
    }
    
}

