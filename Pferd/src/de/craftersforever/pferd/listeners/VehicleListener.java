package de.craftersforever.pferd.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class VehicleListener implements Listener {

    @EventHandler
    public void onHorseExit(VehicleExitEvent e) {
        if(e.isCancelled())return;
        if (e.getVehicle().getType() != EntityType.HORSE) return;
        Horse h = (Horse) e.getVehicle();
        if (h.hasMetadata("craftersforever.pferd")) {
            if (e.getExited().getType() == EntityType.PLAYER) {
                h.remove();
            }
        }
    }
}

