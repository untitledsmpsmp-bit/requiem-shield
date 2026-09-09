package com.requiem.shield.ability;

import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import com.requiem.shield.RequiemShield;
import com.requiem.shield.util.SoundUtil;
import com.requiem.shield.util.ParticleUtil;

import java.util.*;

public class SoulSplitAbility {

    private final RequiemShield plugin;
    private final Map<UUID, SoulData> activeSouls = new HashMap<>();

    public SoulSplitAbility(RequiemShield plugin) {
        this.plugin = plugin;
    }

    /**
     * Activates Soul Split for a player
     */
    public boolean activate(Player player) {
        Player marked = plugin.getMarkManager().getMarkedPlayer();
        
        // Validate conditions
        if (marked == null) {
            player.sendMessage("§7✦ §cNo marked player exists.");
            return false;
        }
        
        if (!marked.isOnline()) {
            player.sendMessage("§7✦ §cMarked player is offline.");
            return false;
        }
        
        if (!marked.getWorld().equals(player.getWorld())) {
            player.sendMessage("§7✦ §cMarked player is in a different world.");
            return false;
        }
        
        if (marked.getLocation().distance(player.getLocation()) > 20) {
            player.sendMessage("§7✦ §cMarked player is too far away.");
            return false;
        }
        
        // Create zombie body
        Zombie zombie = (Zombie) marked.getWorld().spawnEntity(marked.getLocation(), org.bukkit.entity.EntityType.ZOMBIE);
        zombie.setGlowing(true);
        zombie.setInvulnerable(true);
        zombie.setCanPickupItems(false);
        
        // Store soul data
        UUID markedUUID = marked.getUniqueId();
        SoulData data = new SoulData(zombie, marked.getLocation(), 0);
        activeSouls.put(markedUUID, data);
        
        // Enter spectator mode
        marked.setGameMode(GameMode.SPECTATOR);
        
        // Play effects
        SoundUtil.playSoulSplitSound(marked);
        ParticleUtil.playSoulSplitEffect(marked);
        
        // Send title
        marked.showTitle(org.bukkit.Bukkit.createTitle(
            org.net.minecraft.network.chat.Component.literal("§f§lSOUL SPLIT"),
            org.net.minecraft.network.chat.Component.literal("§7Your body remains behind"),
            10, 70, 20
        ));
        
        player.sendMessage("§7✦ §fSoul Split activated!");
        
        // Schedule timeout and damage tracking
        scheduleTimeout(markedUUID);
        startDamageTracking(markedUUID);
        
        return true;
    }

    /**
     * Reunites a soul with its body
     */
    public void reunite(Player player) {
        UUID uuid = player.getUniqueId();
        SoulData data = activeSouls.remove(uuid);
        
        if (data == null) {
            player.sendMessage("§7✦ §cYou are not in Soul Split.");
            return;
        }
        
        // Apply damage transfer (50% of damage dealt to zombie)
        double damageToApply = data.accumulatedDamage * 0.5;
        if (damageToApply > 0) {
            player.damage(damageToApply);
        }
        
        // Remove zombie
        data.zombie.remove();
        
        // Return to survival
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(data.bodyLocation);
        
        // Play restoration effects
        SoundUtil.playRestorationSound(player);
        ParticleUtil.playRestorationEffect(player);
        
        // Send title
        player.showTitle(org.bukkit.Bukkit.createTitle(
            org.net.minecraft.network.chat.Component.literal("§f§lSOUL RESTORED"),
            org.net.minecraft.network.chat.Component.literal("§7Your existence has returned."),
            10, 70, 20
        ));
    }

    /**
     * Schedules automatic timeout after 8 seconds
     */
    private void scheduleTimeout(UUID uuid) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            SoulData data = activeSouls.get(uuid);
            if (data != null) {
                Player player = plugin.getServer().getPlayer(uuid);
                if (player != null) {
                    reunite(player);
                }
            }
        }, 160L); // 8 seconds = 160 ticks
    }

    /**
     * Starts tracking damage to the zombie body
     */
    private void startDamageTracking(UUID uuid) {
        SoulData data = activeSouls.get(uuid);
        if (data == null) return;
        
        // Damage tracking through entity events
        // This is handled in a separate listener
    }

    /**
     * Tracks damage dealt to a zombie
     */
    public void addDamageToZombie(Zombie zombie, double damage) {
        for (SoulData data : activeSouls.values()) {
            if (data.zombie.equals(zombie)) {
                data.accumulatedDamage += damage;
                return;
            }
        }
    }

    /**
     * Cleanup on plugin disable
     */
    public void cleanup() {
        for (SoulData data : activeSouls.values()) {
            data.zombie.remove();
        }
        activeSouls.clear();
    }

    /**
     * Inner class to store soul data
     */
    private static class SoulData {
        Zombie zombie;
        org.bukkit.Location bodyLocation;
        double accumulatedDamage;

        SoulData(Zombie zombie, org.bukkit.Location bodyLocation, double accumulatedDamage) {
            this.zombie = zombie;
            this.bodyLocation = bodyLocation;
            this.accumulatedDamage = accumulatedDamage;
        }
    }
}
