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
        list.add("data");
        list.add("quiz");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(command.getName().equalsIgnoreCase("activity")){
            List<String> tab = new ArrayList<>();
            if(args.length == 1){
                for(String sub : list){
                    if(sub.contains(args[0]))tab.add(sub);
                }
            }else if(args.length == 2){
                if("add".equalsIgnoreCase(args[0])){
                    return null;
                }else if("data".equalsIgnoreCase(args[0])){
                    return null;
                }else if("quiz".equalsIgnoreCase(args[0])){
                    tab.add("[问题]");
                }
            }else if(args.length == 3){
                if("add".equalsIgnoreCase(args[0])){
                    tab.add("<数量>");
                    tab.add("1");
                    tab.add("2");
                }else if("quiz".equalsIgnoreCase(args[0])){
                    tab.add("[正确选项]");
                    tab.add("A");
                    tab.add("B");
                    tab.add("C");
                    tab.add("D");
                }
            }else {
                if("quiz".equalsIgnoreCase(args[0])){
                    tab.add("[选项]");
                }
            }
            return tab;
        }
        return null;
    }

}
