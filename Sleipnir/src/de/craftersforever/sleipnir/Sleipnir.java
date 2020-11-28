package de.craftersforever.sleipnir;


import de.craftersforever.sleipnir.commands.PferdCommand;
import de.craftersforever.sleipnir.listeners.*;
import de.craftersforever.sleipnir.storage.MySQLController;
import de.craftersforever.sleipnir.storage.SQLiteController;
import de.craftersforever.sleipnir.storage.StorageDriver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class Sleipnir extends JavaPlugin {
    private boolean invincibleHorse;
    private TextManager textManager;
    private final HashMap<UUID, HorseSetting> horseSettings = new HashMap<>();
    private StorageDriver storageDriver;
    private static Sleipnir sleipnir;

    public static Sleipnir getInstance() {
        return sleipnir;
    }


    public void onEnable() {
        sleipnir = this;
        initializeConfig();
        if (getConfig().getString("storage").equals("sqlite")) {
            storageDriver = new SQLiteController(this);
        } else {
            storageDriver = new MySQLController(this);
        }
        textManager = new TextManager(this);
        registerListeners();
        registerCommands();
        startSaveScheduler();
    }

    private void startSaveScheduler() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                savePlayerData(true);
            }
        }, 0l, 2400l);
    }

    private void savePlayerData(boolean async) {
        if (!async) {
            for (UUID uuid : horseSettings.keySet()) {
                HorseSetting horseSetting = horseSettings.get(uuid);
                double speed = horseSetting.getSpeed();
                double jumpstrength = horseSetting.getJumpstrength();
                Material armorMaterial = horseSetting.getArmor().getType();
                Horse.Color color = horseSetting.getColor();
                Horse.Style style = horseSetting.getStyle();
                boolean adult = horseSetting.isAdult();
                storageDriver.setPlayerSetting(uuid, color, style, adult, speed, jumpstrength, armorMaterial);
            }
        } else {
            Iterator<Map.Entry<UUID, HorseSetting>> iter = horseSettings.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<UUID, HorseSetting> entry = iter.next();
                HorseSetting horseSetting = entry.getValue();
                final UUID uuid = entry.getKey();
                final double speed = horseSetting.getSpeed();
                final double jumpstrength = horseSetting.getJumpstrength();
                final Material armorMaterial = horseSetting.getArmor().getType();
                final Horse.Color color = horseSetting.getColor();
                final Horse.Style style = horseSetting.getStyle();
                final boolean adult = horseSetting.isAdult();
                Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
                    @Override
                    public void run() {
                        storageDriver.setPlayerSetting(uuid, color, style, adult, speed, jumpstrength, armorMaterial);
                    }
                });
                if (!getServer().getOfflinePlayer(uuid).isOnline()) {
                    iter.remove();
                }
            }
        }
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
        savePlayerData(false);
    }

    public HorseSetting getHorseSetting(UUID uniqueId) {
        return horseSettings.get(uniqueId);
    }

    public boolean containsHorseSetting(UUID uniqueId) {
        return horseSettings.containsKey(uniqueId);
    }

    public void generateHorseSetting(UUID puuid) {
        final UUID uuid = puuid;
        Bukkit.getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                ResultSet resultSet = storageDriver.getPlayerSettings(uuid);

                HorseSetting horseSetting = null;
                try {
                    if (!resultSet.isBeforeFirst()) {
                        horseSetting = new HorseSetting();
                    } else {
                        while (resultSet.next()) {
                            double speed = resultSet.getDouble("speed");
                            double jumpstrength = resultSet.getDouble("jumpstrength");
                            String armorMaterial = resultSet.getString("armor");
                            String color = resultSet.getString("color");
                            String style = resultSet.getString("style");
                            boolean adult = resultSet.getBoolean("adult");
                            horseSetting = new HorseSetting(speed, jumpstrength, armorMaterial, style, color, adult);
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                horseSettings.put(uuid, horseSetting);
            }
        });
    }

    public void setHorseSetting(UUID uuid, HorseSetting horseSetting) {
        if (horseSetting != null) {
            horseSettings.put(uuid, horseSetting);
        }
    }
}
