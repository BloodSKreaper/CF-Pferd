package de.craftersforever.pferd.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryManager {

    private static HashMap<UUID, Inventory> inventories = new HashMap<>();
    private static ItemSets itemSets = new ItemSets();


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

    public static void showCoordinationMenu(Inventory inv){
        List<ItemStack> menuItems = itemSets.getSelectionMenu();
        for(int i = 0; i < menuItems.size(); i++){
            inv.setItem(i,menuItems.get(i));
        }
    }

    public static void showColorMenu(Inventory inv){
        List<ItemStack> menuItems = itemSets.getColorMenu();
        for(int i = 0; i < menuItems.size(); i++){
            inv.setItem(i,menuItems.get(i));
        }
    }

    public static void showStyleMenu(Inventory inv){
        List<ItemStack> menuItems = itemSets.getStyleMenu();
        for(int i = 0; i < menuItems.size(); i++){
            inv.setItem(i,menuItems.get(i));
        }
    }

    public static void showArmorMenu(Inventory inv){
        List<ItemStack> menuItems = itemSets.getArmorMenu();
        for(int i = 0; i < menuItems.size(); i++){
            inv.setItem(i,menuItems.get(i));
        }
    }

    public static void removeInventory(UUID uuid) {
        inventories.remove(uuid);
    }


}
