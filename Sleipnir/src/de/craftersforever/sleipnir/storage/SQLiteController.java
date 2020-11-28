package de.craftersforever.sleipnir.storage;

import de.craftersforever.sleipnir.Sleipnir;
import org.bukkit.Material;
import org.bukkit.entity.Horse;

import java.sql.*;
import java.util.UUID;

public class SQLiteController extends StorageDriver{

    private Connection connection;
    private String DB_PATH;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    public SQLiteController(Sleipnir plugin) {
        DB_PATH = plugin.getDataFolder() + "/playersettings.db";
        initDBConnection();
        createTableIfNotExist();
    }

    public ResultSet getPlayerSettings(UUID uuid) {
        try {
            Statement stmt = connection.createStatement();
            return stmt.executeQuery("SELECT * FROM Playersetting WHERE player_uuid = \"" + uuid.toString() + "\"");
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void setPlayerSetting(UUID uuid, Horse.Color color, Horse.Style style, boolean adult, double speed, double jumpstrength, Material armor) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO Playersetting" +
                    "(player_uuid, color, style, adult, speed, jumpstrength, armor) VALUES " +
                    "(\"" + uuid.toString() + "\", \"" + color.name() + "\", \"" + style.name() + "\", " + adult + ", " + speed + ", " + jumpstrength + ", \"" + armor.toString() + "\")" +
                    "ON CONFLICT(player_uuid) DO UPDATE SET color=\"" + color.name() + "\",style=\"" + style.name() + "\",adult=" + adult + ",speed=" + speed + ",jumpstrength=" + jumpstrength + ",armor=\"" + armor.toString() + "\"");
        } catch (SQLException exception) {
            exception.printStackTrace();
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
                    "  player_uuid TEXT NOT NULL UNIQUE PRIMARY KEY,\n" +
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

    private void initDBConnection() {
        try {
            if (connection != null)
                return;
            System.out.println("Creating Connection to Database...");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
                System.out.println("...Connection established");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                            System.out.println("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
