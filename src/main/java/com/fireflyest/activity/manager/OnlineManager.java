package com.fireflyest.activity.manager;

import com.fireflyest.activity.Activity;
import com.fireflyest.activity.chat.ActivityChat;
import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.time.ActivityTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class OnlineManager {

    private OnlineManager(){

    }

    private static DataManager manager;

    private static Map<String, Long>login = new HashMap<>();

    public static void iniOnlineManager(DataManager dataManager){
        manager = dataManager;
        new BukkitRunnable() {
            int hour = ActivityTime.getHour();
            @Override
            public void run() {
                //在线活跃奖励
                for(Player player : Bukkit.getOnlinePlayers()) {
                    String name = player.getName();
                    //半小时获得一活跃度(未签到的不给)
                    if(manager.containsDay(name, ActivityTime.getDay())){
                        manager.addActivity(name, ActivityTime.getDay(), 1);
                    }
                    //半小时提醒一次在线奖励
                    if(manager.getOnlineTime(name, ActivityTime.getDay())>1800000){
                        player.sendMessage(Language.PLAYTIME_REMIND);
                        ActivityChat.sendCommandButton(player, "在线奖励", "GREEN", "点击查看在线奖励", "/activity playtime");
                    }
                    //每天早上清空已完成任务
                    if(hour == 4 && ActivityTime.getHour() == 5) {
                        for(String task : manager.getTaskList(name)) {
                            if("finish".equalsIgnoreCase(task.split(",")[2])){
                                manager.updateTask(name, task);
                            }
                        }
                    }
                }
                hour = ActivityTime.getHour();
            }
        }.runTaskTimer(Activity.getProvidingPlugin(Activity.class), 0L, 36000L);
    }

    /**
     * 获取玩家在线时间
     * @param name 游戏名
     * @return 在线时间
     */
    public static long getPlaytime(String name){
        if(login.getOrDefault(name, 0L)==0){
            login.put(name, ActivityTime.getDate());
        }
        long l = login.getOrDefault(name, 0L);
        return ActivityTime.getDate()-l;
    }

    public static void playerJoin(Player player){
        String name = player.getName();

        //判断玩家数据是否本月
        int month = manager.getMonth(name);
        if(month ==0){
            iniPlayerData(name);
        }else if(month != ActivityTime.getMonth()){
            updatePlayerData(name);
        }

        //记录上线时间
        login.put(name, ActivityTime.getDate());

        //发送提醒签到按钮
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!manager.containsDay(name, ActivityTime.getDay())) {
                    player.sendMessage(Language.SIGN_IN_REMIND);
                    ActivityChat.sendCommandButton(player, "点击签到", "GREEN", "点击打开签到界面", "/activity");
                }
                cancel();
                return;
            }
        }.runTaskLater(Activity.getProvidingPlugin(Activity.class), 20L);
    }

    public static void iniPlayerData(String name){
        manager.setMonth(name, ActivityTime.getMonth());
        manager.addChance(name, 2);
        manager.addTask(name, (String)Config.TASK.toArray()[0], "0");
    }

    public static void updatePlayerData(String name){
        manager.setMonth(name, ActivityTime.getMonth());
        manager.addChance(name, 2);
        manager.setLastMonthActivity(name, manager.getTotalActivity(name));
        manager.clearData(name);
    }

    public static void savePlayerTime(String name){
        manager.addOnlineTime(name, ActivityTime.getDay(), getPlaytime(name));
        login.put(name, ActivityTime.getDate());
    }

}
