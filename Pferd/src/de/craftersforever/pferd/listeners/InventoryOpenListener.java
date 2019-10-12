package de.craftersforever.pferd.listeners;

import de.craftersforever.pferd.inventory.InventoryManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
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
    public void onMoveItem(InventoryMoveItemEvent event){
        if (event.isCancelled()) return;
        Inventory destination = event.getDestination();
        if(InventoryManager.isCachedInventory(destination)){
            event.setCancelled(true);
        }
    }

}
