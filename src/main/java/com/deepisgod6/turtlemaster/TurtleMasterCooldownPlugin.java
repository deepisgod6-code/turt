package com.deepisgod6.turtlemaster;

import org.bukkit.plugin.java.JavaPlugin;
import com.deepisgod6.turtlemaster.listeners.PotionDrinkListener;
import com.deepisgod6.turtlemaster.commands.CooldownCommand;

public class TurtleMasterCooldownPlugin extends JavaPlugin {

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        // Initialize config
        saveDefaultConfig();
        reloadConfig();

        // Initialize cooldown manager
        this.cooldownManager = new CooldownManager(this);

        // Register listeners
        getServer().getPluginManager().registerEvents(
                new PotionDrinkListener(this, cooldownManager),
                this
        );

        // Register commands
        getCommand("tmcooldown").setExecutor(new CooldownCommand(this, cooldownManager));

        getLogger().info("TurtleMaster Cooldown plugin enabled!");
    }

    @Override
    public void onDisable() {
        if (cooldownManager != null) {
            cooldownManager.saveCooldowns();
        }
        getLogger().info("TurtleMaster Cooldown plugin disabled!");
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}