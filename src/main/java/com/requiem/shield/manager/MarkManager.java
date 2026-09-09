package com.requiem.shield.manager;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class MarkManager {

    private final JavaPlugin plugin;
    private final Map<Player, MarkData> marks = new HashMap<>();

    public MarkManager(JavaPlugin plugin) {
        this.plugin = plugin;
        
        // Cleanup expired marks every 5 ticks
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::cleanupExpiredMarks, 5L, 5L);
    }

    /**
     * Marks a player for 10 seconds
     */
    public void markPlayer(Player target) {
        // Remove existing mark if any
        marks.remove(target);
        
        // Create new mark
        long expiryTime = System.currentTimeMillis() + 10000;
        marks.put(target, new MarkData(expiryTime));
    }

    /**
     * Gets the marked player, if any
     */
    public Player getMarkedPlayer() {
        for (Map.Entry<Player, MarkData> entry : marks.entrySet()) {
            if (!entry.getKey().isOnline()) {
                marks.remove(entry.getKey());
                continue;
            }
            if (!isMarked(entry.getKey())) {
                marks.remove(entry.getKey());
                continue;
            }
            return entry.getKey();
        }
        return null;
    }

    /**
     * Checks if a player is marked
     */
    public boolean isMarked(Player player) {
        MarkData data = marks.get(player);
        if (data == null) {
            return false;
        }
        
        if (System.currentTimeMillis() > data.expiryTime) {
            marks.remove(player);
            return false;
        }
        
        return true;
    }

    /**
     * Gets remaining mark time in seconds
     */
    public long getRemainingMarkTime(Player player) {
        MarkData data = marks.get(player);
        if (data == null) return 0;
        
        long remaining = data.expiryTime - System.currentTimeMillis();
        return Math.max(0, remaining / 1000);
    }

    /**
     * Clears a player's mark
     */
    public void clearMark(Player player) {
        marks.remove(player);
    }

    /**
     * Cleans up expired marks
     */
    private void cleanupExpiredMarks() {
        marks.entrySet().removeIf(entry -> {
            Player player = entry.getKey();
            MarkData data = entry.getValue();
            
            if (!player.isOnline() || System.currentTimeMillis() > data.expiryTime) {
                return true;
            }
            
            return false;
        });
    }

    /**
     * Cleanup on plugin disable
     */
    public void cleanup() {
        marks.clear();
    }

    /**
     * Inner class to store mark data
     */
    private static class MarkData {
        long expiryTime;

        MarkData(long expiryTime) {
            this.expiryTime = expiryTime;
        }
    }
}
