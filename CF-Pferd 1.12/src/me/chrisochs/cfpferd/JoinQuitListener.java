package me.chrisochs.cfpferd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;


public class JoinQuitListener implements Listener {
	private playerdata playerdata;
	private InventoryManager InventoryManager;

	private Plugin plugin;
	public JoinQuitListener(Plugin pl, playerdata pldata, InventoryManager invmanager){
		plugin = pl;
		playerdata = pldata;
		InventoryManager = invmanager;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		final PlayerJoinEvent event = e;
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){

			@Override
			public void run() {
				playerdata.setupPlayerConfig(event.getPlayer().getUniqueId());
				
			}
			
		}, 2L);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
				InventoryManager.removeInventory(e.getPlayer());
	}
}
