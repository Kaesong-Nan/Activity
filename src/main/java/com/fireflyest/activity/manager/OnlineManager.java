package com.fireflyest.activity.manager;

import com.fireflyest.activity.time.ActivityTime;
import org.bukkit.entity.Player;

public class OnlineManager {

    private DataManager manager;

    public OnlineManager(DataManager dataManager){
        manager = dataManager;
    }

    public void playerJoin(String name){
        int month = manager.getMonth(name);
        if(month ==0){
            iniPlayerData(name);
        }else if(month == ActivityTime.getMonth()){

        }else{
            updatePlayerData(name);
        }
    }

    public void iniPlayerData(String name){
        manager.setMonth(name, ActivityTime.getMonth());
        manager.addChance(name, 2);
        manager.addTask(name, "");
    }

    public void updatePlayerData(String name){
        manager.setMonth(name, ActivityTime.getMonth());
        manager.addChance(name, 2);
    }

}
