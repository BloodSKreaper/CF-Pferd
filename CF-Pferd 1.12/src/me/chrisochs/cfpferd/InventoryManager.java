package me.chrisochs.cfpferd;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryManager {
	
	
	private HashMap<UUID, Inventory> inventories = new HashMap<UUID, Inventory>();
	public Inventory getInventory(Player p){
		Inventory inv;
		if(inventories.containsKey(p.getUniqueId())){
			inv = inventories.get(p.getUniqueId());
		}else{
			inv = Bukkit.getServer().createInventory(null, 9, ChatColor.DARK_BLUE+"Wähle aus:");
			inventories.put(p.getUniqueId(), inv);
		}
		return inv;
		
	}
	
	public void removeInventory(Player p){
		if(inventories.containsKey(p.getUniqueId())){
			inventories.remove(p.getUniqueId());
		}
	}
	
	
}
