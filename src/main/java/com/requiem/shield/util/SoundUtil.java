package com.requiem.shield.util;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtil {

    /**
     * Plays cinematic mark sound
     */
    public static void playMarkSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.8f, 1.2f);
    }

    /**
     * Plays Soul Split activation sound
     */
    public static void playSoulSplitSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 1.0f);
    }

    /**
     * Plays Return to Zero activation sound
     */
    public static void playReturnToZeroSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 1.0f, 0.5f);
    }

    /**
     * Plays rewind/rejection sound
     */
    public static void playRewindSound(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 0.8f);
    }

    /**
     * Plays eerie distorted sound for marked player
     */
    public static void playEerieSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 0.7f, 0.5f);
    }

    /**
     * Plays restoration sound
     */
    public static void playRestorationSound(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1.0f, 1.0f);
    }
}
