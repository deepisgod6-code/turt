package com.deepisgod6.turtlemaster;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final JavaPlugin plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private int cooldownDuration;
    private boolean enabled;

    public CooldownManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        this.cooldownDuration = plugin.getConfig().getInt("cooldown-duration", 60);
        this.enabled = plugin.getConfig().getBoolean("enabled", true);
    }

    /**
     * Check if a player can drink a Turtle Master potion
     * @param player The player to check
     * @return true if the player can drink, false if on cooldown
     */
    public boolean canDrinkTurtleMaster(Player player) {
        if (!enabled) {
            return true;
        }

        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) {
            return true;
        }

        long expirationTime = cooldowns.get(uuid);
        if (System.currentTimeMillis() >= expirationTime) {
            cooldowns.remove(uuid);
            return true;
        }

        return false;
    }

    /**
     * Get remaining cooldown time in seconds
     * @param player The player to check
     * @return Remaining cooldown in seconds, or 0 if no cooldown
     */
    public long getRemainingCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) {
            return 0;
        }

        long expirationTime = cooldowns.get(uuid);
        long remaining = (expirationTime - System.currentTimeMillis()) / 1000;

        if (remaining <= 0) {
            cooldowns.remove(uuid);
            return 0;
        }

        return remaining;
    }

    /**
     * Apply cooldown to a player after drinking Turtle Master
     * @param player The player to apply cooldown to
     */
    public void applyCooldown(Player player) {
        if (!enabled) {
            return;
        }

        UUID uuid = player.getUniqueId();
        long expirationTime = System.currentTimeMillis() + (cooldownDuration * 1000L);
        cooldowns.put(uuid, expirationTime);
    }

    /**
     * Set custom cooldown for a player
     * @param player The player
     * @param seconds Duration in seconds
     */
    public void setCooldown(Player player, int seconds) {
        UUID uuid = player.getUniqueId();
        long expirationTime = System.currentTimeMillis() + (seconds * 1000L);
        cooldowns.put(uuid, expirationTime);
    }

    /**
     * Clear cooldown for a player
     * @param player The player
     */
    public void clearCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
    }

    /**
     * Get current cooldown duration setting
     * @return Cooldown duration in seconds
     */
    public int getCooldownDuration() {
        return cooldownDuration;
    }

    /**
     * Check if feature is enabled
     * @return true if enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Reload cooldown settings from config
     */
    public void reload() {
        plugin.reloadConfig();
        loadConfig();
    }

    /**
     * Save cooldowns (placeholder for future database integration)
     */
    public void saveCooldowns() {
        // Could be extended to save to a database or file
    }

    /**
     * Get number of players on cooldown
     * @return Number of players
     */
    public int getPlayersOnCooldown() {
        return cooldowns.size();
    }
}