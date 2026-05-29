package com.deepisgod6.turtlemaster.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.deepisgod6.turtlemaster.CooldownManager;
import com.deepisgod6.turtlemaster.TurtleMasterCooldownPlugin;
import com.deepisgod6.turtlemaster.utils.MessageUtil;

public class CooldownCommand implements CommandExecutor {

    private final TurtleMasterCooldownPlugin plugin;
    private final CooldownManager cooldownManager;

    public CooldownCommand(TurtleMasterCooldownPlugin plugin, CooldownManager cooldownManager) {
        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "reload":
                return handleReload(sender);
            case "status":
                return handleStatus(sender);
            case "set":
                return handleSet(sender, args);
            case "clear":
                return handleClear(sender, args);
            default:
                sendHelpMessage(sender);
                return true;
        }
    }

    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("turtlemaster.admin")) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cYou don't have permission!"));
            return true;
        }

        cooldownManager.reload();
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&aConfiguration reloaded!"));
        return true;
    }

    private boolean handleStatus(CommandSender sender) {
        if (!sender.hasPermission("turtlemaster.admin")) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cYou don't have permission!"));
            return true;
        }

        boolean enabled = cooldownManager.isEnabled();
        int duration = cooldownManager.getCooldownDuration();
        int playersOnCooldown = cooldownManager.getPlayersOnCooldown();

        sender.sendMessage(MessageUtil.addPrefix(plugin, "&b=== Turtle Master Cooldown Status ==="));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&eEnabled: &a" + (enabled ? "Yes" : "No")));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&eCooldown Duration: &a" + duration + "s"));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&aPlayers on Cooldown: &a" + playersOnCooldown));

        return true;
    }

    private boolean handleSet(CommandSender sender, String[] args) {
        if (!sender.hasPermission("turtlemaster.admin")) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cYou don't have permission!"));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cUsage: /tmcooldown set <player> <seconds>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            String message = MessageUtil.formatMessage(
                    plugin.getConfig().getString("messages.not-found"),
                    "{player}", args[1]
            );
            sender.sendMessage(MessageUtil.addPrefix(plugin, message));
            return true;
        }

        try {
            int seconds = Integer.parseInt(args[2]);
            if (seconds < 0) {
                sender.sendMessage(MessageUtil.addPrefix(plugin, "&cSeconds must be positive!"));
                return true;
            }

            cooldownManager.setCooldown(target, seconds);
            String message = MessageUtil.formatMessage(
                    plugin.getConfig().getString("messages.cooldown-set"),
                    "{player}", target.getName()
            );
            message = MessageUtil.formatMessage(message, "{seconds}", String.valueOf(seconds));
            sender.sendMessage(MessageUtil.addPrefix(plugin, message));
            return true;
        } catch (NumberFormatException e) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cSeconds must be a valid number!"));
            return true;
        }
    }

    private boolean handleClear(CommandSender sender, String[] args) {
        if (!sender.hasPermission("turtlemaster.admin")) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cYou don't have permission!"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(MessageUtil.addPrefix(plugin, "&cUsage: /tmcooldown clear <player>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            String message = MessageUtil.formatMessage(
                    plugin.getConfig().getString("messages.not-found"),
                    "{player}", args[1]
            );
            sender.sendMessage(MessageUtil.addPrefix(plugin, message));
            return true;
        }

        cooldownManager.clearCooldown(target);
        String message = MessageUtil.formatMessage(
                plugin.getConfig().getString("messages.cooldown-cleared"),
                "{player}", target.getName()
        );
        sender.sendMessage(MessageUtil.addPrefix(plugin, message));
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&b=== Turtle Master Cooldown Commands ==="));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&e/tmcooldown reload &8- Reload configuration"));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&e/tmcooldown status &8- Show current status"));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&e/tmcooldown set <player> <seconds> &8- Set cooldown for player"));
        sender.sendMessage(MessageUtil.addPrefix(plugin, "&e/tmcooldown clear <player> &8- Clear cooldown for player"));
    }
}