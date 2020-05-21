package com.fireflyest.activity.manager;

import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.item.ActivityItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {

    private static Map<String, Inventory>guis = new HashMap<>();

    //物品列表
    private static Map<String, ItemStack>items = new HashMap<>();

    //物品注释
    private static List<String> lores = new ArrayList<>();

    //石头
    private static ItemStack stone = new ItemStack(Material.STONE);

    private GuiManager(){
    }


    /**
     * 初始化物品
     */
    public static void iniGuiManager(){
        FileConfiguration config = Config.getConfig();

        //加载配置物品
        for(String name : config.getConfigurationSection("Item").getKeys(false)) {
            //获取材料类型
            Material material = Material.matchMaterial(config.getString(name+".material", "STONE"));
            if(material == null)material = Material.STONE;
            //创建物品
            ItemStack stack = new ItemStack(material);
            //设置参数
            lores.clear();
            for(String lore : config.getStringList("Item."+name+".lore")) lores.add(lore.replace("&", "§"));
            ActivityItem.setLores(stack, config.getString("Item."+name+".display").replace("&", "§"),  lores);
            //储存
            items.put(name, stack);
        }
    }

    /**
     * 获取一个新的界面
     * @return 新容器
     */
    public static Inventory getNewGui(){
        Inventory gui = Bukkit.createInventory(null, 54, "");
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
        Inventory gui = getNewGui();

        ItemStack item;

        item = items.getOrDefault("Activity", stone);


        guis.put(name, gui);
    }

    /**
     * 获取玩家菜单
     * @param name 游戏名
     * @return 容器
     */
    public static Inventory getGui(String name){
        return guis.get(name);
    }

}
