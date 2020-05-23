package com.fireflyest.activity.manager;

import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RewardManager {

    private static DataManager manager;

    private RewardManager(){

    }

    public static void iniRewardManager(DataManager dataManager){
        manager = dataManager;
    }

    /**
     * 签到奖励
     * @param player 玩家
     * @param weekend 是否周末
     */
    public static void signInReward(Player player, boolean weekend){
        String name = player.getName();
        int random;
        if(weekend) {
            random = (int)(Config.WEEKEND.size()*Math.random());
            player.sendMessage(Language.SIGN_IN_WEEKEND);
            serverCommand(Config.WEEKEND.get(random).replace("%player%", name));
        }else {
            random = (int)(Config.REWARDS.size()*Math.random());
            serverCommand(Config.REWARDS.get(random).replace("%player%", name));
        }
        player.sendMessage(Language.SIGN_IN_SUCCESS);
        if(random == 0)player.sendMessage(Language.UNLUCK_TODAY);
    }

    /**
     * 活跃奖励
     * @param player 玩家
     * @param reward 奖励
     */
    public static void aReward(Player player, String reward){
        //判断活跃是否足够或是否领取过
        String name = player.getName();
        if(manager.getTotalActivity(name) < Config.getActivityInt(reward+".Price")){
            player.sendMessage(Language.NOT_ENOUGH_ACTIVITY);
            return;
        }
        if(manager.hasAReward(name, reward)){
            player.sendMessage(Language.SIGNIN_REWARD_LIMIT);
            return;
        }
        manager.addAReward(name, reward);
        player.sendMessage(Language.EXCHANGE_SUCCESS);
        player.playSound(player.getLocation(), "entity.player.levelup", 1, 1);
        serverCommand(Config.getActivityString(reward+".Reward"));
    }

    /**
     * 在线奖励
     * @param player 玩家
     * @param reward 奖励
     */
    public static void pReward(Player player, String reward){
        String name = player.getName();
        String time = Config.getPlaytimeString(reward+".Time"), type = Config.getPlaytimeString(reward+".Type");
        long date = 1;
        if(time.contains("分")) { date = 1000*60; date *= Integer.parseInt(time.replace("分", "")); 	}
        if(time.contains("时")) { date = 1000*60*60; date *= Integer.parseInt(time.replace("时", "")); }
        if(time.contains("天")) { date = 1000*60*60*24; date *= Integer.parseInt(time.replace("天", "")); }
        if("本月".equals(type)){
            if(manager.getTotalTime(name)+OnlineManager.getPlaytime(name) < date){
                player.sendMessage(Language.NOT_ENOUGH_PLAYTIME);
                return;
            }
            if(manager.hasPReward(name, reward)){
                player.sendMessage(Language.SIGNIN_REWARD_LIMIT);
                return;
            }
            manager.addPReward(name, reward);
        }else {
            if(OnlineManager.getPlaytime(name) < date){
                player.sendMessage(Language.NOT_ENOUGH_PLAYTIME);
                return;
            }
            if(manager.hasPReward(name, reward)){
                player.sendMessage(Language.SIGNIN_REWARD_LIMIT);
                return;
            }
            OnlineManager.savePlayerTime(name);
        }
        player.sendMessage(Language.EXCHANGE_SUCCESS);
        player.playSound(player.getLocation(), "entity.player.levelup", 1, 1);
        RewardManager.serverCommand(Config.getPlaytimeString(reward+".Reward"));
    }

    /**
     * 签到奖励
     * @param player 玩家
     * @param reward 奖励
     */
    public static void sReward(Player player, String reward){
        String name = player.getName();
        int amount = Config.getSigninInt(reward+".Amount");
        String type = Config.getSigninString(reward+".Type");
        if("连续".equals(type)){
            if(manager.getSeries(name) < amount){
                player.sendMessage(Language.NOT_ENOUGH_SIGNIN);
                return;
            }
            if(manager.hasSReward(name, reward)){

            }
        }else {
            if(manager.getAddUp(name) < amount){
                player.sendMessage(Language.NOT_ENOUGH_SIGNIN);
                return;
            }
        }
        manager.addSReward(name, reward);
        player.sendMessage(Language.EXCHANGE_SUCCESS);
        player.playSound(player.getLocation(), "entity.player.levelup", 1, 1);
        RewardManager.serverCommand(Config.getSigninString(reward+".Reward"));
    }

    /**
     * 后台指令
     * @param command 指令
     */
    public static void serverCommand(String command){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

}
