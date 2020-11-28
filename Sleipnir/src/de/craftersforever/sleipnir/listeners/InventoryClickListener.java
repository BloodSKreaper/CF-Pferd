package de.craftersforever.sleipnir.listeners;

import de.craftersforever.sleipnir.HorseSetting;
import de.craftersforever.sleipnir.Sleipnir;
import de.craftersforever.sleipnir.inventory.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class InventoryClickListener implements Listener {
    private final Sleipnir sleipnir;

    public InventoryClickListener(Sleipnir sleipnir) {
        this.sleipnir = sleipnir;
    }

    private void showWarning(Player player, String message) {
        player.closeInventory();
        Bukkit.getLogger().warning(message);
        player.sendMessage(sleipnir.getTextManager().getText("INTERNAL_ERROR", message));
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onInventoryInteractEvent(InventoryClickEvent e) {
        if (e.isCancelled()) return;
        if (e.getWhoClicked().getType() != EntityType.PLAYER) return;
        Player p = (Player) e.getWhoClicked();
        Inventory customInventory = InventoryManager.getInventory(p.getUniqueId());

        if (!customInventory.equals(e.getClickedInventory())) return;
        e.setCancelled(true);
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!e.getCurrentItem().getItemMeta().hasLore()) return;
        if (e.getCurrentItem().getItemMeta().getLore().size() < 1) return;
        if (!p.isInsideVehicle()) {
            p.closeInventory();
            return;
        }
        if (p.getVehicle().getType() != EntityType.HORSE) {
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
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Sleipnir.getInstance(), "command");
        String command = persistentDataContainer.get(namespacedKey, PersistentDataType.STRING);
        if (command.startsWith("setStyle")) {
            String styleName = command.split("setStyle")[1];
            changeStyle(styleName, p);
        } else if (command.startsWith("showMenu")) {
            String menuName = command.split("showMenu")[1];
            showMenu(menuName, customInventory, p);
        } else if (command.startsWith("setColor")) {
            String colorName = command.split("setColor")[1];
            changeColor(colorName, p);
        } else if (command.startsWith("setArmor")) {
            String armorName = command.split("setArmor")[1];
            changeArmor(armorName, p);
        } else if (command.startsWith("changeAge")) {
            changeAge(p);
        } else if (command.startsWith("closeInventory")) {
            p.closeInventory();
        }
        changeRidingHorse(p, horse);
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
                showWarning(player, sleipnir.getTextManager().getText("INVALID_MENU", menuName));
                break;
        }
    }

    private void changeColor(String colorName, Player player) {
        Horse.Color color;
        try {
            color = Horse.Color.valueOf(colorName);
        } catch (IllegalArgumentException ex) {
            showWarning(player, sleipnir.getTextManager().getText("INVALID_COLOR", colorName));
            return;
        }
        HorseSetting setting = sleipnir.getHorseSetting(player.getUniqueId());
        setting.setColor(color);
        sleipnir.setHorseSetting(player.getUniqueId(), setting);
    }

    private void changeAge(Player player) {
        HorseSetting setting = sleipnir.getHorseSetting(player.getUniqueId());
        setting.setAdult(!setting.isAdult());
        sleipnir.setHorseSetting(player.getUniqueId(), setting);
    }

    private void changeStyle(String styleName, Player player) {
        Horse.Style style;
        try {
            style = Horse.Style.valueOf(styleName);
        } catch (IllegalArgumentException ex) {
            showWarning(player, sleipnir.getTextManager().getText("INVALID_STYLE", styleName));
            return;
        }
        HorseSetting setting = sleipnir.getHorseSetting(player.getUniqueId());
        setting.setStyle(style);
        sleipnir.setHorseSetting(player.getUniqueId(), setting);
    }


    private void changeArmor(String armorName, Player player) {
        Material armorMaterial;
        try {
            armorMaterial = Material.valueOf(armorName);
        } catch (IllegalArgumentException ex) {
            showWarning(player, sleipnir.getTextManager().getText("INVALID_ARMOR", armorName));
            return;
        }
        ItemStack armor = new ItemStack(armorMaterial);
        HorseSetting setting = sleipnir.getHorseSetting(player.getUniqueId());
        setting.setArmor(armor);
        sleipnir.setHorseSetting(player.getUniqueId(), setting);
    }

    private void changeRidingHorse(Player player, Horse horse) {
        HorseSetting setting = sleipnir.getHorseSetting(player.getUniqueId());
        horse.setColor(setting.getColor());
        horse.setStyle(setting.getStyle());
        horse.getInventory().setArmor(setting.getArmor());
        if (setting.isAdult()) {
            horse.setAdult();
        } else {
            horse.setBaby();
        }
        horse.setJumpStrength(setting.getJumpstrength());
        horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(setting.getSpeed());
    }
}
