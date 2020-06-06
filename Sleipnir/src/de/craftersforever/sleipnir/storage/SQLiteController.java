package de.craftersforever.sleipnir.storage;

import de.craftersforever.sleipnir.Sleipnir;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Horse;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;

public class SQLiteController {

    private static Connection connection;
    private static String DB_PATH;

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
            return stmt.executeQuery("SELECT * FROM Playersetting WHERE player_uuid = \"" + uuid.toString()+"\"");
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean setPlayerSetting(UUID uuid, Horse.Color color, Horse.Style style, boolean adult, double speed, double jumpstrength, Material armor) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO Playersetting" +
                    "(player_uuid, color, style, adult, speed, jumpstrength, armor) VALUES " +
                    "(\"" + uuid.toString() + "\", \"" + color.name() + "\", \"" + style.name() + "\", " + adult + ", " + speed + ", " + jumpstrength + ", \"" + armor.toString() + "\")" +
                    "ON CONFLICT(player_uuid) DO UPDATE SET color=\"" + color.name() + "\",style=\"" + style.name() + "\",adult=" + adult + ",speed=" + speed + ",jumpstrength=" + jumpstrength + ",armor=\"" + armor.toString()+"\"");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }

    }

    private boolean createTableIfNotExist() {
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
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
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

    private void handleDB() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS books;");
            stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);");
            stmt.execute("INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', '2001-05-06', '1234', '5.67')");

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");

            ps.setString(1, "Willi Winzig");
            ps.setString(2, "Willi's Wille");
            ps.setDate(3, Date.valueOf("2011-05-16"));
            ps.setInt(4, 432);
            ps.setDouble(5, 32.95);
            ps.addBatch();

            ps.setString(1, "Anton Antonius");
            ps.setString(2, "Anton's Alarm");
            ps.setDate(3, Date.valueOf("2009-10-01"));
            ps.setInt(4, 123);
            ps.setDouble(5, 98.76);
            ps.addBatch();

            connection.setAutoCommit(false);
            ps.executeBatch();
            connection.setAutoCommit(true);

            ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
            while (rs.next()) {
                System.out.println("Autor = " + rs.getString("author"));
                System.out.println("Titel = " + rs.getString("title"));
                System.out.println("Erscheinungsdatum = "
                        + rs.getDate("publication"));
                System.out.println("Seiten = " + rs.getInt("pages"));
                System.out.println("Preis = " + rs.getDouble("price"));
            }
            rs.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Couldn't handle DB-Query");
            e.printStackTrace();
        }
    }
}
