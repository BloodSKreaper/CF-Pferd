package de.craftersforever.sleipnir.listeners;

import de.craftersforever.sleipnir.Sleipnir;
import de.craftersforever.sleipnir.inventory.InventoryManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerListener implements Listener {
    private final Sleipnir sleipnir;

    public PlayerListener(Sleipnir sleipnir) {
        this.sleipnir = sleipnir;
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!sleipnir.containsHorseSetting(player.getUniqueId())) {
            sleipnir.generateHorseSetting(player.getUniqueId());
        }

    }


    @SuppressWarnings("unused")
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.getVehicle() == null) return;
        if (p.getVehicle().getType() != EntityType.HORSE) return;
        if (p.getVehicle().hasMetadata("craftersforever.pferd")) {
            p.leaveVehicle();
            InventoryManager.removeInventory(p.getUniqueId());
        }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (p.getVehicle() == null) return;
        if (p.getVehicle().getType() != EntityType.HORSE) return;
        if (p.getVehicle().hasMetadata("craftersforever.pferd")) {
            p.leaveVehicle();
        }
    }

    @SuppressWarnings("unused")
    @EventHandler
    public void onLeash(PlayerLeashEntityEvent e) {
        if (e.isCancelled()) return;
        Player p = e.getPlayer();
        if (e.getEntity().getType() != EntityType.HORSE) return;
        if (e.getEntity().hasMetadata("craftersforever.pferd")) {
            e.setCancelled(true);
            p.sendMessage(sleipnir.getTextManager().getText("HORSE_NOT_LEASHABLE"));
        }
    }


}
