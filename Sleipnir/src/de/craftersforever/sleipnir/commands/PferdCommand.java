package de.craftersforever.sleipnir.commands;

import de.craftersforever.sleipnir.Sleipnir;
import de.craftersforever.sleipnir.listeners.HorseSetting;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class PferdCommand implements CommandExecutor {
    private final Sleipnir sleipnir;

    public PferdCommand(Sleipnir plugin) {
        this.sleipnir = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', sleipnir.getTextManager().getText("ONLY_PLAYER_COMMAND")));
            return true;
        }
        Player p = (Player) sender;
        if (p.isInsideVehicle()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', sleipnir.getTextManager().getText("NO_SUMMONING_WHILE_IN_VEHICLE")));
            return true;
        }
        spawnHorse(p);
        return true;
    }

    private void spawnHorse(Player p) {
        Horse h = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
        if (!h.isValid()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', sleipnir.getTextManager().getText("HORSE_NOT_SUMMONED")));
            return;
        }
        h.setTamed(true);
        h.setOwner(p);
        h.getInventory().addItem(new ItemStack(Material.SADDLE, 1));
        h.setMetadata("craftersforever.pferd", new FixedMetadataValue(sleipnir, true));
        h.setInvulnerable(sleipnir.isInvincibleHorse());
        HorseSetting horseSetting = sleipnir.getHorseSetting(p.getUniqueId());
        h.setJumpStrength(horseSetting.getJumpstrength());
        h.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horseSetting.getSpeed());

        h.setStyle(horseSetting.getStyle());
        h.setColor(horseSetting.getColor());
        h.getInventory().setArmor(horseSetting.getArmor());
        if (horseSetting.isAdult()) {
            h.setAdult();
        } else {
            h.setBaby();
        }
        p.getWorld().strikeLightningEffect(p.getLocation());
        h.addPassenger(p);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', sleipnir.getTextManager().getText("HORSE_SUMMONED")));
    }
}
