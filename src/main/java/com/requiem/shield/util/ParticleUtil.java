package com.requiem.shield.util;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleUtil {

    /**
     * Plays mark effect particles (white/gold)
     */
    public static void playMarkEffect(Player target) {
        Location loc = target.getLocation().add(0, 1, 0);
        
        // White particles
        target.getWorld().spawnParticle(Particle.END_ROD, loc, 20, 0.5, 0.5, 0.5, 0.1);
        
        // Gold particles
        target.getWorld().spawnParticle(Particle.DUST, loc, 15, 0.5, 0.5, 0.5, 0, 
                new Particle.DustOptions(Color.fromBGR(255, 215, 0), 1.5f));
    }

    /**
     * Plays Soul Split activation effect (white/gold)
     */
    public static void playSoulSplitEffect(Player player) {
        Location loc = player.getLocation().add(0, 1, 0);
        
        // Circular white particles
        player.getWorld().spawnParticle(Particle.END_ROD, loc, 30, 1.0, 1.0, 1.0, 0.15);
        
        // Gold dust
        player.getWorld().spawnParticle(Particle.DUST, loc, 25, 1.0, 1.0, 1.0, 0,
                new Particle.DustOptions(Color.fromBGR(255, 215, 0), 2.0f));
    }

    /**
     * Plays Return to Zero rewind effect (white/gold)
     */
    public static void playRewindEffect(Player target) {
        Location loc = target.getLocation().add(0, 1, 0);
        
        // Rewind spirals - white
        target.getWorld().spawnParticle(Particle.END_ROD, loc, 20, 0.8, 0.8, 0.8, 0.2);
        
        // Gold rewind
        target.getWorld().spawnParticle(Particle.DUST, loc, 15, 0.8, 0.8, 0.8, 0,
                new Particle.DustOptions(Color.fromBGR(255, 215, 0), 1.5f));
    }

    /**
     * Plays restoration effect (white/gold)
     */
    public static void playRestorationEffect(Player player) {
        Location loc = player.getLocation().add(0, 1, 0);
        
        // Healing white particles
        player.getWorld().spawnParticle(Particle.FALLING_DUST, loc, 20, 0.5, 0.5, 0.5, 
                org.bukkit.Material.GOLD_BLOCK.createBlockData());
        
        // Gold dust
        player.getWorld().spawnParticle(Particle.DUST, loc, 20, 0.5, 0.5, 0.5, 0,
                new Particle.DustOptions(Color.fromBGR(255, 215, 0), 1.5f));
    }
}
