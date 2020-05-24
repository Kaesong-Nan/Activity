package com.fireflyest.activity.chat;

import com.fireflyest.activity.Activity;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

/**
 *
 * @author Fireflyest
 * OPEN_URL  在用户的浏览器打开指定地址
 * OPEN_FILE  在用户的电脑打开指定文件
 * RUN_COMMAND  让用户运行指令
 * SUGGEST_COMMAND  在用户的输入框设置文字
 * CHANGE_PAGE  改变书本的页码
 *
 * SHOW_TEXT  显示一个文本
 * SHOW_ACHIEVEMENT  显示一个成就及其介绍
 * SHOW_ITEM  显示一个物品的名字和其他信息
 * SHOW_ENTITY  显示一个实体的名字，ID和其他信息
 *
 */
public class ActivityChat {

    private static final TextComponent LEFT = new TextComponent("[");
    private static final TextComponent RIGHT = new TextComponent("]");
    private static final TextComponent BLANK = new TextComponent("          ");
    private static final TextComponent CONFIRM = new TextComponent("✔确 定");
    private static final TextComponent CANCEL = new TextComponent("✘取 消");
    private static final TextComponent ACCEPT = new TextComponent("✔接 受");
    private static final TextComponent REFUSE = new TextComponent("✘拒 绝");

    private ActivityChat(){

    }

    /**
     * 发送一个可执行指令的按钮文本
     * @param player 玩家
     * @param display 文本
     * @param color 文本颜色
     * @param hover 指令提示
     * @param command 所执行指令
     */
    public static void sendCommandButton(Player player, String display, String color, String hover, String command) {
        player.spigot().sendMessage(new ComponentBuilder(LEFT)
                .append(display).color(ChatColor.valueOf(color))
                .event(new ClickEvent( ClickEvent.Action.RUN_COMMAND, command))
                .event(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()))
                .append(RIGHT).color(ChatColor.WHITE).create());
    }

    public static void sendDayActivity(Player player, String day, String activity, String hover){
        player.spigot().sendMessage(new ComponentBuilder(LEFT)
                .append(day).color(ChatColor.GOLD)
                .append(RIGHT).color(ChatColor.WHITE)
                .append(activity).color(ChatColor.DARK_AQUA)
                .event(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hover).create()))
                .create());
    }

    /**
     * 发送选项按钮,确认或取消
     * @param player 玩家
     * @param command 指令的头部
     */
    public static void sendOptionButton(Player player, String command){
        player.spigot().sendMessage(new ComponentBuilder(LEFT)
                .append(CONFIRM).color(ChatColor.GREEN)
                .event(new ClickEvent( ClickEvent.Action.RUN_COMMAND, command+" confirm"))
                .event(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击确认").create()))
                .append(RIGHT).color(ChatColor.WHITE)
                .append(BLANK)
                .append(LEFT).color(ChatColor.WHITE)
                .append(CANCEL).color(ChatColor.RED)
                .event(new ClickEvent( ClickEvent.Action.RUN_COMMAND, command+" cancel"))
                .event(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击取消").create()))
                .append(RIGHT).color(ChatColor.WHITE).create());
    }

    /**
     * 发送选项按钮,接受或拒绝
     * @param player 玩家
     * @param command 指令头部
     */
    public static void sendApplyButton(Player player, String command){
        player.spigot().sendMessage(new ComponentBuilder(LEFT)
                .append(ACCEPT).color(ChatColor.GREEN)
                .event(new ClickEvent( ClickEvent.Action.RUN_COMMAND, command+" accept"))
                .event(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击接受").create()))
                .append(RIGHT).color(ChatColor.WHITE)
                .append(BLANK)
                .append(LEFT).color(ChatColor.WHITE)
                .append(REFUSE).color(ChatColor.RED)
                .event(new ClickEvent( ClickEvent.Action.RUN_COMMAND, command+" refuse"))
                .event(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("点击拒绝").create()))
                .append(RIGHT).color(ChatColor.WHITE).create());
    }

}
