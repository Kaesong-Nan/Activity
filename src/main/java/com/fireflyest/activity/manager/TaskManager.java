package com.fireflyest.activity.manager;

import com.fireflyest.activity.Activity;
import com.fireflyest.activity.chat.ActivityChat;
import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.data.YamlManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskManager {

    private static DataManager manager;

    private TaskManager(){

    }

    public static void iniTaskManager(DataManager dataManager){
        manager = dataManager;
    }

    public static void doTask(Player player, String tg, String type){
        if(!YamlManager.isEnable())return;
        String name = player.getName();
        new BukkitRunnable() {
            @Override
            public void run() {
                //遍历玩家接受的任务
                for(String task : manager.getTaskList(name)){
                    //判断类型是否一致
                    if(!type.equalsIgnoreCase(Config.getTaskString(task+".Work")))continue;
                    //判断进度
                    String progress = manager.getTask(name, task).split(",")[2];
                    if(progress.equalsIgnoreCase("did") || progress.equalsIgnoreCase("finish"))continue;
                    //判断是否包含目标
                    String target = Config.getTaskString(task+".Target");
                    if(!Config.containsTarget(target, tg))continue;
                    //更新任务
                    manager.updateTask(name, task);
                    if("did".equalsIgnoreCase(manager.getTask(name, task).split(",")[2])){
                        player.sendMessage(Language.FINISH_TASK.replace("%task%", task));
                        ActivityChat.sendCommandButton(player, "活跃任务", "GREEN", "点击领取奖励", "/activity task");
                    }
                    break;
                }
                cancel();
            }
        }.runTask(Activity.getProvidingPlugin(Activity.class));
    }

}
