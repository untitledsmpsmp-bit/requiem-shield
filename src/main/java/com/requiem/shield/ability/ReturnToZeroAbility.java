package com.requiem.shield.ability;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.requiem.shield.RequiemShield;
import com.requiem.shield.util.SoundUtil;
import com.requiem.shield.util.ParticleUtil;

import java.util.*;

public class ReturnToZeroAbility {

    private final RequiemShield plugin;
    private final Map<UUID, Long> activeEffects = new HashMap<>();
    private final Random random = new Random();

    public ReturnToZeroAbility(RequiemShield plugin) {
        this.plugin = plugin;
        startEffectTicker();
    }

    /**
     * Activates Return to Zero on the marked player
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
        
        // Apply effect
        long expiryTime = System.currentTimeMillis() + 5000; // 5 seconds
        activeEffects.put(marked.getUniqueId(), expiryTime);
        
        // Apply slowness potions
        marked.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2, false, false));
        marked.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 100, 1, false, false));
        
        // Play activation effects
        SoundUtil.playReturnToZeroSound(player);
        SoundUtil.playEerieSound(marked);
        ParticleUtil.playRewindEffect(marked);
        
        // Send titles
        player.showTitle(org.bukkit.Bukkit.createTitle(
            org.net.minecraft.network.chat.Component.literal("§f§lRETURN TO ZERO"),
            org.net.minecraft.network.chat.Component.literal("§7Reality has been rewritten."),
            10, 100, 20
        ));
        
        marked.showTitle(org.bukkit.Bukkit.createTitle(
            org.net.minecraft.network.chat.Component.literal("§c§lRETURNED TO ZERO"),
            org.net.minecraft.network.chat.Component.literal("§7Reality no longer accepts your actions."),
            10, 100, 20
        ));
        
        player.sendMessage("§7✦ §fReturn to Zero activated!");
        marked.sendMessage("§7✦ §f✦ Your action was returned to zero.");
        
        return true;
    }

    /**
     * Checks if a player is affected by Return to Zero
     */
    public boolean isAffected(Player player) {
        Long expiryTime = activeEffects.get(player.getUniqueId());
        if (expiryTime == null) {
            return false;
        }
        
        if (System.currentTimeMillis() > expiryTime) {
            activeEffects.remove(player.getUniqueId());
            return false;
        }
        
        return true;
    }

    /**
     * Tries to reject an action with 50% chance
     */
    public boolean tryReject(Player player) {
        if (!isAffected(player)) {
            return false;
        }
        
        if (random.nextDouble() < 0.5) {
            // Reject the action
            player.sendMessage("§7✦ §fYour action was returned to zero.");
            SoundUtil.playRewindSound(player);
            ParticleUtil.playRewindEffect(player);
            return true;
        }
        
        return false;
    }

    /**
     * Starts the effect ticker to check for expired effects
     */
    private void startEffectTicker() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
            activeEffects.entrySet().removeIf(entry -> {
                Player player = plugin.getServer().getPlayer(entry.getKey());
                long expiryTime = entry.getValue();
                
                if (player == null || !player.isOnline() || System.currentTimeMillis() > expiryTime) {
                    return true;
                }
                
                return false;
            });
        }, 20L, 20L); // Check every second
    }

    /**
     * Cleanup on plugin disable
     */
    public void cleanup() {
        activeEffects.clear();
    }
}
