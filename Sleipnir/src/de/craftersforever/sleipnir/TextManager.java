/*
 *            This file is part of LibelulaProtectionBlocks.
 *
 *  LibelulaProtectionBlocks is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  LibelulaProtectionBlocks is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with LibelulaProtectionBlocks.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.craftersforever.sleipnir;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author Diego D'Onofrio <ddonofrio@member.fsf.org>
 */
public class TextManager {

    private final Sleipnir sleipnir;
    private Locale currentLocale;
    private ResourceBundle messages;

    public TextManager(Sleipnir plugin) {
        this.sleipnir = plugin;
        try {
            initialize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws MalformedURLException {
        String lang = sleipnir.getConfig().getString("lang");
        String country = sleipnir.getConfig().getString("country");
        currentLocale = new Locale(lang, country);
        File i18nFolder;
        i18nFolder = new File(sleipnir.getDataFolder(), "lang");
        if (!i18nFolder.exists()) {
            i18nFolder.mkdirs();
        }
        URL[] urls = new URL[]{i18nFolder.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);

        File selectedLang = new File(i18nFolder, "i18n_" + lang.toLowerCase()
                + "_" + country.toUpperCase() + ".properties");
        String langFileName = "lang/" + selectedLang.getName();

        if (sleipnir.getResource(langFileName) != null) {
            sleipnir.saveResource(langFileName, true);
        } else if (!new File(sleipnir.getDataFolder(), langFileName).exists()) {
            Bukkit.getLogger().warning("Invalid configured lang/country, setting to de/DE");
            sleipnir.saveResource("lang/i18n_de_DE.properties", true);
            currentLocale = new Locale("de", "DE");
        }
        messages = ResourceBundle.getBundle("i18n", currentLocale, loader);
        Bukkit.getLogger().info(getText("i18n_selection"));
    }

    public String getText(String text, Object... params) {
        String result = text;
        try {
            result = MessageFormat.format(messages.getString(text), params);
        } catch (MissingResourceException e) {
            Bukkit.getLogger().warning(ChatColor.RED + "Translation not found: \"" + result + "\"");
        } catch (NullPointerException e) {
            Bukkit.getLogger().warning(ChatColor.RED + "Translation not found: \"" + result + "\"");
        }
        return ChatColor.translateAlternateColorCodes('&', result);
    }

    public boolean isInitialized() {
        return messages != null;
    }

}