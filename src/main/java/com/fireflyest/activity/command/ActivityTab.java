package com.fireflyest.activity.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ActivityTab implements TabCompleter {

    private static List<String> list = new ArrayList<>();

    public ActivityTab(){
        list.add("reload");
        list.add("default");
        list.add("help");
        list.add("add");
        list.add("rank");
        list.add("task");
        list.add("playtime");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(command.getName().equalsIgnoreCase("activity")){
            List<String> tab = new ArrayList<>();
            if(args.length == 1){
                for(String sub : list){
                    if(sub.contains(args[0]))tab.add(sub);
                }
            }
            return tab;
        }
        return null;
    }

}
