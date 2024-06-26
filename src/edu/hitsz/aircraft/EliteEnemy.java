package edu.hitsz.aircraft;


public class EliteEnemy extends EnemyAircraft{
    public EliteEnemy(int locationX, int locationY, double speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootNum = 1;
        this.power = 30;
        this.direction = 1;
        this.shootTime = 800;
        this.score = 30;
        this.propNum = 1;
    }

    @Override
    public void update() {
        setIsNaturalDeath(false);
        this.vanish();
    }
}
