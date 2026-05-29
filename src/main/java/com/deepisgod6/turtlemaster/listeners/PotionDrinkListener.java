package com.deepisgod6.turtlemaster.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.deepisgod6.turtlemaster.CooldownManager;
import com.deepisgod6.turtlemaster.TurtleMasterCooldownPlugin;
import com.deepisgod6.turtlemaster.utils.MessageUtil;

public class PotionDrinkListener implements Listener {

    private final TurtleMasterCooldownPlugin plugin;
    private final CooldownManager cooldownManager;

    public PotionDrinkListener(TurtleMasterCooldownPlugin plugin, CooldownManager cooldownManager) {
        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPotionDrink(PlayerItemConsumeEvent event) {
        if (!cooldownManager.isEnabled()) {
            return;
        }

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        // Check if the item has potion meta
        if (!(item.getItemMeta() instanceof PotionMeta potionMeta)) {
            return;
        }

        // Check if this is a Turtle Master potion
        if (!isTurtleMasterPotion(potionMeta)) {
            return;
        }

        // Check if player is on cooldown
        if (!cooldownManager.canDrinkTurtleMaster(player)) {
            event.setCancelled(true);
            long remainingSeconds = cooldownManager.getRemainingCooldown(player);
            String message = MessageUtil.formatMessage(
                    plugin.getConfig().getString("messages.cooldown-active"),
                    "{remaining}", String.valueOf(remainingSeconds)
            );
            player.sendMessage(MessageUtil.addPrefix(plugin, message));
            return;
        }

        // Apply cooldown
        cooldownManager.applyCooldown(player);

        if (plugin.getConfig().getBoolean("send-notifications", true)) {
            String message = MessageUtil.formatMessage(
                    plugin.getConfig().getString("messages.cooldown-expired"),
                    null, null
            );
            // Notify player after a short delay (potion effects are applied after)
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (player.isOnline()) {
                    player.sendMessage(MessageUtil.addPrefix(plugin, message));
                }
            }, 5L);
        }
    }

    /**
     * Check if a potion is a Turtle Master potion
     * @param potionMeta The potion metadata
     * @return true if this is a Turtle Master potion
     */
    private boolean isTurtleMasterPotion(PotionMeta potionMeta) {
        for (PotionEffect effect : potionMeta.getCustomEffects()) {
            if (effect.getType().equals(PotionEffectType.RESISTANCE)) {
                return true;
            }
        }
        return false;
    }
}