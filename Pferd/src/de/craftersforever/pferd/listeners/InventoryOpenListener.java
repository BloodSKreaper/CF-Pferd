package de.craftersforever.pferd.listeners;

import de.craftersforever.pferd.inventory.InventoryManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

public class InventoryOpenListener implements Listener {


    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if(e.isCancelled())return;
        if(!e.getPlayer().isInsideVehicle())return;
        if(e.getPlayer().getVehicle().getType() != EntityType.HORSE)return;
        Horse horse = (Horse) e.getPlayer().getVehicle();
        if(!horse.hasMetadata("craftersforever.pferd"))return;
        Inventory inventory = InventoryManager.getInventory(e.getPlayer().getUniqueId());
        if(inventory.equals(e.getInventory()))return;
        e.setCancelled(true);
        InventoryManager.showCoordinationMenu(inventory);
        e.getPlayer().openInventory(inventory);
    }

    @EventHandler
    public void onDragItem(InventoryDragEvent event){
        if(event.isCancelled())return;
        Inventory inventory = event.getInventory();
        if(InventoryManager.isCachedInventory(inventory)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItem(InventoryClickEvent event){
        if(event.isCancelled())return;
        Inventory inventory = event.getInventory();
        if(InventoryManager.isCachedInventory(inventory)){
            event.setCancelled(true);
        }
    }

}
