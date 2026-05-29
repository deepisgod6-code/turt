package com.deepisgod6.turtlemaster.utils;

import net.md_5.bungee.api.ChatColor;
import com.deepisgod6.turtlemaster.TurtleMasterCooldownPlugin;

public class MessageUtil {

    /**
     * Translate color codes in a message
     * @param message The message with color codes
     * @return The translated message
     */
    public static String translateColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Add plugin prefix to a message
     * @param plugin The plugin instance
     * @param message The message
     * @return The message with prefix
     */
    public static String addPrefix(TurtleMasterCooldownPlugin plugin, String message) {
        String prefix = plugin.getConfig().getString("messages.prefix", "&b[TurtleMaster]&r ");
        return translateColors(prefix + message);
    }

    /**
     * Format a message by replacing placeholders
     * @param message The message template
     * @param placeholder The placeholder to replace
     * @param replacement The replacement value
     * @return The formatted message
     */
    public static String formatMessage(String message, String placeholder, String replacement) {
        if (placeholder == null || replacement == null) {
            return message;
        }
        return message.replace(placeholder, replacement);
    }
}