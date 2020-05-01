package de.craftersforever.sleipnir;


import de.craftersforever.sleipnir.commands.PferdCommand;
import de.craftersforever.sleipnir.listeners.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Sleipnir extends JavaPlugin {
    private boolean invincibleHorse;
    private TextManager textManager;
    private final HashMap<UUID, HorseSetting> horseSettings = new HashMap<>();


    public void onEnable() {
        initializeConfig();
        textManager = new TextManager(this);
        registerListeners();
        registerCommands();
    }

    private void initializeConfig() {
        saveDefaultConfig();
        invincibleHorse = getConfig().getBoolean("horse.invincible");
    }

    private void registerCommands() {
        getCommand("horse").setExecutor(new PferdCommand(this));
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new InventoryClickListener(this), this);
        pm.registerEvents(new InventoryOpenListener(), this);
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new VehicleListener(), this);

    }

    public TextManager getTextManager() {
        return textManager;
    }

    public boolean isInvincibleHorse() {
        return invincibleHorse;
    }

    public void onDisable() {
    }

    public HorseSetting getHorseSetting(UUID uniqueId) {
        return horseSettings.get(uniqueId);
    }

    public boolean containsHorseSetting(UUID uniqueId) {
        return horseSettings.containsKey(uniqueId);
    }

    public void generateHorseSetting(UUID uuid) {
        HorseSetting horseSetting = new HorseSetting();
        horseSettings.put(uuid, horseSetting);
    }

    public void setHorseSetting(UUID uuid, HorseSetting horseSetting) {
        horseSettings.put(uuid, horseSetting);
    }
}
