package me.chrisochs.cfpferd;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickEventHandler implements Listener{
	private CF_Pferd First;
	public InventoryClickEventHandler(CF_Pferd first){
		First = first;
	}
	
	@EventHandler
	public void onInventoryInteractEvent(InventoryClickEvent e){
		if(e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			
			if(e.getInventory().getName().equals(ChatColor.DARK_BLUE+"Wähle aus:")){
				e.setCancelled(true);
				if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() == true){
				//FARBE
				if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lSchwarz")){
					First.setupColor("BLACK", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Braun")){
					First.setupColor("BROWN", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Nougat")){
					First.setupColor("CHESTNUT", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Cremig")){
					First.setupColor("CREAMY", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Dunkelbraun")){
					First.setupColor("DARK_BROWN", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Grau")){
					First.setupColor("GRAY", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lWeiß")){
					First.setupColor("WHITE", p);
				}
			
			//MUSTER

				else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Weißes Abzeichen")){
					First.setupStyle("WHITE", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Schwarze Pünktchen")){
					First.setupStyle("BLACK_DOTS", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Weiße Pünktchen")){
					First.setupStyle("WHITE_DOTS", p);				
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Weiße Flecken")){
					First.setupStyle("WHITEFIELD", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Kein Muster")){
					First.setupStyle("NONE", p);
				}

			//RÜSTUNG
				else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lEisenrüstung")){
		        	First.setupArmor("IRON_BARDING", p);
		        }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lGoldrüstung")){
		        	First.setupArmor("GOLD_BARDING", p);
			    }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lDiamandrüstung")){
			    	First.setupArmor("DIAMOND_BARDING", p);
				}else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lKeine Rüstung")){
					First.setupArmor("NONE", p);
				}
			//COORDINATION
				else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Alter togglen")){
		        	First.toggleAdult(p);
		        }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Farbe editieren")){
		        	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

		        	First.Itemsets.setupColorMenu(p.getUniqueId(), e.getInventory());

			    }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Muster editieren")){
			    	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

					First.Itemsets.setupStyleMenu(p.getUniqueId(), First.InventoryManager.getInventory(p));

				}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Rüstung editieren")){
					p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

					First.Itemsets.setupEquipmentMenu(p.getUniqueId(), First.InventoryManager.getInventory(p));

			    }
			    //ZURÜCK
			    else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Zurück")){
			    	p.playSound(p.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);

				First.Itemsets.setupCoordinationMenu(p.getUniqueId(), e.getInventory());
				}
				}
			}
			
		}
	}
}
