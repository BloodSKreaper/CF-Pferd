package de.craftersforever.sleipnir.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class InventoryManager {

    private static final HashMap<UUID, Inventory> inventories = new HashMap<>();
    private static final ItemSets itemSets = new ItemSets();


    public static Inventory getInventory(UUID uuid) {
        Inventory inv;
        if (inventories.containsKey(uuid)) {
            inv = inventories.get(uuid);
        } else {
            inv = Bukkit.getServer().createInventory(null, 9, ChatColor.GOLD + "Bitte auswählen:");
            inventories.put(uuid, inv);
        }
        return inv;
    }

    public static boolean isCachedInventory(Inventory inventory) {
        return inventories.containsValue(inventory);
    }

    public static void showCoordinationMenu(Inventory inv) {
        ItemStack[] menuItems = itemSets.getSelectionMenu();
        for (int i = 0; i < menuItems.length; i++) {
            inv.setItem(i, menuItems[i]);
        }
    }

    public static void showColorMenu(Inventory inv) {
        ItemStack[] menuItems = itemSets.getColorMenu();
        for (int i = 0; i < menuItems.length; i++) {
            inv.setItem(i, menuItems[i]);
        }
    }

    public static void showStyleMenu(Inventory inv) {
        ItemStack[] menuItems = itemSets.getStyleMenu();
        for (int i = 0; i < menuItems.length; i++) {
            inv.setItem(i, menuItems[i]);
        }
    }

    public static void showArmorMenu(Inventory inv) {
        ItemStack[] menuItems = itemSets.getArmorMenu();
        for (int i = 0; i < menuItems.length; i++) {
            inv.setItem(i, menuItems[i]);
        }
    }

    public static void removeInventory(UUID uuid) {
        inventories.remove(uuid);
    }


}
