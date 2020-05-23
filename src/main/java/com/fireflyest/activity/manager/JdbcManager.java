package com.fireflyest.activity.manager;

import java.util.Set;

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
    public long getOnlineTime(String name, int day) {
        return 0;
    }

    @Override
    public long getTotalTime(String name) {
        return 0;
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
    public int getTotalActivity(String name) {
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
    public void addAddUp(String name) {

    }

    @Override
    public int getAddUp(String name) {
        return 0;
    }

    @Override
    public int getSeries(String name) {
        return 0;
    }

    @Override
    public void addTask(String name, String task, String progress) {

    }

    @Override
    public String getTask(String name, String Task) {
        return null;
    }

    @Override
    public int getDidTask(String name) {
        return 0;
    }

    @Override
    public void updateTask(String name, String task) {

    }

    @Override
    public Set<String> getTaskList(String name) {
        return null;
    }

    @Override
    public void clearData(String name) {

    }

    @Override
    public boolean hasAReward(String name, String reward) {
        return false;
    }

    @Override
    public boolean hasPReward(String name, String reward) {
        return false;
    }

    @Override
    public boolean hasSReward(String name, String reward) {
        return false;
    }

    @Override
    public void addAReward(String name, String reward) {

    }

    @Override
    public void addPReward(String name, String reward) {

    }

    @Override
    public void addSReward(String name, String reward) {

    }


}
