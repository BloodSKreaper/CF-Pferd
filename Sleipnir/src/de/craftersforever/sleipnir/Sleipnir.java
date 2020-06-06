package de.craftersforever.sleipnir;


import de.craftersforever.sleipnir.commands.PferdCommand;
import de.craftersforever.sleipnir.listeners.*;
import de.craftersforever.sleipnir.storage.SQLiteController;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Sleipnir extends JavaPlugin {
    private boolean invincibleHorse;
    private TextManager textManager;
    private final HashMap<UUID, HorseSetting> horseSettings = new HashMap<>();
    private SQLiteController sqLiteController;


    public void onEnable() {
        initializeConfig();
        sqLiteController = new SQLiteController(this);
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
        for (UUID uuid : horseSettings.keySet()) {
            HorseSetting horseSetting = horseSettings.get(uuid);
            double speed = horseSetting.getSpeed();
            double jumpstrength = horseSetting.getJumpstrength();
            Material armorMaterial = horseSetting.getArmor().getType();
            Horse.Color color = horseSetting.getColor();
            Horse.Style style = horseSetting.getStyle();
            boolean adult = horseSetting.isAdult();
            sqLiteController.setPlayerSetting(uuid, color, style, adult, speed, jumpstrength, armorMaterial);
        }
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
                ResultSet resultSet = sqLiteController.getPlayerSettings(uuid);
                HorseSetting horseSetting = null;
                try {
                    while (resultSet.next()) {
                        double speed = resultSet.getDouble("speed");
                        double jumpstrength = resultSet.getDouble("jumpstrength");
                        String armorMaterial = resultSet.getString("armor");
                        String color = resultSet.getString("color");
                        String style = resultSet.getString("style");
                        boolean adult = resultSet.getBoolean("adult");
                        horseSetting = new HorseSetting(speed, jumpstrength, armorMaterial, style, color, adult);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

                if(horseSetting == null){
                    horseSetting = new HorseSetting();
                }

                horseSettings.put(uuid, horseSetting);

            }
        });
    }

    public void setHorseSetting(UUID uuid, HorseSetting horseSetting) {
        horseSettings.put(uuid, horseSetting);
    }
}
