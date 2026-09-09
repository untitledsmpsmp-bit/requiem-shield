package com.requiem.shield;

import org.bukkit.plugin.java.JavaPlugin;
import com.requiem.shield.listener.ShieldListener;
import com.requiem.shield.listener.CombatListener;
import com.requiem.shield.listener.AbilityListener;
import com.requiem.shield.command.CommandHandler;
import com.requiem.shield.manager.ShieldManager;
import com.requiem.shield.manager.CooldownManager;
import com.requiem.shield.manager.MarkManager;

public class RequiemShield extends JavaPlugin {

    private static RequiemShield instance;
    private ShieldManager shieldManager;
    private CooldownManager cooldownManager;
    private MarkManager markManager;

    @Override
    public void onEnable() {
        instance = this;
        
        // Initialize managers
        shieldManager = new ShieldManager(this);
        cooldownManager = new CooldownManager(this);
        markManager = new MarkManager(this);
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new ShieldListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new AbilityListener(this), this);
        
        // Register commands
        new CommandHandler(this);
        
        getLogger().info("✦ Requiem Shield enabled - Reality has been rewritten.");
    }

    @Override
    public void onDisable() {
        // Cleanup
        if (markManager != null) {
            markManager.cleanup();
        }
        getLogger().info("✦ Requiem Shield disabled.");
    }

    public static RequiemShield getInstance() {
        return instance;
    }

    public ShieldManager getShieldManager() {
        return shieldManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public MarkManager getMarkManager() {
        return markManager;
    }
}
