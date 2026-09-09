package com.requiem.shield.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.requiem.shield.RequiemShield;

public class CommandHandler implements CommandExecutor {

    private final RequiemShield plugin;

    public CommandHandler(RequiemShield plugin) {
        this.plugin = plugin;
        plugin.getCommand("rtzcommandtoggle").setExecutor(this);
        plugin.getCommand("rtz1").setExecutor(this);
        plugin.getCommand("rtz2").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("rtzcommandtoggle")) {
            return handleCommandToggle(player);
        } else if (command.getName().equalsIgnoreCase("rtz1")) {
            return handleSoulSplit(player);
        } else if (command.getName().equalsIgnoreCase("rtz2")) {
            return handleReturnToZero(player);
        }

        return false;
    }

    /**
     * Toggles command mode on/off
     */
    private boolean handleCommandToggle(Player player) {
        // Implementation for toggling command mode
        player.sendMessage("✦ Command mode toggled.");
        return true;
    }

    /**
     * Soul Split command
     */
    private boolean handleSoulSplit(Player player) {
        if (!plugin.getCooldownManager().canUse(player, "soul_split")) {
            plugin.getCooldownManager().sendCooldownMessage(player, "soul_split");
            return true;
        }

        // TODO: Call ability activation
        player.sendMessage("✦ Soul Split command activated.");
        return true;
    }

    /**
     * Return to Zero command
     */
    private boolean handleReturnToZero(Player player) {
        if (!plugin.getCooldownManager().canUse(player, "return_to_zero")) {
            plugin.getCooldownManager().sendCooldownMessage(player, "return_to_zero");
            return true;
        }

        // TODO: Call ability activation
        player.sendMessage("✦ Return to Zero command activated.");
        return true;
    }
}
