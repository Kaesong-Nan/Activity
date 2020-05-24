package com.fireflyest.activity.command;

import com.fireflyest.activity.Activity;
import com.fireflyest.activity.chat.ActivityChat;
import com.fireflyest.activity.convert.ActivityConvert;
import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.data.YamlManager;
import com.fireflyest.activity.manager.DataManager;
import com.fireflyest.activity.manager.GuiManager;
import com.fireflyest.activity.manager.YmlManager;
import com.fireflyest.activity.time.ActivityTime;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ActivityCommand implements CommandExecutor {

    private static DataManager manager;

    public ActivityCommand(){
        manager = new YmlManager();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("activity")) return true;
        Player player = (sender instanceof Player)? (Player)sender : null;
        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Language.PLAYER_COMMAND);
                return true;
            }
            GuiManager.openGui(player);
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")) {
                for(String msg : Language.HELP){ sender.sendMessage(msg.replace("&", "§")); }
            }else if(args[0].equalsIgnoreCase("reload")){
                if(!sender.isOp())return true;
                sender.sendMessage(Language.RELOADING);
                YamlManager.loadConfig();
                GuiManager.iniGuiManager(manager);
                sender.sendMessage(Language.RELOADED);
            }else if(args[0].equalsIgnoreCase("default")){
                if(!sender.isOp())return true;
                YamlManager.upDateConfig();
                sender.sendMessage(Language.RELOADING);
                YamlManager.loadConfig();
                sender.sendMessage(Language.RELOADED);
            }else if(args[0].equalsIgnoreCase("test")){
                if(player == null) { sender.sendMessage(Language.PLAYER_COMMAND); return true; }
            }else if(args[0].equalsIgnoreCase("rank")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Map<Double, String> rank = new TreeMap<>();
                        sender.sendMessage(Language.ACTIVITY_RANK);
                        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()){
                            String name = offlinePlayer.getName();
                            int day = ActivityTime.getDay();
                            if(!manager.containsDay(name, day))continue;
                            double add = ((double)Math.random())/1000;
                            rank.put(manager.getActivity(name, day)*-1-add, name);
                        }
                        int i = 0;
                        for(Map.Entry<Double, String> entry : rank.entrySet()) {
                            i++;
                            sender.sendMessage("[§6"+i+"§r]§3"+entry.getValue()+"§7 ▶ §r"+entry.getKey().intValue()*-1);
                        }
                        cancel();
                    }
                }.runTask(Activity.getProvidingPlugin(Activity.class));
            }else if(args[0].equalsIgnoreCase("playtime")){
                if(player == null) { sender.sendMessage(Language.PLAYER_COMMAND); return true; }
                player.openInventory(GuiManager.PLAYTIME);
            }else if(args[0].equalsIgnoreCase("task")){
                if(player == null) { sender.sendMessage(Language.PLAYER_COMMAND); return true; }
                GuiManager.openTaskGui(player);
            }
        }else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add") && sender.hasPermission("activity.add")) {
                manager.addChance(args[1], 1);
                sender.sendMessage(Language.ADD_CHANCE.replace("%player%", args[1]).replace("%amount%", "1"));
            }else if(args[0].equalsIgnoreCase("data")){
                if(player == null) { sender.sendMessage(Language.PLAYER_COMMAND); return true; }
                player.sendMessage(Language.DAY_ACTIVITY
                        .replace("%days%", manager.getDayList(args[1]).size()+"")
                        .replace("%activity%", manager.getTotalActivity(args[1])+"")
                        .replace("%player%", args[1])
                );
                TreeSet<String>days = new TreeSet<>(manager.getDayList(args[1]));
                for(String day: days){
                    int activity = manager.getActivity(args[1], Integer.parseInt(day));
                    ActivityChat.sendDayActivity(
                            player,
                            day,
                            ActivityConvert.convertBar(activity),
                            "活跃度: "+activity + "  在线: " + ActivityConvert.convertTime(manager.getOnlineTime(args[1], Integer.parseInt(day)))
                    );
                }
            }
        }else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("add") && sender.hasPermission("activity.add")) {
                manager.addChance(args[1], Integer.parseInt(args[2]));
                sender.sendMessage(Language.ADD_CHANCE.replace("%player%", args[1]).replace("%amount%", args[2]));
            }
        }else sender.sendMessage("正确用法§3" + cmd.getUsage());
        return true;
    }
}
