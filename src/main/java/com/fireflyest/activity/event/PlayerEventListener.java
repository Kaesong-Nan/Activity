package com.fireflyest.activity.event;

import com.fireflyest.activity.data.Language;
import com.fireflyest.activity.data.YamlManager;
import com.fireflyest.activity.manager.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerLoadEvent;

public class PlayerEventListener implements Listener {

    private static DataManager manager;

    public PlayerEventListener(){
        manager = new YmlManager();
        OnlineManager.iniOnlineManager(manager);
        GuiManager.iniGuiManager(manager);
        ClickManager.iniClickManager(manager);
        RewardManager.iniRewardManager(manager);
        TaskManager.iniTaskManager(manager);
        QuizManager.iniQuizManager(manager);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        YamlManager.setup("PlayerData", event.getPlayer().getName());
        OnlineManager.playerJoin(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        Player player = (Player)event.getWhoClicked();
        if(!event.getView().getTitle().contains(Language.PLUGIN_NAME)) return;
        event.setCancelled(true);
        ClickManager.playerClick(event.getCurrentItem(), player);
    }

    @EventHandler
    public void onServerLoadEvent(ServerLoadEvent event) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            OnlineManager.playerJoin(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        OnlineManager.savePlayerTime(event.getPlayer().getName());
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if(!event.getPlugin().getName().equals("Activity"))return;
        for(Player player : Bukkit.getOnlinePlayers()) {
            OnlineManager.savePlayerTime(player.getName());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        TaskManager.doTask(player, event.getBlock().getType().name(), "⚒");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if(!event.getRightClicked().getType().isAlive())return;
        TaskManager.doTask(event.getPlayer(), event.getRightClicked().getName(), "☻");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!event.getDamager().getType().name().equals("PLAYER"))return;
        if(!event.getEntity().getType().isAlive())return;
        LivingEntity entity = (LivingEntity)event.getEntity();
        if(entity.getHealth() - event.getDamage() > 0)return;
        TaskManager.doTask((Player)event.getDamager(), entity.getType().name().toUpperCase(), "⚔");
    }

}
