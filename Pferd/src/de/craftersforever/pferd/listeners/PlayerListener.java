package de.craftersforever.pferd.listeners;

import de.craftersforever.pferd.inventory.InventoryManager;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p =  e.getPlayer();
        if (!p.isInsideVehicle()) return;
        if (p.getVehicle().getType() != EntityType.HORSE) return;
        if (p.getVehicle().hasMetadata("craftersforever.pferd")) {
            p.leaveVehicle();
            InventoryManager.removeInventory(p.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (!p.isInsideVehicle()) return;
        if (p.getVehicle().getType() != EntityType.HORSE) return;
        if (p.getVehicle().hasMetadata("craftersforever.pferd")) {
            p.leaveVehicle();
        }
    }

    @EventHandler
    public void onLeash(PlayerLeashEntityEvent e) {
        if(e.isCancelled())return;
        Player p = e.getPlayer();
        if (e.getEntity().getType() != EntityType.HORSE) return;
        if (e.getEntity().hasMetadata("craftersforever.pferd")) {
            e.setCancelled(true);
            p.sendMessage("§cDu kannst dieses Pferd nicht anleinen!");
        }
    }


}
