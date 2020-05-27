package com.fireflyest.activity.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Language {

    public FileConfiguration getLang() {
        return lang;
    }

    private FileConfiguration lang;

    public static List<String>HELP = new ArrayList<>();
    public static String VERSION;
    public static String TITLE;
    public static String PLUGIN_NAME;
    public static String PLAYER_COMMAND;
    public static String RELOADING;
    public static String RELOADED;
    public static String DISPLAY_DAY;
    public static String NOT_ENOUGH_CHANCE;
    public static String SIGN_IN_FUTURE;
    public static String SIGN_IN_SUCCESS;
    public static String SIGN_IN_ALREADY;
    public static String SIGN_IN_REMIND;
    public static String UNLUCK_TODAY;
    public static String PLAYTIME_REMIND;
    public static String SIGN_IN_FESTIVAL;
    public static String NOT_ENOUGH_ACTIVITY;
    public static String NOT_ENOUGH_SIGNIN;
    public static String NOT_ENOUGH_PLAYTIME;
    public static String EXCHANGE_SUCCESS;
    public static String FAULT_SIGN_IN;
    public static String REWARD_TASK;
    public static String ADD_CHANCE;
    public static String FINISH_TASK;
    public static String SIGN_IN_WEEKEND;
    public static String ACTIVITY_RANK;
    public static String SIGNIN_REWARD_LIMIT;
    public static String DAY_ACTIVITY;
    public static String SELECT_OPTION;
    public static String RIGHT_OPTION;
    public static String FALSE_OPTION;
    public static String WIN_PLAYER;
    public static String SELECTED;
    public static String BUTTON_SIGNIN;
    public static String BUTTON_PLAYTIME;
    public static String BUTTON_TASK;

    public Language(FileConfiguration lang){
        this.lang = lang;
        this.setUp();
    }

    private void setUp(){
        VERSION = lang.getString("Version");
        HELP = lang.getStringList("Help");
        DISPLAY_DAY = lang.getString("DisplayDay").replace("&", "§");
        TITLE = lang.getString("Title").replace("&", "§");
        PLUGIN_NAME = lang.getString("PluginName").replace("&", "§");
        PLAYER_COMMAND = TITLE + lang.getString("PlayerCommand").replace("&", "§");
        RELOADING = TITLE + lang.getString("Reloading").replace("&", "§");
        RELOADED = TITLE + lang.getString("Reloaded").replace("&", "§");

        NOT_ENOUGH_CHANCE = TITLE + lang.getString("NotEnoughChance").replace("&", "§");
        SIGN_IN_FUTURE = TITLE + lang.getString("SignInFuture").replace("&", "§");
        SIGN_IN_SUCCESS = TITLE + lang.getString("SignInSuccess").replace("&", "§");
        SIGN_IN_ALREADY = TITLE + lang.getString("SignInAlready").replace("&", "§");
        SIGN_IN_REMIND = TITLE + lang.getString("SignInRemind").replace("&", "§");
        UNLUCK_TODAY = TITLE + lang.getString("UnluckToday").replace("&", "§");
        PLAYTIME_REMIND = TITLE + lang.getString("PlaytimeRemind").replace("&", "§");
        SIGN_IN_FESTIVAL = TITLE + lang.getString("SignInFestival").replace("&", "§");
        NOT_ENOUGH_ACTIVITY = TITLE + lang.getString("NotEnoughActivity").replace("&", "§");
        EXCHANGE_SUCCESS = TITLE + lang.getString("ExchangeSuccess").replace("&", "§");
        NOT_ENOUGH_SIGNIN  = TITLE + lang.getString("NotEnoughSignin").replace("&", "§");
        NOT_ENOUGH_PLAYTIME = TITLE + lang.getString("NotEnoughPlaytime").replace("&", "§");
        FAULT_SIGN_IN = TITLE + lang.getString("FaultSignIn").replace("&", "§");
        REWARD_TASK = TITLE + lang.getString("RewardTask").replace("&", "§");
        ADD_CHANCE = TITLE + lang.getString("AddChance").replace("&", "§");
        FINISH_TASK  = TITLE + lang.getString("FinishTask").replace("&", "§");
        SIGN_IN_WEEKEND = TITLE + lang.getString("SignInWeekend").replace("&", "§");
        ACTIVITY_RANK  = TITLE + lang.getString("ActivityRank").replace("&", "§");
        SIGNIN_REWARD_LIMIT  = TITLE + lang.getString("SignInRewardLimit").replace("&", "§");
        DAY_ACTIVITY  = TITLE + lang.getString("DayActivity").replace("&", "§");
        SELECT_OPTION  = TITLE + lang.getString("SelectOption").replace("&", "§");
        RIGHT_OPTION  = TITLE + lang.getString("RightOption").replace("&", "§");
        FALSE_OPTION  = TITLE + lang.getString("FalseOption").replace("&", "§");
        WIN_PLAYER  = TITLE + lang.getString("WinPlayer").replace("&", "§");
        SELECTED  = TITLE + lang.getString("Selected").replace("&", "§");
        BUTTON_SIGNIN =  lang.getString("ButtonSignIn").replace("&", "§");
        BUTTON_PLAYTIME =  lang.getString("ButtonPlaytime").replace("&", "§");
        BUTTON_TASK =  lang.getString("ButtonTask").replace("&", "§");
    }

}
