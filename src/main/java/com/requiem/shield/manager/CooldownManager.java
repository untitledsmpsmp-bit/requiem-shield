package com.requiem.shield.manager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class CooldownManager {

    private final JavaPlugin plugin;
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    private final Map<String, Long> cooldownDurations = new HashMap<>();

    public CooldownManager(JavaPlugin plugin) {
        this.plugin = plugin;
        // Cooldown durations in seconds
        cooldownDurations.put("soul_split", 360L);
        cooldownDurations.put("return_to_zero", 240L);
    }

    /**
     * Starts a cooldown for a player and ability
     */
    public void startCooldown(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>())
                .put(ability, System.currentTimeMillis());
    }

    /**
     * Checks if a player can use an ability
     */
    public boolean canUse(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        Map<String, Long> playerCooldowns = cooldowns.get(uuid);
        
        if (playerCooldowns == null || !playerCooldowns.containsKey(ability)) {
            return true;
        }
        
        Long startTime = playerCooldowns.get(ability);
        Long duration = cooldownDurations.get(ability) * 1000L; // Convert to milliseconds
        
        return System.currentTimeMillis() - startTime >= duration;
    }

    /**
     * Gets remaining cooldown time in seconds
     */
    public long getRemainingCooldown(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        Map<String, Long> playerCooldowns = cooldowns.get(uuid);
        
        if (playerCooldowns == null || !playerCooldowns.containsKey(ability)) {
            return 0;
        }
        
        Long startTime = playerCooldowns.get(ability);
        Long duration = cooldownDurations.get(ability) * 1000L;
        long remaining = duration - (System.currentTimeMillis() - startTime);
        
        return Math.max(0, remaining / 1000);
    }

    /**
     * Sends cooldown message to player
     */
    public void sendCooldownMessage(Player player, String ability) {
        long remaining = getRemainingCooldown(player, ability);
        String abilityName = ability.equals("soul_split") ? "Soul Split" : "Return To Zero";
        player.sendMessage("§7✦ §f" + abilityName + " §7is on cooldown for §f" + remaining + "§7s");
    }

    /**
     * Notifies player when ability is ready (called once per cooldown expiry)
     */
    public void notifyReady(Player player, String ability) {
        String abilityName = ability.equals("soul_split") ? "Soul Split" : "Return To Zero";
        player.sendMessage("§7✦ §f" + abilityName + " §7is now ready!");
    }
}
