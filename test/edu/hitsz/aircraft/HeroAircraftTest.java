package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.prop.AbstractProp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        heroAircraft = null;
    }

    @DisplayName("Test crash method")

    @org.junit.jupiter.api.Test
    void crash() {
        heroAircraft.setLocation(200, 200);
        BloodPropFactory bloodPropFactory = new BloodPropFactory();
        AbstractProp bloodProp = bloodPropFactory.createProp(200, 200);
        assumeTrue(heroAircraft != null && bloodProp != null);
        assertTrue(heroAircraft.crash(bloodProp));

        BombPropFactory bombPropFactory = new BombPropFactory();
        AbstractProp bombProp = bombPropFactory.createProp(100, 100);
        assumeTrue(heroAircraft != null && bombProp != null);
        assertFalse(heroAircraft.crash(bombProp));
    }



    @ParameterizedTest
    @DisplayName("Test increaseHp method")
    @CsvSource({"60, 30, 90",
                "980, 30, 1000"})
    void increaseHp(int hp, int increase, int res) {
        assumeTrue(heroAircraft != null);
        heroAircraft.hp = hp;
        heroAircraft.increaseHp(increase);
        assertEquals(heroAircraft.getHp(), res);
    }

    @DisplayName("Test shoot method")
    @org.junit.jupiter.api.Test
    void shoot() {
        // 测试返回列表内元素数量及其类型
        assumeTrue(heroAircraft != null);
        List<BaseBullet> bullets = heroAircraft.shoot();
        assertNotNull(bullets);
        assertEquals(1, bullets.size());
        BaseBullet bullet = bullets.getFirst();
        assertInstanceOf(HeroBullet.class ,bullet);
    }
}