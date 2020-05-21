package com.fireflyest.activity.event;

import com.fireflyest.activity.data.YamlManager;
import com.fireflyest.activity.manager.DataManager;
import com.fireflyest.activity.manager.GuiManager;
import com.fireflyest.activity.manager.OnlineManager;
import com.fireflyest.activity.manager.YmlManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {

    private static DataManager manager;
    private static OnlineManager onlineManager;

    public PlayerEventListener(){
        manager = new YmlManager();
        onlineManager = new OnlineManager(manager);
        GuiManager.iniGuiManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        YamlManager.setup("PlayerData", event.getPlayer().getName());
        onlineManager.playerJoin(event.getPlayer().getName());
    }

}
