package com.fireflyest.activity.manager;

import java.util.List;

public class JdbcManager implements DataManager{
    @Override
    public void setMonth(String name, int month) {

    }

    @Override
    public int getMonth(String name) {
        return 0;
    }

    @Override
    public void setLastMonthActivity(String name, int activity) {

    }

    @Override
    public int getLastMonthActivity(String name) {
        return 0;
    }

    @Override
    public void addOnlineTime(String name, int day, long time) {

    }

    @Override
    public List<Long> getOnlineTime(String name, int day) {
        return null;
    }

    @Override
    public boolean containsDay(String name, int day) {
        return false;
    }

    @Override
    public void addActivity(String name, int day, int activity) {

    }

    @Override
    public int getActivity(String name, int day) {
        return 0;
    }

    @Override
    public void addChance(String name, int chance) {

    }

    @Override
    public int getChance(String name) {
        return 0;
    }

    @Override
    public void addTask(String name, String task) {

    }

    @Override
    public List<String> getTaskLit(String name) {
        return null;
    }
}
