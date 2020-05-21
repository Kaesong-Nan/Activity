package com.fireflyest.activity.data;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static FileConfiguration getConfig() {
        return config;
    }

    private static FileConfiguration config;

    public static String VERSION;
    public static boolean HEAD_SKIN;
    public static int DEFAULT_CHANCE;

    public Config(FileConfiguration config){
        this.config = config;
        this.setUp();
    }

    private void setUp(){
        VERSION = config.getString("Version");
        HEAD_SKIN = config.getBoolean("DisplaySkin");
        DEFAULT_CHANCE = config.getInt("DefaultChance");
    }

    public static void setKey(String key, Object value) {
        config.set(key, value);
    }

}
