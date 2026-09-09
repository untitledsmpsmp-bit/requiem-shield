package com.requiem.shield.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import com.requiem.shield.RequiemShield;
import com.requiem.shield.util.SoundUtil;
import com.requiem.shield.util.ParticleUtil;

public class CombatListener implements Listener {

    private final RequiemShield plugin;

    public CombatListener(RequiemShield plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the mark effect when shield user attacks another player
     */
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        
        Player attacker = (Player) event.getDamager();
        Player target = (Player) event.getEntity();
        
        // Check if attacker has Requiem Shield in offhand
        if (!plugin.getShieldManager().isRequiemShield(attacker.getInventory().getItemInOffHand())) {
            return;
        }
        
        // Only mark if no player is already marked
        if (plugin.getMarkManager().getMarkedPlayer() != null) {
            return;
        }
        
        // Mark the target
        plugin.getMarkManager().markPlayer(target);
        
        // Apply Glowing effect
        target.setGlowing(true);
        
        // Play effects
        SoundUtil.playMarkSound(target);
        ParticleUtil.playMarkEffect(target);
        
        // Send messages
        attacker.sendMessage("§7✦ §f" + target.getName() + " §7has been marked for §f10§7s");
        target.sendMessage("§7✦ §fYou have been marked by §f" + attacker.getName() + "§7!");
    }
}
