package de.craftersforever.pferd;


import de.craftersforever.pferd.commands.PferdCommand;
import de.craftersforever.pferd.listeners.InventoryOpenListener;
import de.craftersforever.pferd.listeners.PlayerListener;
import de.craftersforever.pferd.listeners.VehicleListener;
import de.craftersforever.pferd.listeners.InventoryClickListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Pferd extends JavaPlugin {

	public void onEnable() {
		registerListeners();
		registerCommands();
	}

	private void registerCommands() {
		if(getCommand("pferd")!=null) {
			getCommand("pferd").setExecutor(new PferdCommand(this));
		}
	}

	private void registerListeners(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryClickListener(),this);
		pm.registerEvents(new InventoryOpenListener(),this);
		pm.registerEvents(new PlayerListener(),this);
		pm.registerEvents(new VehicleListener(),this);

	}

	public void onDisable() {
	}
}
