package com.fireflyest.activity.board;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * @author Fireflyest
 */
public class ScriptBoard {

    private final static ScoreboardManager MANAGER = Bukkit.getScoreboardManager();

    private ScriptBoard(){

    }

    /**
     * 给玩家添加一个积分榜
     * @param player 玩家
     * @param title 积分榜标题
     */
    public static void addScoreboard(Player player, String title) {
        Objective obj = MANAGER.getNewScoreboard().registerNewObjective(player.getName(), "", title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(obj.getScoreboard());
    }

    /**
     * 设置玩家积分榜标题
     * @param player 玩家
     * @param title 积分榜标题
     */
    public static void setBoardTitle(Player player, String title) {
        Objective obj = player.getScoreboard().getObjective(player.getName());
        obj.setDisplayName(title);
    }

    /**
     * 设置玩家积分榜某一条目
     * @param player 玩家
     * @param key 键
     * @param score 值
     */
    public static void setScoreboardValue(Player player, String key, int score) {
        Objective obj = player.getScoreboard().getObjective(player.getName());
        player.getScoreboard().resetScores(key);
        obj.getScore(key).setScore(score);
    }

    /**
     * 删除玩家积分榜某一条目
     * @param player 玩家
     * @param key 键
     */
    public static void removeScoreboardValue(Player player, String key){
        player.getScoreboard().resetScores(key);
    }

    /**
     * 清理一个玩家的积分榜
     * @param player 玩家
     */
    public static void clearScoreboard(Player player) {
        player.setScoreboard(MANAGER.getNewScoreboard());
    }



}
