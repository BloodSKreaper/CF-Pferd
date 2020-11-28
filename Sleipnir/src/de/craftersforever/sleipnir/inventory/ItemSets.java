package de.craftersforever.sleipnir.inventory;

import de.craftersforever.sleipnir.Sleipnir;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

class ItemSets {
    private final ItemStack[] selection = new ItemStack[9];
    private final ItemStack[] color = new ItemStack[9];
    private final ItemStack[] style = new ItemStack[9];
    private final ItemStack[] armor = new ItemStack[9];

    ItemSets() {
        setupColorMenu();
        setupStyleMenu();
        setupSelectionMenu();
        setupEquipmentMenu();
    }

    ItemStack[] getSelectionMenu() {
        return selection;
    }

    ItemStack[] getColorMenu() {
        return color;
    }

    ItemStack[] getStyleMenu() {
        return style;
    }

    ItemStack[] getArmorMenu() {
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
        style[0] = getItemStack(Material.SNOW, "Weißes Abzeichen", "Weißes Abzeichen auswählen", "setStyleWHITE");
        style[1] = getItemStack(Material.SKELETON_SPAWN_EGG, "Schwarze Pünktchen", "Schwarze Pünktchen auswählen", "setStyleBLACK_DOTS");
        style[2] = getItemStack(Material.WOLF_SPAWN_EGG, "Weiße Pünktchen", "Weiße Pünktchen auswählen", "setStyleWHITE_DOTS");
        style[3] = getItemStack(Material.POLAR_BEAR_SPAWN_EGG, "Weiße Flecken", "Weiße Flecken auswählen", "setStyleWHITEFIELD");
        style[4] = new ItemStack(Material.AIR);
        style[5] = getItemStack(Material.RED_STAINED_GLASS_PANE, "Kein Muster", "Kein Muster auswählen", "setStyleNONE");
        style[6] = new ItemStack(Material.AIR);
        style[7] = new ItemStack(Material.AIR);
        style[8] = getItemStack(Material.RED_WOOL, "Zurück", "Zurück zur Übersicht", "showMenuSelection");
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
        color[0] = getItemStack(Material.INK_SAC, "Schwarz", "Schwarz auswählen", "setColorBLACK");
        color[1] = getItemStack(Material.COCOA_BEANS, "Braun", "Braun auswählen", "setColorBROWN");
        color[2] = getItemStack(Material.BROWN_STAINED_GLASS_PANE, "Nougat", "Nougat auswählen", "setColorCHESTNUT");
        color[3] = getItemStack(Material.PUMPKIN_SEEDS, "Cremig", "Cremig auswählen", "setColorCREAMY");
        color[4] = getItemStack(Material.MELON_SEEDS, "Dunkelbraun", "Dunkelbraun auswählen", "setColorDARK_BROWN");
        color[5] = getItemStack(Material.GRAY_DYE, "Grau", "Grau auswählen", "setColorGRAY");
        color[6] = getItemStack(Material.SUGAR, "Weiß", "Weiß auswählen", "setColorWHITE");
        color[7] = new ItemStack(Material.AIR);
        color[8] = getItemStack(Material.RED_WOOL, "Zurück", "Zurück zur Übersicht", "showMenuSelection");
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

        armor[0] = getItemStack(Material.IRON_HORSE_ARMOR, "Eisenrüstung", "Eisenrüstung auswählen", "setArmorIRON_HORSE_ARMOR");
        armor[1] = new ItemStack(Material.AIR);
        armor[2] = getItemStack(Material.GOLDEN_HORSE_ARMOR, "Goldrüstung", "Goldrüstung auswählen", "setArmorGOLDEN_HORSE_ARMOR");
        armor[3] = new ItemStack(Material.AIR);
        armor[4] = getItemStack(Material.DIAMOND_HORSE_ARMOR, "Diamantrüstung", "Diamandrüstung auswählen", "setArmorDIAMOND_HORSE_ARMOR");
        armor[5] = new ItemStack(Material.AIR);
        armor[6] = getItemStack(Material.RED_STAINED_GLASS_PANE, "Keine Rüstung", "Keine Rüstung auswählen", "setArmorAIR");
        armor[7] = new ItemStack(Material.AIR);
        armor[8] = getItemStack(Material.RED_WOOL, "Zurück", "Zurück zur Auswahlseite", "showMenuSelection");
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
        selection[0] = getItemStack(Material.BONE_MEAL, "Alter ändern", "§aKlicke um dein Pferd zu einem Fohlen/Ross zu machen!", "changeAge");
        selection[1] = new ItemStack(Material.AIR);
        selection[2] = getItemStack(Material.INK_SAC, "Farbe ändern", "§aHier kannst du die Farbe ändern", "showMenuColor");
        selection[3] = new ItemStack(Material.AIR);
        selection[4] = getItemStack(Material.SKELETON_SPAWN_EGG, "Muster ändern", "§aHier kannst du das Muster ändern", "showMenuStyle");
        selection[5] = new ItemStack(Material.AIR);
        selection[6] = getItemStack(Material.DIAMOND_HORSE_ARMOR, "Rüstung ändern", "§aHier kannst du die Rüstung ändern", "showMenuArmor");
        selection[7] = new ItemStack(Material.AIR);
        selection[8] = getItemStack(Material.RED_WOOL, "Schließen", "Schließt das Auswahlmenü", "closeInventory");
    }


    private ItemStack getItemStack(Material material, String stackName, String lore, String command) {
        List<String> itemLore = new ArrayList<>();
        itemLore.add(lore);
        ItemStack itemStack = new ItemStack(material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(stackName);
            itemMeta.setLore(itemLore);
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(Sleipnir.getInstance(), "command");
            persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, command);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
