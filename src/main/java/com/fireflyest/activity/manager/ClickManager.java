package com.fireflyest.activity.manager;

import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.item.ActivityItem;
import com.fireflyest.activity.time.ActivityTime;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ClickManager {

    private static DataManager manager;

    private ClickManager(){

    }

    public static void iniClickManager(DataManager dataManager){
        manager = dataManager;
    }

    public static void playerClick(ItemStack item, Player player){
        if(!item.hasItemMeta())return;
        if(!item.getItemMeta().hasLore())return;
        String name = player.getName();
        List<String> lores = item.getItemMeta().getLore();
        if(!lores.get(0).contains("@")) return;
        player.playSound(player.getLocation(), "block.stone_button.click_on", 1, 1);
        String type = lores.get(0).substring(5);
        int day;
        switch(type) {
            case "finish":
                player.sendMessage(Language.SIGN_IN_ALREADY);
                break;
            case "future":
                player.sendMessage(Language.SIGN_IN_FUTURE);
                break;
            case "now"://签到
                day = Integer.parseInt(ActivityItem.getItemValue(item.getItemMeta().getDisplayName()));
                signIn(day, player, item.getType().name());
                GuiManager.updatePlayerGui(player);
                break;
            case "miss"://补签
                if(manager.getChance(name) <= 0) { player.sendMessage(Language.NOT_ENOUGH_CHANCE); return; }
                day = Integer.parseInt(ActivityItem.getItemValue(item.getItemMeta().getDisplayName()));
                signIn(day, player, item.getType().name());
                manager.addChance(name, -1);
                GuiManager.updatePlayerGui(player);
                break;
            case "festival":
                day = Integer.parseInt(ActivityItem.getItemValue(item.getItemMeta().getDisplayName()));
                //判断节日是否签到当天
                if(day == ActivityTime.getDay()){
                    signIn(day, player, item.getType().name());
                    String itemName = item.getItemMeta().getDisplayName();
                    String festival = itemName.substring(itemName.indexOf("[§b§l")+5, itemName.indexOf("§f]"));
                    RewardManager.serverCommand(Config.getFestivalString(festival+".Reward").replace("%player%", name));
                    player.sendMessage(Language.SIGN_IN_FESTIVAL.replace("%festival%", festival));
                }else {
                    player.sendMessage(Language.FAULT_SIGN_IN);
                }
                GuiManager.updatePlayerGui(player);
                break;
            case "back":
                player.closeInventory();
                GuiManager.openGui(player);
                break;
            case "close":
                player.closeInventory();
                break;
            case "activity":
                player.closeInventory();
                player.openInventory(GuiManager.ACTIVITY);
                break;
            case "signin":
                player.closeInventory();
                player.openInventory(GuiManager.SIGN_IN);
                break;
            case "playtime":
                player.closeInventory();
                player.openInventory(GuiManager.PLAYTIME);
                break;
            case "task":
                player.closeInventory();
                GuiManager.openTaskGui(player);
                break;
            case "taskbook":
                String task = item.getItemMeta().getDisplayName().substring(4), statue = item.getType().name();
                //接受或领取奖励
                if("BOOK".equals(statue)){
                    manager.addTask(name, task, 0+"");
                }else if("ENCHANTED_BOOK".equals(statue)){
                    int activity = Config.getTaskInt(task+".Reward");
                    manager.updateTask(name, task);
                    manager.addActivity(name, ActivityTime.getDay(), activity);
                    player.sendMessage(Language.REWARD_TASK.replace("%task%", task).replace("%amount%", activity+""));
                }
                GuiManager.openTaskGui(player);
                break;
            default:
                //领取奖励界面
                if(!type.contains("reward"))break;
                String reward = item.getItemMeta().getDisplayName().substring(4);;
                if(type.startsWith("a")){
                    //活跃奖励
                    RewardManager.aReward(player, reward);
                }else if(type.startsWith("p")){
                    //在线奖励
                    RewardManager.pReward(player, reward);
                }else {
                    //签到奖励
                    RewardManager.sReward(player, reward);
                }
        }
    }

    private static void signIn(int day, Player player, String type) {//签到
        String name = player.getName();
        RewardManager.signInReward(player, Config.WEEKEND_ITEM.equals(type));
        manager.addActivity(name, day, 1);
        manager.addAddUp(name);
        player.playSound(player.getLocation(), "entity.player.levelup", 1, 1);
    }

}
