package com.fireflyest.activity.manager;

import com.fireflyest.activity.Activity;
import com.fireflyest.activity.convert.ActivityConvert;
import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.item.ActivityItem;
import com.fireflyest.activity.time.ActivityTime;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GuiManager {

    //菜单列表
    private static Map<String, Inventory>guis = new HashMap<>();
    private static Map<String, Inventory>tasks = new HashMap<>();

    //物品列表
    private static Map<String, ItemStack>items = new HashMap<>();

    //物品注释
    private static List<String> lores = new ArrayList<>();

    //石头
    private static ItemStack stone = new ItemStack(Material.STONE);

    public static Inventory ACTIVITY = Bukkit.createInventory(null, 54, Language.PLUGIN_NAME+ " §9活跃奖励");
    public static Inventory SIGN_IN = Bukkit.createInventory(null, 54, Language.PLUGIN_NAME+ " §9签到奖励");
    public static Inventory PLAYTIME = Bukkit.createInventory(null, 54, Language.PLUGIN_NAME+ " §9在线奖励");

    private static DataManager manager;

    private GuiManager(){
    }


    /**
     * 初始化物品
     */
    public static void iniGuiManager(DataManager dataManager){

        manager = dataManager;

        FileConfiguration config = Config.getConfig();

        //加载配置物品
        for(String name : config.getConfigurationSection("Item").getKeys(false)) {
            //获取材料类型
            Material material = Material.matchMaterial(config.getString("Item."+name+".material", "STONE"));
            if(material == null)material = Material.STONE;
            //创建物品
            ItemStack stack = new ItemStack(material);
            ActivityItem.addItemFlags(stack);
            //设置参数
            lores.clear();
            for(String lore : config.getStringList("Item."+name+".lore")) lores.add(lore.replace("&", "§"));
            ActivityItem.setLores(stack, config.getString("Item."+name+".display").replace("&", "§"),  lores);
            //储存
            items.put(name, stack);
        }

        //加载各界面的基础框架
        for(int i = 45 ; i <= 53 ; i++) { ACTIVITY.setItem(i, items.getOrDefault("Blank", stone)); }
        for(int i = 45 ; i <= 53 ; i++) { SIGN_IN.setItem(i, items.getOrDefault("Blank", stone)); }
        for(int i = 45 ; i <= 53 ; i++) { PLAYTIME.setItem(i, items.getOrDefault("Blank", stone)); }
        ACTIVITY.setItem(49, items.getOrDefault("Back", stone));
        SIGN_IN.setItem(49, items.getOrDefault("Back", stone));
        PLAYTIME.setItem(49, items.getOrDefault("Back", stone));
        for(String name : Config.ACTIVITY){
            ItemStack item = items.get("A_Reward");
            item.setType(Material.matchMaterial(Config.getActivityString(name+".Item")));
            ActivityItem.setDisplayName(item, "§e§l"+name);
            ActivityItem.setItemValue(item, 1, Config.getActivityInt(name+".Price"));
            ACTIVITY.setItem(Config.getActivityInt(name+".Loc"), item);
        }
        for(String name : Config.SIGN_IN){
            ItemStack item = items.get("S_Reward");
            item.setType(Material.matchMaterial(Config.getSigninString(name+".Item")));
            ActivityItem.setDisplayName(item, "§e§l"+name);
            ActivityItem.setItemValue(item, 1, Config.getSigninString(name+".Type"));
            ActivityItem.setItemValue(item, 2, Config.getSigninInt(name+".Amount"));
            SIGN_IN.setItem(Config.getSigninInt(name+".Loc"), item);
        }
        for(String name : Config.PLAYTIME){
            ItemStack item = items.get("P_Reward");
            item.setType(Material.matchMaterial(Config.getPlaytimeString(name+".Item")));
            ActivityItem.setDisplayName(item, "§e§l"+name);
            ActivityItem.setItemValue(item, 1, Config.getPlaytimeString(name+".Type"));
            ActivityItem.setItemValue(item, 2, Config.getPlaytimeString(name+".Time"));
            PLAYTIME.setItem(Config.getPlaytimeInt(name+".Loc"), item);
        }
    }

    /**
     * 获取一个新的界面
     * @return 新容器
     */
    public static Inventory getNewGui(){
        return getNewGui(Bukkit.createInventory(null, 54, Language.PLUGIN_NAME + " §9签到界面"));
    }

    public static Inventory getNewGui(Inventory old){
        Inventory gui = old;
        gui.clear();
        for(int i = 7 ; i <= 53 ; i+=9) { gui.setItem(i, items.getOrDefault("Blank", stone)); }
        gui.setItem(44, items.getOrDefault("Blank", stone));
        gui.setItem(53, items.getOrDefault("Close", stone));
        return gui;
    }

    /**
     * 初始化玩家菜单
     * @param player 玩家
     */
    public static void updatePlayerGui(Player player){
        String name = player.getName();

        Inventory gui = guis.containsKey(name) ? getNewGui(guis.get(name)):getNewGui();

        //侧边按钮
        ItemStack item;
        //活跃
        item = items.getOrDefault("Activity", stone).clone();
        if(Config.HEAD_SKIN){ ActivityItem.createSkull(item, player); }
        ActivityItem.setItemValue(item, 1, manager.getActivity(name, ActivityTime.getDay()));
        ActivityItem.setItemValue(item, 2, manager.getTotalActivity(name));
        ActivityItem.setItemValue(item, 3, manager.getLastMonthActivity(name));
        gui.setItem(8, item);
        //签到
        item = items.getOrDefault("Signin", stone).clone();
        ActivityItem.setItemValue(item, 1, manager.getChance(name));
        ActivityItem.setItemValue(item, 2, manager.getAddUp(name));
        ActivityItem.setItemValue(item, 3, manager.getSeries(name));
        gui.setItem(17, item);
        //在线
        item = items.getOrDefault("Playtime", stone).clone();
        ActivityItem.setItemValue(item, 1, ActivityConvert.convertTime(manager.getTotalTime(name) + OnlineManager.getPlaytime(name)));
        ActivityItem.setItemValue(item, 2, ActivityConvert.convertTime(manager.getOnlineTime(name, ActivityTime.getDay()) + OnlineManager.getPlaytime(name)));
        ActivityItem.setItemValue(item, 3, ActivityConvert.convertTime(OnlineManager.getPlaytime(name)));
        gui.setItem(26, item);
        //任务
        item = items.getOrDefault("Task", stone).clone();
        ActivityItem.setItemValue(item, 1, manager.getTaskList(name).size());
        ActivityItem.setItemValue(item, 2, manager.getDidTask(name));
        gui.setItem(35, item);


        //日历内容
        for(int i = 0; i < ActivityTime.getFirstDay() -1 ; i++) { gui.setItem(i, items.getOrDefault("Blank", stone)); }
        int day = ActivityTime.getDay();
        Set<Integer>festivals = new HashSet<>();
        for(String festival:Config.FESTIVAL){
            if(Config.getFestivalInt(festival+".Month") == ActivityTime.getMonth()){
                festivals.add(Config.getFestivalInt(festival+".Day"));
            }
        }
        for(int i = 1 ; i <= ActivityTime.getMaxDay() ; i++) {
            if(manager.containsDay(name, i)) {//已签
                addItem(gui, "Finish", i);
            }else if(festivals.contains(i)){//节日
                for(String festival : Config.FESTIVAL) {
                    if(Config.getFestivalInt(festival+".Month")!=ActivityTime.getMonth())continue;
                    if(Config.getFestivalInt(festival+".Day")!=i)continue;
                    item = items.getOrDefault("Festival", stone);
                    item.setType(Material.matchMaterial(Config.getFestivalString(festival+".Item")));
                    ActivityItem.setDisplayName(item, "§f[§b§l"+festival+"§f]");
                    break;
                }
                addItem(gui, "Festival", i);
            }else if(i < day) {//过去
                addItem(gui, "Miss", i);
            }else if(i > day) {//未来
                addItem(gui, "Future", i);
            }else { //当天
                addItem(gui, "Now", i);
            }
        }
        for(int i = 0 ; i < ActivityTime.getFirstDay()-1 ; i++) { gui.clear(i); }

        //保存菜单
        guis.put(name, gui);
    }

    public static void updateTaskGui(Player player){
        String name = player.getName();

    Inventory gui = tasks.containsKey(name) ? getNewGui(tasks.get(name)):Bukkit.createInventory(null, 54, Language.PLUGIN_NAME+ " §9任务列表");

        gui.clear();

        //框架
        for(int i = 45 ; i <= 53 ; i++) { gui.setItem(i, items.getOrDefault("Blank", stone)); }
        gui.setItem(49, items.getOrDefault("Back", stone));

        //任务书
        ItemStack item;
        for(String task : Config.TASK){
            item = items.get("Taskbook").clone();
            //判断是否接受，未接受的判断是否每日任务
            if(manager.getTaskList(name).contains(task)){
                item.setType(Material.matchMaterial(Config.getTaskString(task+".Item")));
                ActivityItem.setDisplayName(item, "§e§l"+task);
                ActivityItem.setItemValue(item, 1, Config.getTaskString(task+".Work"));
                ActivityItem.setItemValue(item, 2, Config.getTaskString(task+".Target"));
                String progress = manager.getTask(name, task).split(",")[2];
                //判断任务进度
                if("did".equalsIgnoreCase(progress)){
                    //完成可领取
                    item.setType(Material.ENCHANTED_BOOK);
                    gui.addItem(item);
                }else if(!"finish".equalsIgnoreCase(progress)){
                    //未完成
                    ActivityItem.setItemValue(item, 3, progress+"/"+Config.getTaskInt(task+".Progress"));
                    //任务进度条
                    if(Config.TASK_PROGRESS && !"1".equals(progress)){
                        int p = Integer.parseInt(progress), t=Config.getTaskInt(task+".Progress")-p;
                        ActivityItem.setLore(item, 5, "§r"+ActivityConvert.convertBar(p)+"§7"+ActivityConvert.convertBar(t));
                    }
                    gui.addItem(item);
                }
            }else if("daily".equalsIgnoreCase(Config.getTaskString(task+".Type"))){
                ActivityItem.setDisplayName(item, "§e§l"+task);
                ActivityItem.setItemValue(item, 1, Config.getTaskString(task+".Work"));
                ActivityItem.setItemValue(item, 2, Config.getTaskString(task+".Target"));
                ActivityItem.setItemValue(item, 3, Config.getTaskString(task+".Progress"));
                gui.addItem(item);
            }
        }

        //保存菜单
        tasks.put(name, gui);
    }

    /**
     * 添加日历物品
     * @param gui 界面
     * @param type 类型
     * @param day 日期
     */
    private static void addItem(Inventory gui, String type, int day) {
        ItemStack item = items.getOrDefault(type, stone).clone();
        //判断是不是周末
        if(((day-1+ActivityTime.getFirstDay())%7 == 0 || (day+5+ActivityTime.getFirstDay())%7 == 0) && (type.equals("Miss") || type.equals("Future"))) {
            Material weekend = Material.matchMaterial(Config.WEEKEND_ITEM);
            item.setType(weekend==null?Material.STONE:weekend);
        }
        //设置参数
        ActivityItem.setDisplayName(item, "§r"+item.getItemMeta().getDisplayName()+"§0 "+day);
        ActivityItem.setLore(item, 2, Language.DISPLAY_DAY.replace("%month%", ActivityTime.getMonth()+"").replace("%day%", day+""));
        gui.addItem(item);
    }

    /**
     * 玩家打开菜单
     * @param player 玩家
     */
    public static void openGui(Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                updatePlayerGui(player);
                player.openInventory(guis.get(player.getName()));
                cancel();
            }
        }.runTask(Activity.getProvidingPlugin(Activity.class));
    }

    public static void openTaskGui(Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                updateTaskGui(player);
                player.openInventory(tasks.get(player.getName()));
                cancel();
            }
        }.runTask(Activity.getProvidingPlugin(Activity.class));
    }

}
