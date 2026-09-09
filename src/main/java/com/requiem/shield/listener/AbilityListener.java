package com.requiem.shield.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import com.requiem.shield.RequiemShield;

public class AbilityListener implements Listener {

    private final RequiemShield plugin;

    public AbilityListener(RequiemShield plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Initialize player data on join if needed
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Clear mark if marked player quits
        if (plugin.getMarkManager().isMarked(player)) {
            player.setGlowing(false);
            plugin.getMarkManager().clearMark(player);
        }
    }
}
