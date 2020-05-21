package com.fireflyest.activity.manager;

import java.util.List;

public interface DataManager {

    void setMonth(String name, int month);
    int getMonth(String name);
    void setLastMonthActivity(String name, int activity);
    int getLastMonthActivity(String name);
    void addOnlineTime(String name, int day, long time);
    List<Long> getOnlineTime(String name, int day);
    boolean containsDay(String name, int day);
    void addActivity(String name, int day, int activity);
    int getActivity(String name, int day);
    void addChance(String name, int chance);
    int getChance(String name);
    void addTask(String name, String task);
    List<String> getTaskLit(String name);

}
