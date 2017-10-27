package me.chrisochs.cfpferd;

import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class HorseSaveListeners implements Listener{

	
	@EventHandler
	public void OnExit(VehicleExitEvent e){
		
		if(e.getVehicle()instanceof Horse){
			try{
			Horse h = (Horse) e.getVehicle();
			if(h.getCustomName() != null&&h.getCustomName().equalsIgnoreCase("RIDING")){
				e.setCancelled(false);
				if(e.getExited() instanceof Player){
				h.remove();
				}
			}
			}
			catch(NullPointerException ee){
				  ee.printStackTrace();}
		}else if(e.getVehicle()instanceof Boat){
			try{
			Boat b = (Boat) e.getVehicle();
			if(b.getCustomName() != null && b.getCustomName().equalsIgnoreCase("RIDING")){
				e.setCancelled(false);
				if(e.getExited() instanceof Player&&b.getPassengers().size() !=2){
				b.remove();
				}
			}
			}
			catch(NullPointerException ee){
				  ee.printStackTrace();}
		}
		
		else{
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRename(PlayerInteractEntityEvent e){
		if((e.getRightClicked() instanceof Horse)&&e.getPlayer().getItemInHand().getType() == Material.NAME_TAG){
		   Horse h= (Horse) e.getRightClicked();
		   try{
		   if(h.getCustomName().equalsIgnoreCase("RIDING")||e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("riding")){
			   e.setCancelled(true);
			   e.getPlayer().sendMessage("§4Fehler: §cUmbenennung nicht möglich!");
		   }
		   }catch(NullPointerException ee){
				  ee.printStackTrace();}
		}
	}


	  


	  
	  
	  @EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e){
		  if(e.getPlayer() instanceof Player){
			  Player p = (Player) e.getPlayer();
			  if(p.getVehicle() instanceof Horse||p.getVehicle() instanceof Boat){
					  if(p.getVehicle().getCustomName().equalsIgnoreCase("RIDING")){
						  p.leaveVehicle();
					  }
			  }
			  
		  }
	  }
	  @EventHandler
	  public void onPlayerDeath(PlayerDeathEvent e){
		  if(e.getEntity() instanceof Player){
			  Player p = (Player) e.getEntity();
			  if(p.getVehicle() instanceof Horse || p.getVehicle() instanceof Boat){
					  if(p.getVehicle().getCustomName() != null&&p.getVehicle().getCustomName().equalsIgnoreCase("RIDING")){
						  p.leaveVehicle();
					  }
			  }
		  }
	  }

	  
	  @EventHandler
	  public void Leine(PlayerLeashEntityEvent e){
		  if(e.getEntity()instanceof Horse){
			  try{
				  Horse h = (Horse) e.getEntity();
					  if(!(h.getCustomName().equalsIgnoreCase("RIDING"))){
						  
					  }else{e.setCancelled(true);}
				  
				  }
		  catch(Exception ee){
			  ee.printStackTrace();
		  }
		  
		  }
	  }
	  
	  

	  

	  

}
