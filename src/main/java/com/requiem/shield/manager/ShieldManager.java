package com.requiem.shield.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import com.requiem.shield.RequiemShield;

public class ShieldManager {

    private static final String SHIELD_TAG = "requiem_shield";
    private final NamespacedKey shieldKey;

    public ShieldManager(RequiemShield plugin) {
        this.shieldKey = new NamespacedKey(plugin, SHIELD_TAG);
    }

    /**
     * Creates a Requiem Shield with the persistent data container tag
     */
    public ItemStack createShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        ItemMeta meta = shield.getItemMeta();
        
        if (meta != null) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            pdc.set(shieldKey, PersistentDataType.BYTE, (byte) 1);
            shield.setItemMeta(meta);
        }
        
        return shield;
    }

    /**
     * Checks if an ItemStack is a Requiem Shield
     */
    public boolean isRequiemShield(ItemStack item) {
        if (item == null || !item.getType().equals(Material.SHIELD)) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(shieldKey, PersistentDataType.BYTE);
    }

    /**
     * Tags an item as a Requiem Shield
     */
    public void tagAsRequiemShield(ItemStack item) {
        if (item == null || !item.getType().equals(Material.SHIELD)) {
            return;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            PersistentDataContainer pdc = meta.getPersistentDataContainer();
            pdc.set(shieldKey, PersistentDataType.BYTE, (byte) 1);
            item.setItemMeta(meta);
        }
    }
}
