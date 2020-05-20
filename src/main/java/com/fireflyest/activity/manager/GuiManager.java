package com.fireflyest.activity.manager;

import com.fireflyest.activity.data.Config;
import com.fireflyest.activity.item.ActivityItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiManager {

    //默认箱子
    private static Inventory gui = Bukkit.createInventory(null, 54, "");

    //物品列表
    private static Map<String, ItemStack>items = new HashMap<>();

    //物品注释
    private static List<String> lores = new ArrayList<>();;

    public GuiManager(){
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

        //设置默认箱子

    }

}
