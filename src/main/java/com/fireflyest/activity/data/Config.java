package com.fireflyest.activity.data;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static FileConfiguration getConfig() {
        return config;
    }

    private static FileConfiguration config;

    public static String VERSION;

    public Config(FileConfiguration config){
        this.config = config;
        this.setUp();
    }

    private void setUp(){
        VERSION = config.getString("Version");
    }

    public static void setKey(String key, Object value) {
        config.set(key, value);
    }

}
