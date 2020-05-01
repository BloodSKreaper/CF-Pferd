package de.craftersforever.sleipnir.listeners;

import de.craftersforever.sleipnir.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.ItemStack;


public class HorseSetting {
    private double speed;
    private double jumpstrength;
    private ItemStack armor;
    private Horse.Style style;
    private Horse.Color color;
    private boolean adult;

    public HorseSetting() {
        speed = 0.2;
        jumpstrength = 1;
        armor = new ItemStack(Material.AIR);
        style = Utils.randomEnum(Horse.Style.class);
        color = Utils.randomEnum(Horse.Color.class);
        adult = true;
    }

    public HorseSetting(double speed, double jumpstrength, Material armormaterial, Horse.Style style, Horse.Color color, boolean adult) {
        this.speed = speed;
        this.jumpstrength = jumpstrength;
        this.armor = new ItemStack(armormaterial);
        this.style = style;
        this.color = color;

    }

    public HorseSetting(double speed, double jumpstrength, String armormaterial, String styleName, String colorName, boolean adult) {
        this.speed = speed;
        this.jumpstrength = jumpstrength;

        Material material;
        try {
            material = Material.valueOf(armormaterial);
        } catch (IllegalArgumentException exception) {
            Bukkit.getLogger().severe("Illegal armor Material");
            material = Material.AIR;
        }
        armor = new ItemStack(material);

        Horse.Style style;
        try {
            style = Horse.Style.valueOf(styleName);
        } catch (IllegalArgumentException exception) {
            style = Horse.Style.WHITE;
        }
        this.style = style;

        Horse.Color color;
        try {
            color = Horse.Color.valueOf(styleName);
        } catch (IllegalArgumentException exception) {
            color = Horse.Color.WHITE;
        }
        this.color = color;
    }

    public double getSpeed() {
        return speed;
    }

    public double getJumpstrength() {
        return jumpstrength;
    }

    public ItemStack getArmor() {
        return armor;
    }

    public Horse.Style getStyle() {
        return style;
    }

    public Horse.Color getColor() {
        return color;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setJumpstrength(double jumpstrength) {
        this.jumpstrength = jumpstrength;
    }

    public void setArmor(ItemStack armor) {
        this.armor = armor;
    }

    public void setStyle(Horse.Style style) {
        this.style = style;
    }

    public void setColor(Horse.Color color) {
        this.color = color;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
