package de.craftersforever.pferd.inventory;

import java.util.ArrayList;
import java.util.List;

import com.gmail.filoghost.hiddenstring.HiddenStringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

class ItemSets {
	private List<ItemStack> selection = new ArrayList<>();
	private List<ItemStack> color = new ArrayList<>();
	private List<ItemStack> style = new ArrayList<>();
	private List<ItemStack> armor = new ArrayList<>();

    ItemSets() {
        setupColorMenu();
        setupStyleMenu();
    	setupSelectionMenu();
        setupEquipmentMenu();
    }

    List<ItemStack> getSelectionMenu(){
        return selection;
    }

    List<ItemStack> getColorMenu(){
        return color;
    }

    List<ItemStack> getStyleMenu(){
        return style;
    }

    List<ItemStack> getArmorMenu(){
        return armor;
    }



    // MUSTER ANFANG
    private void setupStyleMenu() {
        /* 0 = Weißes Abzeichen
         * 1 = Schwarze Pünktchen
         * 2 = Weiße Pünktchen
         * 3 = Weiße Flecken
         * 4 = Luft
         * 5 = Kein Muster
         * 6 = Luft
         * 7 = Luft
         * 8 = Zurück
         */
        style.add(getItemStack(Material.SNOW, "Weißes Abzeichen","Weißes Abzeichen auswählen","setStyleWHITE"));
        style.add(getItemStack(Material.SKELETON_SPAWN_EGG, "Schwarze Pünktchen","Schwarze Pünktchen auswählen","setStyleBLACK_DOTS"));
        style.add(getItemStack(Material.WOLF_SPAWN_EGG, "Weiße Pünktchen","Weiße Pünktchen auswählen", "setStyleWHITE_DOTS"));
        style.add(getItemStack(Material.POLAR_BEAR_SPAWN_EGG, "Weiße Flecken","Weiße Flecken auswählen", "setStyleWHITEFIELD"));
        style.add(getItemStack(Material.AIR, ""));
        style.add(getItemStack(Material.RED_STAINED_GLASS_PANE, "Kein Muster","Kein Muster auswählen", "setStyleNONE"));
        style.add(getItemStack(Material.AIR, ""));
        style.add(getItemStack(Material.AIR, ""));
        style.add(getItemStack(Material.RED_WOOL, "Zurück","Zurück zur Übersicht", "showMenuSelection"));
    }
    // MUSTER ENDE

    // FARBE ANFANG
    private void setupColorMenu() {
        /*
        * 0 = Schwarz
        * 1 = Braun
        * 2 = Nougat
        * 3 = Cremig
        * 4 = Dunkelbraun
        * 5 = Grau
        * 6 = Weiß
        * 7 = Luft
        * 8 = Zurück
         */
        color.add(getItemStack(Material.INK_SAC, "Schwarz", "Schwarz auswählen", "setColorBLACK"));
        color.add(getItemStack(Material.COCOA_BEANS, "Braun", "Braun auswählen", "setColorBROWN"));
        color.add(getItemStack(Material.BROWN_STAINED_GLASS_PANE, "Nougat", "Nougat auswählen", "setColorCHESTNUT"));
        color.add(getItemStack(Material.PUMPKIN_SEEDS, "Cremig","Cremig auswählen", "setColorCREAMY"));
        color.add(getItemStack(Material.MELON_SEEDS, "Dunkelbraun","Dunkelbraun auswählen", "setColorDARK_BROWN"));
        color.add(getItemStack(Material.GRAY_DYE, "Grau","Grau auswählen","setColorGRAY"));
        color.add(getItemStack(Material.SUGAR, "Weiß","Weiß auswählen","setColorWHITE"));
        color.add(getItemStack(Material.AIR, ""));
        color.add(getItemStack(Material.RED_WOOL, "Zurück","Zurück zur Übersicht", "showMenuSelection"));
    }
    //Farbe Ende



    // Armor ANFANG
    private void setupEquipmentMenu() {
        /*
        * 0 = Eisenrüstung
        * 1 = Luft
        * 2 = Goldrüstung
        * 3 = Luft
        * 4 = Diarüstung
        * 5 = Luft
        * 6 = Keine Rüstung
        * 7 = Luft
        * 8 = Zurück
         */

        armor.add(getItemStack(Material.IRON_HORSE_ARMOR, "Eisenrüstung","Eisenrüstung auswählen", "setArmorIRON_HORSE_ARMOR"));
        armor.add(getItemStack(Material.AIR, ""));
        armor.add(getItemStack(Material.GOLDEN_HORSE_ARMOR, "Goldrüstung","Goldrüstung auswählen", "setArmorGOLD_HORSE_ARMOR"));
        armor.add(getItemStack(Material.AIR, ""));
        armor.add(getItemStack(Material.DIAMOND_HORSE_ARMOR, "Diamantrüstung", "Diamandrüstung auswählen", "setArmorDIAMOND_HORSE_ARMOR"));
        armor.add(getItemStack(Material.AIR, ""));
        armor.add(getItemStack(Material.RED_STAINED_GLASS_PANE, "Keine Rüstung", "Keine Rüstung auswählen", "setArmorAIR"));
        armor.add(getItemStack(Material.AIR, ""));
        armor.add(getItemStack(Material.RED_WOOL, "Zurück", "Zurück zur Auswahlseite", "showMenuSelection"));
    }
    // ENDE Armor

    // ANFANG Selection
    private void setupSelectionMenu() {
        /*
         * 0 = Alter toggeln
         * 1 = Luft
         * 2 = Farbe ändern
         * 3 = Luft
         * 4 = Muster ändern
         * 5 = Luft
         * 6 = Rüstung ändern
         * 7 = Luft
         * 8 = Schließen
         */
        selection.add(getItemStack(Material.BONE_MEAL, "Alter ändern", "§aKlicke um dein Pferd zu einem Pony/Pferd zu machen!", "changeAge"));
        selection.add(getItemStack(Material.AIR, ""));
        selection.add(getItemStack(Material.INK_SAC, "Farbe ändern", "§aHier kannst du die Farbe ändern","showMenuColor"));
        selection.add(getItemStack(Material.AIR, ""));
        selection.add(getItemStack(Material.SKELETON_SPAWN_EGG, "Muster ändern", "§aHier kannst du das Muster ändern","showMenuStyle"));
        selection.add(getItemStack(Material.AIR, ""));
        selection.add(getItemStack(Material.DIAMOND_HORSE_ARMOR, "Rüstung ändern","Hier kannst du die Rüstung ändern","showMenuArmor"));
        selection.add(getItemStack(Material.AIR, ""));
        selection.add(getItemStack(Material.RED_WOOL, "Schließen","Schließt das Auswahlmenü","closeInventory"));
    }

    private ItemStack getItemStack(Material material, String stackName){
        ItemStack itemStack = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(stackName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private ItemStack getItemStack(Material material, String stackName, String lore, String hiddenString){
        List<String> itemLore = new ArrayList<>();
        itemLore.add(lore);
        itemLore.add(HiddenStringUtils.encodeString(hiddenString));
        ItemStack itemStack = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(stackName);
        itemMeta.setLore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
