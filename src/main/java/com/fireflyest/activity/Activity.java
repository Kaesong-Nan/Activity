package com.fireflyest.activity;

import com.fireflyest.activity.command.ActivityCommand;
import com.fireflyest.activity.data.YamlManager;
import com.fireflyest.activity.event.PlayerEventListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name="Activity", version="2.0.0-SNAPSHOT")
@Author(value = "Fireflyest")
@Command(name = "activity", usage = "/activity <reload|help|default>")
@Command(name = "a")
//@Command(name = "script",
//        desc = "base command",
//        aliases = {"reload", "help", "default"},
//        permission = "test.foo",
//        permissionMessage = "You do not have permission!")
//@Permission(name = "test.foo", desc = "Allows foo command", defaultValue = PermissionDefault.OP)
public class Activity extends JavaPlugin {

    @Override
    public void onEnable() {
        YamlManager.iniYamlManager(this);

        this.getServer().getPluginManager().registerEvents( new PlayerEventListener(), this );

        this.getCommand("activity").setExecutor(new ActivityCommand());
    }

    @Override
    public void onDisable() {

    }

}
