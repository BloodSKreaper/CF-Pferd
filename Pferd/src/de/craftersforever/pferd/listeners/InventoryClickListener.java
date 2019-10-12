package de.craftersforever.pferd.listeners;

import com.gmail.filoghost.HiddenStringUtils;
import de.craftersforever.pferd.inventory.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class InventoryClickListener implements Listener {

    private void showWarning(Player player, String message) {
        player.closeInventory();
        Bukkit.getLogger().warning(message);
        player.sendMessage("§4Warnung: §cEin interner Fehler ist aufgetreten. Bitte melde dies an ein Teammitglied: §4" + message);
    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        if (e.getWhoClicked().getType() != EntityType.PLAYER) return;
        Player p = (Player) e.getWhoClicked();
        Inventory customInventory = InventoryManager.getInventory(p.getUniqueId());
        if (!customInventory.equals(e.getClickedInventory())) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta())return;
        if(e.getCurrentItem().getItemMeta().getLore() == null) return;
        if (e.getCurrentItem().getItemMeta().getLore().size() < 2) return;
        if (!p.isInsideVehicle())return;
        if(p.getVehicle().getType() != EntityType.HORSE) {
            p.closeInventory();
            return;
        }
        Horse horse = (Horse) p.getVehicle();
        if (!horse.hasMetadata("craftersforever.pferd")) {
            p.closeInventory();
        }
        //Clicker ist player and riding a horse, player is clicking a item with custom name and metadata
        p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
        ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
        List<String> itemLore = itemMeta.getLore();
        String hiddenString = HiddenStringUtils.extractHiddenString(itemLore.get(1));
        if (hiddenString.startsWith("setStyle")) {
            String styleName = hiddenString.split("setStyle")[1];
            changeStyle(styleName, horse, p);
        } else if (hiddenString.startsWith("showMenu")) {
            String menuName = hiddenString.split("showMenu")[1];
            showMenu(menuName, customInventory, p);
        } else if (hiddenString.startsWith("setColor")) {
            String colorName = hiddenString.split("setColor")[1];
            changeColor(colorName, horse, p);
        } else if (hiddenString.startsWith("setArmor")) {
            String armorName = hiddenString.split("setArmor")[1];
            changeArmor(armorName, horse, p);
        } else if (hiddenString.startsWith("changeAge")) {
            if (horse.isAdult()) {
                horse.setBaby();
            } else {
                horse.setAdult();
            }
        } else if (hiddenString.startsWith("closeInventory")) {
            p.closeInventory();
        }
        p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
    }

    private void showMenu(String menuName, Inventory inventory, Player player) {
        switch (menuName) {
            case "Selection":
                InventoryManager.showCoordinationMenu(inventory);
                break;
            case "Color":
                InventoryManager.showColorMenu(inventory);
                break;
            case "Style":
                InventoryManager.showStyleMenu(inventory);
                break;
            case "Armor":
                InventoryManager.showArmorMenu(inventory);
                break;
            default:
                showWarning(player, "Invalid Menu " + menuName);
                break;
        }
    }

    private void changeColor(String colorName, Horse horse, Player player) {
        Horse.Color color;
        try{
            color = Horse.Color.valueOf(colorName);
        }catch(IllegalArgumentException ex){
            showWarning(player, "Invalid Horse Color " + colorName);
            return;
        }
        horse.setColor(color);
    }

    private void changeStyle(String styleName, Horse horse, Player player) {
        Horse.Style style;
        try{
            style = Horse.Style.valueOf(styleName);
        }catch(IllegalArgumentException ex){
            showWarning(player, "Invalid Horse Style " + styleName);
            return;
        }
        horse.setStyle(style);
    }


    private void changeArmor(String armorName, Horse horse, Player player) {
        Material armorMaterial;
        try{
            armorMaterial = Material.valueOf(armorName);
        }catch(IllegalArgumentException ex){
            showWarning(player, "Invalid Armor Material " + armorName);
            return;
        }
        ItemStack armor = new ItemStack(armorMaterial);
        horse.getInventory().setArmor(armor);
    }
}
