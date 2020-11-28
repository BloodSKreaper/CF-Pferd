package de.craftersforever.sleipnir.storage;

import de.craftersforever.sleipnir.Sleipnir;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

public class MySQLController extends StorageDriver {

    private Connection connection;
    private String host, database, user, password;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    public MySQLController(Sleipnir plugin) {
        host = plugin.getConfig().getString("mysql.host");
        database = plugin.getConfig().getString("mysql.database");
        user = plugin.getConfig().getString("mysql.user");
        password = plugin.getConfig().getString("mysql.password");
        if (initDBConnection()) {
            createTableIfNotExist();
            disconnectDatabase();
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "Connection to mysql database " + database + " for " + user + "@" + host + " not possible. Disabling Sleipnir");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    private void disconnectDatabase() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getPlayerSettings(UUID uuid) {
        if (initDBConnection()) {
            try {
                Statement stmt = connection.createStatement();
                return stmt.executeQuery("SELECT * FROM Playersetting WHERE player_uuid = \"" + uuid.toString() + "\"");
            } catch (
                    SQLException exception) {
                exception.printStackTrace();
                return null;
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "Connection to mysql database " + database + " for " + user + "@" + host + " not possible.");
            return null;
        }
    }


    public void setPlayerSetting(UUID uuid, Horse.Color color, Horse.Style style, boolean adult, double speed, double jumpstrength, Material armor) {
        if(initDBConnection()) {
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate("INSERT INTO Playersetting" +
                        "(player_uuid, color, style, adult, speed, jumpstrength, armor) VALUES " +
                        "(\"" + uuid.toString() + "\", \"" + color.name() + "\", \"" + style.name() + "\", " + adult + ", " + speed + ", " + jumpstrength + ", \"" + armor.toString() + "\")" +
                        "ON DUPLICATE KEY UPDATE color=\"" + color.name() + "\",style=\"" + style.name() + "\",adult=" + adult + ",speed=" + speed + ",jumpstrength=" + jumpstrength + ",armor=\"" + armor.toString() + "\"");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "Connection to mysql database " + database + " for " + user + "@" + host + " not possible.");
        }

    }

    private void createTableIfNotExist() {
        /*
        *primary_key*, _secondary_key_
        tables:
        Playersetting(*player_uuid*, color, style, armor,  madult (boolean), speed (double), jumpstrength (double))

         */
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Playersetting (\n" +
                    "  player_uuid VARCHAR(36) NOT NULL UNIQUE PRIMARY KEY,\n" +
                    "  color TEXT,\n" +
                    "  style TEXT,\n" +
                    "  armor TEXT,\n" +
                    "  adult BOOLEAN,\n" +
                    "  speed DOUBLE,\n" +
                    "  jumpstrength DOUBLE\n" +
                    ");");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private boolean initDBConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionCommand =
                    "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password;
            connection = DriverManager.getConnection(connectionCommand);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
