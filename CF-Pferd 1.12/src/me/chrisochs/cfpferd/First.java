package me.chrisochs.cfpferd;



import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_12_R1.AttributeInstance;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import net.minecraft.server.v1_12_R1.GenericAttributes;



public class First extends JavaPlugin implements Listener
{
	//DEKLARIERUNGEN
	private playerdata playerdata = new playerdata();
	InventoryManager InventoryManager = new InventoryManager();
	Itemsets Itemsets = new Itemsets(playerdata);
	
	
	  public static final double NORMAL_SPEED = 0.23888842211270872;





  public void onEnable()
  {
    PluginManager manager = getServer().getPluginManager();
    manager.registerEvents(this, this);
    System.out.println("[CF-Pferd] by ChrisOchs");
    manager.registerEvents(new HorseSaveListeners(), this);
    manager.registerEvents(new JoinQuitListener(this, playerdata, InventoryManager), this);
    manager.registerEvents(new InventoryClickEventHandler(this), this);

  }
  

  

  
  public void onDisable()
  {
	 
    System.out.println("[CF-Pferd] wurde deaktiviert");
  }
  
  public void onLoad()
  {
    System.out.println("[CF-Pferd] wird geladenen");
	  
  }

  public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage(ChatColor.RED + "Befehl kann nur InGame ausgeführt werden!");
      return true;
    }
    Player p = (Player)sender;
    
    //Anfang Pferd
    if (cmd.getName().equalsIgnoreCase("pferd")) {
    	if(p.isInsideVehicle()){
    		p.sendMessage("§cDu musst zuerst aus deinem derzeitigen Vehikel aussteigen!");
    	}else{
			if(p.getLocation().getBlock().getType().equals(Material.WATER) || p.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER) ||p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER||p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.STATIONARY_WATER){
				this.spawnBoat(p);
			}else{
    	this.SpawnHorse(p);
			}
    	}
    	}
    	
    if(cmd.getName().equalsIgnoreCase("horseedit")){
    	if(p.hasPermission("cf.horseedit")){
    		Inventory inv = InventoryManager.getInventory(p);
    		p.openInventory(inv);
    		Itemsets.setupCoordinationMenu(p.getUniqueId(), inv);
    	}else{
            p.sendMessage("§8[§1Stamm§8] §eDu hast keinen Zugriff auf diesen Befehl!");
            p.sendMessage("§e Dieses Feature ist nur Stammspielern zugänglich!");
            p.sendMessage("§2Du willst auch §8[§1Stamm§8] §2werden? Sammle eine Spielzeit von 5 Tagen!");
    	}
    }
    	
    
    return true;
  }
  
private void spawnBoat(Player p) {
	Entity boat = p.getLocation().getWorld().spawnEntity(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY()+0.9, p.getLocation().getZ()), EntityType.BOAT);
	boat.setCustomName("RIDING");
	boat.setCustomNameVisible(false);
	boat.addPassenger(p);
	boat.setInvulnerable(true);
	p.sendMessage("§2Dein Boot wurde gespawnt!");
	
}

//SPEED MUST BE SET!
@SuppressWarnings("deprecation")
public void SpawnHorse(Player p){
	Horse h = (Horse) p.getWorld().spawnEntity(p.getLocation(), EntityType.HORSE);
	h.setTamed(true);
	h.setOwner(p);
	if(playerdata.getBoolean(p.getUniqueId(), "Adult")==true){
		h.setAdult();
	}else{
		h.setBaby();
	}

	h.getInventory().addItem(new ItemStack(Material.SADDLE,1));
		h.setJumpStrength(0.6);
	h.setMaxHealth(20.0);
	h.setInvulnerable(true);
	String color = playerdata.getString(p.getUniqueId(), "Color");
	String style = playerdata.getString(p.getUniqueId(), "Style");
 	String armor = playerdata.getString(p.getUniqueId(), "Armor");
	switch(style){
	case "BLACK_DOTS": h.setStyle(Style.BLACK_DOTS);
	break;
	case "WHITE_DOTS": h.setStyle(Style.WHITE_DOTS);
	break;
	case "NONE": h.setStyle(Style.NONE);
	break;
	case "WHITEFIELD": h.setStyle(Style.WHITEFIELD);
	break;
	case "WHITE": h.setStyle(Style.WHITE);
	break;
	}

	switch(color){
	case "BROWN": h.setColor(Color.BROWN);
	break;
	case "BLACK": h.setColor(Color.BLACK);
	break;
	case "CHESTNUT": h.setColor(Color.CHESTNUT);
	break;
	case "CREAMY": h.setColor(Color.CREAMY);
	break;
	case "DARK_BROWN": h.setColor(Color.DARK_BROWN);
	break;
	case "GRAY": h.setColor(Color.GRAY);
	break;
	case "WHITE": h.setColor(Color.WHITE);
	break;
	}
	

	switch(armor){
	case "IRON_BARDING": h.getInventory().setArmor(new ItemStack(Material.IRON_BARDING,1));
	break;
	case "GOLD_BARDING": h.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING,1));
	break;
	case "DIAMOND_BARDING": h.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING,1));
	break;
	case "NONE": h.getInventory().setArmor(new ItemStack(Material.AIR,1));
	}

	AttributeInstance attributes = ((EntityInsentient)((CraftLivingEntity)h).getHandle()).getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
    	attributes.setValue(NORMAL_SPEED);
    p.getWorld().playEffect(p.getLocation(),Effect.ENDER_SIGNAL, 3);
	h.addPassenger(p.getPlayer());
	h.setCustomName("RIDING");
	p.sendMessage("§2Dein Pferd wurde gespawnt!");
}
  

		  



public void setupColor(String Color, Player p){
	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
	if(playerdata.getString(p.getUniqueId(), "Color").equals(Color)){
		p.sendMessage("§4Fehler: §cDein Pferd hat bereits die Farbe "+Color+"!");
	}else{
	playerdata.set(p.getUniqueId(), "Color", Color);
	ChangeRidingHorse(p);
	Itemsets.setupColorMenu(p.getUniqueId(), InventoryManager.getInventory(p));
	}
}


public void setupStyle(String Style, Player p){
	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

	if(playerdata.getString(p.getUniqueId(), "Style").equals(Style)){
		p.sendMessage("§4Fehler: §cDein Pferd hat bereits den Style "+Style+"!");
	}else{
			playerdata.set(p.getUniqueId(), "Style", Style);
			ChangeRidingHorse(p);
			Itemsets.setupStyleMenu(p.getUniqueId(), InventoryManager.getInventory(p));
	}
}

public void setupArmor(String Armor, Player p){	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

	if(playerdata.getString(p.getUniqueId(), "Armor").equals(Armor)){
		p.sendMessage("§4Fehler: §cDein Pferd hat bereits die Rüstung "+Armor+"!");
	}else{
			playerdata.set(p.getUniqueId(), "Armor", Armor);
			ChangeRidingHorse(p);
			Itemsets.setupEquipmentMenu(p.getUniqueId(), InventoryManager.getInventory(p));
	}
}
public void setupType(String Type, Player p){	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

	if(playerdata.getString(p.getUniqueId(), "Type").equals(Type)){
		p.sendMessage("§4Fehler: §cDein Pferd ist bereits ein "+Type+" Pferd!");
	}else{
			playerdata.set(p.getUniqueId(), "Type", Type);
			ChangeRidingHorse(p);
	}
}

public void toggleAdult(Player p){	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

			if(playerdata.getBoolean(p.getUniqueId(), "Adult")==true){
				playerdata.set(p.getUniqueId(), "Adult", false);
			}else{
				playerdata.set(p.getUniqueId(), "Adult", true);
			}
			ChangeRidingHorse(p);
			Itemsets.setupCoordinationMenu(p.getUniqueId(), InventoryManager.getInventory(p));
}
public void ChangeRidingHorse(Player p){
	if(p.getVehicle()instanceof Horse){
		Horse h = (Horse) p.getVehicle();
		try{
		if(h.getCustomName().equalsIgnoreCase("RIDING")){
			if(playerdata.getBoolean(p.getUniqueId(), "Adult")==true){
				h.setAdult();
			}else{
				h.setBaby();
			}
			String color = playerdata.getString(p.getUniqueId(), "Color");
			String style = playerdata.getString(p.getUniqueId(), "Style");
		 	String armor = playerdata.getString(p.getUniqueId(), "Armor");

			switch(style){
			case "BLACK_DOTS": h.setStyle(Style.BLACK_DOTS);
			break;
			case "WHITE_DOTS": h.setStyle(Style.WHITE_DOTS);
			break;
			case "NONE": h.setStyle(Style.NONE);
			break;
			case "WHITEFIELD": h.setStyle(Style.WHITEFIELD);
			break;
			case "WHITE": h.setStyle(Style.WHITE);
			break;
			}

			switch(color){
			case "BROWN": h.setColor(Color.BROWN);
			break;
			case "BLACK": h.setColor(Color.BLACK);
			break;
			case "CHESTNUT": h.setColor(Color.CHESTNUT);
			break;
			case "CREAMY": h.setColor(Color.CREAMY);
			break;
			case "DARK_BROWN": h.setColor(Color.DARK_BROWN);
			break;
			case "GRAY": h.setColor(Color.GRAY);
			break;
			case "WHITE": h.setColor(Color.WHITE);
			break;
			}
			

			switch(armor){
			case "IRON_BARDING": h.getInventory().setArmor(new ItemStack(Material.IRON_BARDING,1));
			break;
			case "GOLD_BARDING": h.getInventory().setArmor(new ItemStack(Material.GOLD_BARDING,1));
			break;
			case "DIAMOND_BARDING": h.getInventory().setArmor(new ItemStack(Material.DIAMOND_BARDING,1));
			break;
			case "NONE": h.getInventory().setArmor(new ItemStack(Material.AIR,1));
			break;
			}
			}
		}catch(Exception ee){
			ee.printStackTrace();
		}
		}
}


		


@EventHandler
public void onInventory(InventoryOpenEvent e){
	  if(e.getPlayer()instanceof Player){
	  if(e.getView().getTitle().equalsIgnoreCase("riding")){
		  Player p = (Player) e.getPlayer();
		  p.performCommand("horseedit");
		  e.setCancelled(true);
		  
	  }
	  }

}
  

  
  
  


}
