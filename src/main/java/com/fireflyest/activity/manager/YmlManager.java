package com.fireflyest.activity.manager;

import com.fireflyest.activity.data.YamlManager;

import java.util.List;

public class YmlManager implements DataManager{
    @Override
    public void setMonth(String name, int month) {
        YamlManager.setPlayerData(name, "Month", month);
    }

    @Override
    public int getMonth(String name) {
        return YamlManager.getPlayerData(name).getInt("Month", 0);
    }

    @Override
    public void setLastMonthActivity(String name, int activity) {
        YamlManager.setPlayerData(name, "Last", activity);
    }

    @Override
    public int getLastMonthActivity(String name) {
        return YamlManager.getPlayerData(name).getInt("Last", 0);
    }

    @Override
    public void addOnlineTime(String name, int day, long time) {
        List<Long>online = YamlManager.getPlayerData(name).getLongList("Days."+day+".Online");
        online.add(time);
        YamlManager.setPlayerData(name, "Days."+day+".Online", online);
    }

    @Override
    public List<Long> getOnlineTime(String name, int day) {
        return YamlManager.getPlayerData(name).getLongList("Days."+day+".Online");
    }

    @Override
    public boolean containsDay(String name, int day) {
        return YamlManager.getPlayerData(name).contains("Days."+day);
    }

    @Override
    public void addActivity(String name, int day, int activity) {
        int a = YamlManager.getPlayerData(name).getInt("Days."+day+".Activity");
        a+=activity;
        YamlManager.setPlayerData(name, "Days."+day+".Activity", a);
    }

    @Override
    public int getActivity(String name, int day) {
        return YamlManager.getPlayerData(name).getInt("Days."+day+".Activity");
    }

    @Override
    public void addChance(String name, int chance) {
        int c = YamlManager.getPlayerData(name).getInt("Chance");
        c+=chance;
        YamlManager.setPlayerData(name, "Chance", c);
    }

    @Override
    public int getChance(String name) {
        return YamlManager.getPlayerData(name).getInt("Chance");
    }

    @Override
    public void addTask(String name, String task) {

    }

    @Override
    public List<String> getTaskLit(String name) {
        return null;
    }
}
