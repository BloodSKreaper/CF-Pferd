package me.chrisochs.cfpferd;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Itemsets {
	private playerdata playerdata;
	
	public Itemsets(playerdata data){
		playerdata = data;
	}
	
	//MUSTER ANFANG
	
	public void setupStyleMenu(UUID uuid, Inventory inv){
		List<String> actual = new ArrayList<String>();
		actual.add("§4Aktuell");
		String style = playerdata.getString(uuid, "Style");

		ItemStack istack0 = new ItemStack(Material.SNOW_BALL);
		ItemMeta istackMeta0 = istack0.getItemMeta();
		if(style.equals("WHITE")){
		istackMeta0.setLore(actual);
		}
		istackMeta0.setDisplayName("§2§lWeißes Abzeichen");
		istack0.setItemMeta(istackMeta0);
	
		ItemStack istack1 = new ItemStack(Material.MONSTER_EGG,1,(short)51);
		ItemMeta istackMeta1 = istack1.getItemMeta();
		if(style.equals("BLACK_DOTS")){
		istackMeta1.setLore(actual);
		}
		istackMeta1.setDisplayName("§2§lSchwarze Pünktchen");
		istack1.setItemMeta(istackMeta1);
		
		ItemStack istack2= new ItemStack(Material.MONSTER_EGG,1,(short)56);
		ItemMeta istackMeta2 = istack2.getItemMeta();
		if(style.equals("WHITE_DOTS")){
		istackMeta2.setLore(actual);
		}
		istackMeta2.setDisplayName("§2§lWeiße Pünktchen");
		istack2.setItemMeta(istackMeta2);

		ItemStack istack3= new ItemStack(Material.MONSTER_EGG,1,(short)0);
		ItemMeta istackMeta3 = istack3.getItemMeta();
		if(style.equals("WHITEFIELD")){
		istackMeta3.setLore(actual);
		}
		istackMeta3.setDisplayName("§2§lWeiße Flecken");
		istack3.setItemMeta(istackMeta3);
		
		ItemStack istack4= new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14);
		ItemMeta istackMeta4 = istack4.getItemMeta();
		if(style.equals("NONE")){
		istackMeta4.setLore(actual);
		}
		istackMeta4.setDisplayName("§c§lKein Muster");
		istack4.setItemMeta(istackMeta4);
		
		ItemStack istack8= new ItemStack(Material.WOOL,1,(short)14);
		ItemMeta istackMeta8 = istack8.getItemMeta();
		istackMeta8.setDisplayName("§4§lZurück");
		istack8.setItemMeta(istackMeta8);
		
		inv.clear();;
		inv.setItem(0, istack0);
		inv.setItem(1, istack1);
		inv.setItem(2, istack2);
		inv.setItem(3, istack3);
		inv.setItem(6, istack4);
		inv.setItem(8, istack8);
		
	}
	//MUSTER ENDE
	
	
	//FARBE ANFANG
	public void setupColorMenu(UUID uuid, Inventory inv){
		List<String> actual = new ArrayList<String>();
		actual.add("§4Aktuell");
		String color = playerdata.getString(uuid, "Color");
				
		//Schwarz auf Slot 0
		//Braun auf Slot 1
		//Nougat auf Slot 2
		//Cremig auf Slot 3
		//Dunkelbraun auf Slot 4
		//Grau auf Slot 5
		//Weiß auf Slot 6
		//Zurück auf Slot 7
		//Überspringen auf Slot 8
		
		//Schwarz
		ItemStack istack0 = new ItemStack(Material.INK_SACK, 1);
		ItemMeta istackMeta0 = istack0.getItemMeta();
		if(color.equals("BLACK")){
		istackMeta0.setLore(actual);
		}
		istackMeta0.setDisplayName("§2§lSchwarz");
		istack0.setItemMeta(istackMeta0);
		//Braun
		ItemStack istack1 = new ItemStack(Material.INK_SACK, 1, (short)3);
		ItemMeta istackMeta1 = istack1.getItemMeta();
		if(color.equals("BROWN")){
		istackMeta1.setLore(actual);
		}
		istackMeta1.setDisplayName("§2§lBraun");
		istack1.setItemMeta(istackMeta1);
	    //Nougat
		ItemStack istack2 = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)12);
		ItemMeta istackMeta2 = istack2.getItemMeta();
		if(color.equals("CHESTNUT")){
		istackMeta2.setLore(actual);
		}
		istackMeta2.setDisplayName("§2§lNougat");
		istack2.setItemMeta(istackMeta2);
		//Cremig
		ItemStack istack3 = new ItemStack(Material.PUMPKIN_SEEDS);
		ItemMeta istackMeta3 = istack3.getItemMeta();
		if(color.equals("CREAMY")){
		istackMeta3.setLore(actual);
		}
		istackMeta3.setDisplayName("§2§lCremig");
		istack3.setItemMeta(istackMeta3);
		//Dunkelbraun
		ItemStack istack4 = new ItemStack(Material.MELON_SEEDS);
		ItemMeta istackMeta4 = istack4.getItemMeta();
		if(color.equals("DARK_BROWN")){
		istackMeta4.setLore(actual);
		}
		istackMeta4.setDisplayName("§2§lDunkelbraun");
		istack4.setItemMeta(istackMeta4);
		//Grau
		ItemStack istack5 = new ItemStack(Material.INK_SACK, 1, (short)8);
		ItemMeta istackMeta5 = istack5.getItemMeta();
		if(color.equals("GRAY")){
		istackMeta5.setLore(actual);
		}
		istackMeta5.setDisplayName("§2§lGrau");
		istack5.setItemMeta(istackMeta5);
		//Weiß
		ItemStack istack6 = new ItemStack(Material.SUGAR);
		ItemMeta istackMeta6 = istack6.getItemMeta();
		if(color.equals("WHITE")){
		istackMeta6.setLore(actual);
		}
		istackMeta6.setDisplayName("§2§lWeiß");
		istack6.setItemMeta(istackMeta6);
        //Zurück
		ItemStack istack8= new ItemStack(Material.WOOL,1,(short)14);
		ItemMeta istackMeta8 = istack8.getItemMeta();
		istackMeta8.setDisplayName("§4§lZurück");
		istack8.setItemMeta(istackMeta8);
		
		inv.clear();
		inv.setItem(0, istack0);
		inv.setItem(1, istack1);
		inv.setItem(2, istack2);
		inv.setItem(3, istack3);
		inv.setItem(4, istack4);
		inv.setItem(5, istack5);
		inv.setItem(6, istack6);
		inv.setItem(8, istack8);
	}
	//FARBE ENDE

	
	//EQUIPMENT ANFANG
	public void setupEquipmentMenu(UUID uuid, Inventory inv){
		List<String> actual = new ArrayList<String>();
		actual.add("§4Aktuell");
		String style = playerdata.getString(uuid, "Armor");
		
		ItemStack istack0 = new ItemStack(Material.IRON_BARDING);
		ItemMeta istackMeta0 = istack0.getItemMeta();
		if(style.equals("IRON_BARDING")){
		istackMeta0.setLore(actual);
		}
		istackMeta0.setDisplayName("§2§lEisenrüstung");
		istack0.setItemMeta(istackMeta0);
	
		ItemStack istack2 = new ItemStack(Material.GOLD_BARDING);
		ItemMeta istackMeta2 = istack2.getItemMeta();
		if(style.equals("GOLD_BARDING")){
		istackMeta2.setLore(actual);
		}
		istackMeta2.setDisplayName("§2§lGoldrüstung");
		istack2.setItemMeta(istackMeta2);
		
		ItemStack istack4= new ItemStack(Material.DIAMOND_BARDING);
		ItemMeta istackMeta4 = istack4.getItemMeta();
		if(style.equals("DIAMOND_BARDING")){
		istackMeta4.setLore(actual);
		}
		istackMeta4.setDisplayName("§2§lDiamandrüstung");
		istack4.setItemMeta(istackMeta4);
		
		ItemStack istack6= new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14);
		ItemMeta istackMeta6 = istack6.getItemMeta();
		if(style.equals("NONE")){
		istackMeta6.setLore(actual);
		}
		istackMeta6.setDisplayName("§c§lKeine Rüstung");
		istack6.setItemMeta(istackMeta6);

		ItemStack istack8= new ItemStack(Material.WOOL,1,(short)14);
		ItemMeta istackMeta8 = istack8.getItemMeta();
		istackMeta8.setDisplayName("§4§lZurück");
		istack8.setItemMeta(istackMeta8);

		inv.clear();
		inv.setItem(0, istack0);
		inv.setItem(2, istack2);
		inv.setItem(4, istack4);
		inv.setItem(6, istack6);
		inv.setItem(8, istack8);
	}
	//ENDE RÜSTUNG
	
	//ANFANG COORDNIATION

    public void setupCoordinationMenu(UUID uuid, Inventory inv){
    	List<String> Farbe = new ArrayList<String>();
    	List<String> Muster = new ArrayList<String>();
    	List<String> Rüstung = new ArrayList<String>();
        List<String> Adult = new ArrayList<String>();
		Farbe.add("§aHier kannst du die §6Farbe ändern!");
		Muster.add("§aHier kannst du das §6Muster ändern!");		
		Rüstung.add("§aHier kannst du die §6Rüstung ändern!");
		Adult.add("§aHier kannst du das Alter ändern!");
		if(playerdata.getBoolean(uuid, "Adult")==true){
			Adult.add("§aKlicke um dein Pferd zu einem §6Fohlen §azu machen!");
		}else{
			Adult.add("§aKlicke um dein Pferd zu einem §6Pferd §azu machen!");
		}
		
		//Farbe editieren auf Slot 2
		//Muster editieren auf Slot 4
		//Rüstung editieren auf Slot 6
		//Art editieren auf Slot 8
			ItemStack istack0 = new ItemStack(Material.INK_SACK, 1,(short) 15);
			ItemMeta istackMeta0 = istack0.getItemMeta();
			istackMeta0.setLore(Adult);
			istackMeta0.setDisplayName("§2§lAlter togglen");
			istack0.setItemMeta(istackMeta0);
			
			ItemStack istack2 = new ItemStack(Material.INK_SACK);
			ItemMeta istackMeta2 = istack2.getItemMeta();
			istackMeta2.setLore(Farbe);
			istackMeta2.setDisplayName("§2§lFarbe editieren");
			istack2.setItemMeta(istackMeta2);
			
			ItemStack istack4= new ItemStack(Material.MONSTER_EGG,1);
			ItemMeta istackMeta4 = istack4.getItemMeta();
			istackMeta4.setLore(Muster);
			istackMeta4.setDisplayName("§2§lMuster editieren");
			istack4.setItemMeta(istackMeta4);

			ItemStack istack6= new ItemStack(Material.DIAMOND_BARDING);
			ItemMeta istackMeta6 = istack6.getItemMeta();
			istackMeta6.setLore(Rüstung);
			istackMeta6.setDisplayName("§2§lRüstung editieren");
			istack6.setItemMeta(istackMeta6);

			/*ItemStack istack8= new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
			ItemMeta istackMeta8 = istack8.getItemMeta();
			istackMeta8.setLore(Art);
			istackMeta8.setDisplayName("§2§lArt editieren");
			istack8.setItemMeta(istackMeta8);*/

			inv.clear();
			inv.setItem(2, istack2);
			inv.setItem(4, istack4);
			inv.setItem(6, istack6);
			inv.setItem(0, istack0);
			//inv.setItem(8, istack8);
			}
}	
