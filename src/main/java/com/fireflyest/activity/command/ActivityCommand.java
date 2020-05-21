package com.fireflyest.activity.command;

import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.data.YamlManager;
import com.fireflyest.activity.manager.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActivityCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("activity")) return true;
        Player player = (sender instanceof Player)? (Player)sender : null;
        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(Language.PLAYER_COMMAND);
                return true;
            }
            player.openInventory(GuiManager.getGui(player.getName()));
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")) {
                for(String msg : Language.HELP){ sender.sendMessage(msg.replace("&", "§")); }
            }else if(args[0].equalsIgnoreCase("reload")){
                if(!sender.isOp())return true;
                sender.sendMessage(Language.RELOADING);
                YamlManager.loadConfig();
                sender.sendMessage(Language.RELOADED);
            }else if(args[0].equalsIgnoreCase("default")){
                if(!sender.isOp())return true;
                YamlManager.upDateConfig();
                sender.sendMessage(Language.RELOADING);
                YamlManager.loadConfig();
                sender.sendMessage(Language.RELOADED);
            }else if(args[0].equalsIgnoreCase("test")){
                if(player == null) { sender.sendMessage(Language.PLAYER_COMMAND); return true; }
            }
        }else sender.sendMessage("正确用法§3" + cmd.getUsage());
        return true;
    }
}
