package com.fireflyest.activity.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Set;

public class Config {

    public static FileConfiguration getConfig() {
        return config;
    }

    private static FileConfiguration config;

    public static String VERSION;
    public static boolean HEAD_SKIN;
    public static boolean TASK_PROGRESS;
    public static int DEFAULT_CHANCE;
    public static int QUIZ_ACTIVITY;
    public static List<String>REWARDS;
    public static List<String> WEEKEND;
    public static String WEEKEND_ITEM;
    public static Set<String>FESTIVAL;
    public static Set<String>ACTIVITY;
    public static Set<String>SIGN_IN;
    public static Set<String>PLAYTIME;
    public static Set<String>TASK;
    public static Set<String>OBJECT;

    public Config(FileConfiguration config){
        this.config = config;
        this.setUp();
    }

    private void setUp(){
        VERSION = config.getString("Version");
        HEAD_SKIN = config.getBoolean("DisplaySkin");
        TASK_PROGRESS = config.getBoolean("TaskProgress");
        DEFAULT_CHANCE = config.getInt("DefaultChance");
        QUIZ_ACTIVITY = config.getInt("QuizActivity");
        REWARDS = config.getStringList("Rewards");
        WEEKEND = config.getStringList("Weekend");
        WEEKEND_ITEM = config.getString("WeekendItem");
        FESTIVAL = config.getConfigurationSection("Festival").getKeys(false);
        ACTIVITY = config.getConfigurationSection("Activity").getKeys(false);
        SIGN_IN = config.getConfigurationSection("Signin").getKeys(false);
        PLAYTIME = config.getConfigurationSection("Playtime").getKeys(false);
        TASK = config.getConfigurationSection("Task").getKeys(false);
        OBJECT = config.getConfigurationSection("Object").getKeys(false);
    }

    public static void setKey(String key, Object value) {
        config.set(key, value);
    }

    public static String getString(String key){
        return config.getString(key, "STONE");
    }

    public static int getInt(String key){
        return config.getInt(key, 0);
    }

    public static String getFestivalString(String key){
        return getString("Festival."+key);
    }

    public static int getFestivalInt(String key){
        return getInt("Festival."+key);
    }

    public static String getActivityString(String key){
        return getString("Activity."+key);
    }

    public static int getActivityInt(String key){
        return getInt("Activity."+key);
    }

    public static String getSigninString(String key){
        return getString("Signin."+key);
    }

    public static int getSigninInt(String key){
        return getInt("Signin."+key);
    }

    public static String getPlaytimeString(String key){
        return getString("Playtime."+key);
    }

    public static int getPlaytimeInt(String key){
        return getInt("Playtime."+key);
    }

    public static String getTaskString(String key){
        return getString("Task."+key);
    }

    public static int getTaskInt(String key){
        return getInt("Task."+key);
    }

    public static boolean containsTarget(String object, String target){
        String[] o = config.getString("Object."+object).split(",");
        for (String t : o){
            if(target.equalsIgnoreCase(t))return true;
        }
        return false;
    }

}
