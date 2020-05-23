package com.fireflyest.activity.manager;

import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.YamlManager;
import com.fireflyest.activity.time.ActivityTime;

import java.util.List;
import java.util.Set;

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
        long online = YamlManager.getPlayerData(name).getLong("Days."+day+".Online");
        online+=time;
        YamlManager.setPlayerData(name, "Days."+day+".Online", online);
    }

    @Override
    public long getOnlineTime(String name, int day) {
        return YamlManager.getPlayerData(name).getLong("Days."+day+".Online");
    }

    @Override
    public long getTotalTime(String name) {
        long time = 0;
        if(!YamlManager.getPlayerData(name).contains("Days"))return 0;
        for(String day : YamlManager.getPlayerDataKeys(name, "Days")){
            time+=getOnlineTime(name, Integer.parseInt(day));
        }
        return time;
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
    public int getTotalActivity(String name) {
        int activity = 0;
        if(!YamlManager.getPlayerData(name).contains("Days"))return 0;
        for(String day : YamlManager.getPlayerDataKeys(name, "Days")){
            activity+=getActivity(name, Integer.parseInt(day));
        }
        return activity;
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
    public void addAddUp(String name) {
        int a = YamlManager.getPlayerData(name).getInt("Addup");
        a++;
        YamlManager.setPlayerData(name, "Addup", a);
    }

    @Override
    public int getAddUp(String name) {
        return YamlManager.getPlayerData(name).getInt("Addup");
    }

    @Override
    public int getSeries(String name) {
        int series = 0;
        if(containsDay(name, ActivityTime.getDay()-1)) {
            for(int i = ActivityTime.getDay() ; i > 0 ; i--) {
                if(!containsDay(name, i))break;
                series++;
            }
        }
        return series;
    }

    @Override
    public void addTask(String name, String task, String progress) {
        YamlManager.setPlayerData(name, "Task."+task,
                Config.getTaskString(task+".Work")+","+Config.getTaskString(task+".Target")+","+progress
        );
    }

    @Override
    public String getTask(String name, String task) {
        return YamlManager.getPlayerData(name).getString("Task."+task);
    }

    @Override
    public int getDidTask(String name) {
        int did = 0;
        for(String task : getTaskList(name)){
            if("did".equalsIgnoreCase(getTask(name, task).split(",")[2]))did++;
        }
        return did;
    }

    @Override
    public void updateTask(String name, String task) {
        String progress = getTask(name, task).split(",")[2];
        if("did".equalsIgnoreCase(progress)){
            //完成未领取
            addTask(name, task, "finish");
        }else if("finish".equalsIgnoreCase(progress)){
            //完成已领取
            if(YamlManager.getPlayerDataKeys(name, "Task").size() > 1){
                YamlManager.setPlayerData(name, "Task."+task,null);
            }
        }else {
            //未完成
            int p = Integer.parseInt(progress)+1;
            //判断是否完成
            if(p == Config.getTaskInt(task+".Progress")){
                addTask(name, task, "did");
            }else {
                addTask(name, task, p+"");
            }
        }
    }

    @Override
    public Set<String> getTaskList(String name) {
        return YamlManager.getPlayerData(name).getConfigurationSection("Task").getKeys(false);
    }

    @Override
    public void clearData(String name) {
        YamlManager.setPlayerData(name, "Day", null);
    }

    @Override
    public boolean hasAReward(String name, String reward) {
        return YamlManager.getPlayerData(name).getStringList("AReward").contains(reward);
    }

    @Override
    public boolean hasPReward(String name, String reward) {
        return YamlManager.getPlayerData(name).getStringList("PReward").contains(reward);
    }

    @Override
    public boolean hasSReward(String name, String reward) {
        return YamlManager.getPlayerData(name).getStringList("SReward").contains(reward);
    }

    @Override
    public void addAReward(String name, String reward) {
        List<String>rewards = YamlManager.getPlayerData(name).getStringList("AReward");
        rewards.add(reward);
        YamlManager.setPlayerData(name, "AReward", rewards);
    }

    @Override
    public void addPReward(String name, String reward) {
        List<String>rewards = YamlManager.getPlayerData(name).getStringList("PReward");
        rewards.add(reward);
        YamlManager.setPlayerData(name, "PReward", rewards);
    }

    @Override
    public void addSReward(String name, String reward) {
        List<String>rewards = YamlManager.getPlayerData(name).getStringList("SReward");
        rewards.add(reward);
        YamlManager.setPlayerData(name, "SReward", rewards);
    }


}
