package com.requiem.shield.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.entity.Player;
import com.requiem.shield.RequiemShield;

public class ShieldListener implements Listener {

    private final RequiemShield plugin;

    public ShieldListener(RequiemShield plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onShieldInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getHand() != EquipmentSlot.OFF_HAND) {
            return;
        }
        
        if (player.getInventory().getItemInOffHand() == null) {
            return;
        }
        
        if (!plugin.getShieldManager().isRequiemShield(player.getInventory().getItemInOffHand())) {
            return;
        }
        
        // Shield is recognized
    }
}
