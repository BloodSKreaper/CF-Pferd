package de.craftersforever.pferd.commands;

import de.craftersforever.pferd.inventory.InventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class PferdCommand implements CommandExecutor {
    private Plugin plugin;

    public PferdCommand(Plugin plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Befehl kann nur von Spielern ausgeführt werden!");
            return true;
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("pferd")) {
            if (p.isInsideVehicle()) {
                p.sendMessage("§Du kannst dein Pferd nicht rufen, wenn du in einem Fahrzeug bist.");
                return true;
            }
            spawnHorse(p);

            }

        return true;
    }

    private void spawnHorse(Player p) {
        Horse h = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
        h.setTamed(true);
        h.setOwner(p);
        h.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
        h.setMetadata("craftersforever.pferd", new FixedMetadataValue(plugin,true));
        h.setInvulnerable(true);
        p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 3);
        h.addPassenger(p);
        p.sendMessage("§2Du hast dein Pferd gerufen!");
    }
}
