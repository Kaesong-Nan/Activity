package com.fireflyest.activity.event;

import com.fireflyest.activity.data.YamlManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener  implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        YamlManager.setup("PlayerData", event.getPlayer().getName());
    }

}
