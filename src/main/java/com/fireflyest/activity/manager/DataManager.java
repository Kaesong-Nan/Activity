package com.fireflyest.activity.manager;

import java.util.Set;

public interface DataManager {

    void setMonth(String name, int month);
    int getMonth(String name);
    void setLastMonthActivity(String name, int activity);
    int getLastMonthActivity(String name);
    void addOnlineTime(String name, int day, long time);
    long getOnlineTime(String name, int day);
    long getTotalTime(String name);
    boolean containsDay(String name, int day);
    void addActivity(String name, int day, int activity);
    int getActivity(String name, int day);
    int getTotalActivity(String name);
    void addChance(String name, int chance);
    int getChance(String name);
    void addAddUp(String name);
    int getAddUp(String name);
    int getSeries(String name);
    void addTask(String name, String task, String progress);
    String getTask(String name, String Task);
    int getDidTask(String name);
    void updateTask(String name, String task);
    Set<String> getTaskList(String name);
    void clearData(String name);
    boolean hasAReward(String name, String reward);
    boolean hasPReward(String name, String reward);
    boolean hasSReward(String name, String reward);
    void addAReward(String name, String reward);
    void addPReward(String name, String reward);
    void addSReward(String name, String reward);
    Set<String> getDayList(String name);

}
