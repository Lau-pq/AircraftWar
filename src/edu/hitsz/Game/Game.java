package edu.hitsz.Game;

import edu.hitsz.DAO.Record;
import edu.hitsz.DAO.RecordDao;
import edu.hitsz.DAO.RecordDaoImpl;
import edu.hitsz.Swing.RankingBoard;
import edu.hitsz.Swing.StartMenu;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.*;
import edu.hitsz.basic.FlyingsObserver;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.*;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    protected final List<EnemyAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    /**
     * 当前得分
     */
    private int score = 0;

    // BOSS机 出现阈值
    private int bossThreshold = 500;

    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    /**
     * 游戏图片图片
     */
    protected BufferedImage background;

    /**
     * 游戏难度
     */
    protected int level;

    /**
     * 数据库
     */
    private RecordDao recordDao;

    /**
     * 难度相关参数
     */
    protected int enemyMaxNumber = 5; // 屏幕中出现的敌机最大数量
    protected int mobEnemyHP = 30; // 普通机血量
    protected int eliteEnemyHP = 60; // 精英机血量
    protected int elitePlusEnemyHP = 60; // 超级精英机血量
    protected int bossEnemyHP = 600; // Boss机血量
    protected int heroShootTime = 600; // 英雄机射击周期
    protected int eliteEnemyShootTime = 800; // 精英机射击周期
    protected int elitePlusEnemyShootTime = 1200; // 超级精英机射击周期
    protected int bossEnemyShootTime = 1600; // boss机射击周期
    protected double eliteProbability = 0.2; // 精英机与超级精英机产生概率
    protected int enemyCycleDuration = 600; // 敌机产生周期
    protected int updateCycleDuration = 10000; // 难度更新周期 10s

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public final void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 新敌机产生
            if (timeCountAndNewCycleJudge(enemyCycleDuration)) addEnemyAircrafts();

            // boss产生
            if (score >= bossThreshold && !isBossExist()) generateBoss();

            // 更新难度
            if (timeCountAndNewCycleJudge(updateCycleDuration)) updateAction();

            // 飞机射出子弹
            shootAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 死亡检测
            deathCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检测
            gameOverCheckAction();
        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        MusicManager.action("begin");
    }

    //***********************
    //      Action 各部分
    //***********************

    private void addEnemyAircrafts() {
        if (enemyAircrafts.size() < enemyMaxNumber) {
            EnemyAircraft enemyAircraft;
            if (Math.random() < eliteProbability) {
                enemyAircraft = new ElitePlusEnemyFactory().createAircraft(elitePlusEnemyHP, elitePlusEnemyShootTime);
            } else if (Math.random() < eliteProbability * 2) {
                enemyAircraft = new EliteEnemyFactory().createAircraft(eliteEnemyHP, eliteEnemyShootTime);
            } else {
                enemyAircraft = new MobEnemyFactory().createAircraft(mobEnemyHP, 600);
            }
            enemyAircrafts.add(enemyAircraft);
        }
    }

    private boolean timeCountAndNewCycleJudge(int cycleDuration) {
        return time % cycleDuration == 0;
    }

    protected abstract void generateBoss();

    protected boolean isBossExist() {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft instanceof BossEnemy) {
                return true;
            }
        }
        return false;
    }

    protected abstract void updateAction();

    protected abstract void printDifficulty();

    protected int updateParam(int param, int limit, int change) {
        if ((change > 0 && param < limit) || (change < 0 && param > limit)) {
            param += change;
        }
        return param;
    }

    protected double updateParam(double param, double limit, double change) {
        if ((change > 0 && param < limit) || (change < 0 && param > limit)) {
            param += change;
        }
        return param;
    }

    private void shootAction() {
        // TODO 敌机射击
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            if (timeCountAndNewCycleJudge(enemyAircraft.getShootTime())) {
                enemyBullets.addAll(enemyAircraft.shoot());
            }
        }
        if (timeCountAndNewCycleJudge(heroAircraft.getShootTime())) {
            // 英雄射击
            heroBullets.addAll(heroAircraft.shoot());
        }
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    MusicManager.action("hit");
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        enemyAircraft.setIsNaturalDeath(false);
                        props.addAll(enemyAircraft.dropProp());
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop) || prop.crash(heroAircraft)) {
                if (prop instanceof BombProp) {
                    for (EnemyAircraft enemyAircraft : enemyAircrafts) {
                        ((BombProp) prop).addflyingsObserver(enemyAircraft);
                    }
                    for (BaseBullet bullet: enemyBullets) {
                        ((BombProp) prop).addflyingsObserver((FlyingsObserver) bullet);
                    }
                }
                prop.activate(heroAircraft);
                prop.vanish();
            }
        }
    }

    private void deathCheckAction() {
        for (EnemyAircraft enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.notValid() && !enemyAircraft.getIsNaturalDeath()) {
                score += enemyAircraft.getScore();
                if (enemyAircraft instanceof BossEnemy) {
                    bossThreshold = (score + 500) / 500 * 500;
                    MusicManager.action("boss_defeated");
                }
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    private void gameOverCheckAction() {
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            executorService.shutdown();
            gameOverFlag = true;
            System.out.println("Game Over!");

            boolean validName = false;
            String userName = "";
            MusicManager.action("over");
            while (!validName) {
                userName = JOptionPane.showInputDialog(null, "游戏结束，您的得分是 %d，请输入用户名：".formatted(score));
                Object[] options = {"确认"};
                if (Objects.equals(userName, "")) {
                    JOptionPane.showOptionDialog(null, "您还没有输入 ", "提示", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,null, options, options[0]);
                } else {
                    validName = true;
                }
            }
            heroAircraft.reset();
            recordDao = new RecordDaoImpl(level);
            recordDao.getAllRecords();
            recordDao.saveRecord(new Record(userName, score,
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            Main.cardPanel.add(new StartMenu().getMainPanel(), "start");
            Main.cardPanel.add(new RankingBoard(level).getMainPanel(), "RankingBoard");
            Main.cardLayout.show(Main.cardPanel, "RankingBoard");
        }
    }



    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(background, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(background, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, props);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

        // 绘制英雄机血条
        printHeroHealthBar(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
            if (object instanceof BossEnemy) {
                printBossHealthBar(g, (BossEnemy) object);
            }
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    private void printHeroHealthBar(Graphics g) {
        int barWidth = heroAircraft.getWidth();
        int barHeight = 5;
        int x = heroAircraft.getLocationX() - (heroAircraft.getWidth() / 2);
        int y = heroAircraft.getLocationY() + (heroAircraft.getHeight() / 2);

        // 绘制血条底色
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        int hp = heroAircraft.getHp();
        int maxHp = heroAircraft.getMaxHp();
        int fillWidth = hp * barWidth / maxHp; // 计算填充宽度
        Color fillColor = hp > maxHp / 4 ? Color.GREEN : Color.RED;

        // 绘制血条填充色
        g.setColor(fillColor);
        g.fillRect(x, y, fillWidth, barHeight);

        // 绘制血条边框
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }

    private void printBossHealthBar(Graphics g, BossEnemy bossEnemy) {
        int barWidth = bossEnemy.getWidth();
        int barHeight = 5;
        int x = bossEnemy.getLocationX() - (bossEnemy.getWidth() / 2);
        int y = bossEnemy.getLocationY() + (bossEnemy.getHeight() / 2);

        // 绘制血条底色
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        int hp = bossEnemy.getHp();
        int maxHp = bossEnemy.getMaxHp();
        int fillWidth = hp * barWidth / maxHp; // 计算填充宽度
        Color fillColor = Color.RED;

        // 绘制血条填充色
        g.setColor(fillColor);
        g.fillRect(x, y, fillWidth, barHeight);

        // 绘制血条边框
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }

}
