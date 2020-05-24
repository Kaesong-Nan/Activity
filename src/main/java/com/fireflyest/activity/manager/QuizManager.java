package com.fireflyest.activity.manager;

import com.fireflyest.activity.chat.ActivityChat;
import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.time.ActivityTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class QuizManager {

    private static DataManager manager;

    private static boolean finish = false;

    private static String right = null;

    private static Set<String> selected = new HashSet<>();

    public static void iniQuizManager(DataManager dataManager){
        manager = dataManager;
    }

    private QuizManager(){

    }

    public static void sendSingleQuiz(String quiz, String... option){
        finish = false;
        Bukkit.broadcastMessage(Language.TITLE + quiz);
        for(Player player : Bukkit.getOnlinePlayers()){
            char i = 'A';
            for(String o : option){
                //设置正确答案，广播选项
                if(right == null){
                    right = o;
                }else {
                    ActivityChat.sendQuizOption(player, i, o);
                    i++;
                }
            }
        }
    }

    public static void selectOption(Player player, String option){
        String name = player.getName();
        //判断是否选过
        if(selected.contains(name)){
            player.sendMessage(Language.SELECTED);
            return;
        }else {
            selected.add(name);
        }
        Bukkit.broadcastMessage(Language.SELECT_OPTION.replace("%player%", name).replace("%option%", option));
        //判断是否正确
        if(option.equalsIgnoreCase(right)){
            player.sendMessage(Language.RIGHT_OPTION);
        }else {
            player.sendMessage(Language.FALSE_OPTION);
            return;
        }
        //判断是否被抢答
        if(finish)return;

        finish = true;
        right = null;
        selected.clear();
        Bukkit.broadcastMessage(Language.WIN_PLAYER.replace("%player%", name).replace("%activity%", Config.QUIZ_ACTIVITY+""));
        manager.addActivity(name, ActivityTime.getDay(), Config.QUIZ_ACTIVITY);
    }

}
