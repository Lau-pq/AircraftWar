package edu.hitsz.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    public static boolean isActive = true;

    private static final Map<String, String> EVENT_MUSIC_PATH_MAP = new HashMap<>();

    public static String MUSIC_BGM_PATH = "src/videos/bgm.wav";
    public static String MUSIC_BGM_BOSS_PATH = "src/videos/bgm_boss.wav";
    public static String MUSIC_BOMB_EXPLOSION_PATH = "src/videos/bomb_explosion.wav";
    public static String MUSIC_BULLET_PATH = "src/videos/bullet.wav";
    public static String MUSIC_BULLET_HIT_PATH = "src/videos/bullet_hit.wav";
    public static String MUSIC_GAME_OVER_PATH = "src/videos/game_over.wav";
    public static String GET_SUPPLY_PATH = "src/videos/get_supply.wav";
    private static String SOURCE_PATH;

    private static MusicController bgmMusicController;
    private static MusicController bossMusicController;

    static {
        EVENT_MUSIC_PATH_MAP.put("begin", MUSIC_BGM_PATH);
        EVENT_MUSIC_PATH_MAP.put("boss", MUSIC_BGM_BOSS_PATH);
        EVENT_MUSIC_PATH_MAP.put("bomb", MUSIC_BOMB_EXPLOSION_PATH);
        EVENT_MUSIC_PATH_MAP.put("bullet", MUSIC_BULLET_PATH);
        EVENT_MUSIC_PATH_MAP.put("hit", MUSIC_BULLET_HIT_PATH);
        EVENT_MUSIC_PATH_MAP.put("over", MUSIC_GAME_OVER_PATH);
        EVENT_MUSIC_PATH_MAP.put("supply", GET_SUPPLY_PATH);
    }

    public static void action(String event) {
        if(isActive) {
            if (EVENT_MUSIC_PATH_MAP.containsKey(event)) SOURCE_PATH = EVENT_MUSIC_PATH_MAP.get(event);
            switch (event) {
                case "begin" -> bgmMusicController = loopPlay(SOURCE_PATH);
                case "boss" -> bossMusicController = loopPlay(SOURCE_PATH);
                case "bomb", "bullet", "hit", "supply" -> play(SOURCE_PATH);
                case "boss_defeated" -> {
                    if (bossMusicController != null) bossMusicController.stop();
                }
                case "over" -> {
                    if (bgmMusicController != null) bgmMusicController.stop();
                }
            }
        }

    }

    private static void play(String fileName) {
        new MusicController(fileName, false).startPlaybackThread();
    }

    private static MusicController loopPlay(String fileName) {
        MusicController musicController = new MusicController(fileName, true);
        musicController.startPlaybackThread();
        return musicController;
    }

}
