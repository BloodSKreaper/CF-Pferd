package de.craftersforever.sleipnir.storage;

import org.bukkit.Material;
import org.bukkit.entity.Horse;

import java.sql.ResultSet;
import java.util.UUID;

public abstract class StorageDriver {

    public abstract void setPlayerSetting(UUID uuid, Horse.Color color, Horse.Style style, boolean adult, double speed, double jumpstrength, Material armor);
    public abstract ResultSet getPlayerSettings(UUID uuid);
}
