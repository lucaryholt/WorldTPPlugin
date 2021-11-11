package main.plugin.handler;

import static main.plugin.WorldTPPlugin.logger;

import main.plugin.model.TPLocation;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DatabaseHandler {

    private Connection connection = null;

    public DatabaseHandler(FileConfiguration config) {
        try {
            connection = DriverManager.getConnection(makeConnectionString(config));
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not establish connection with database. " + e.getMessage());
        }
    }

    public List<TPLocation> getTPLocations() {
        List<TPLocation> tpLocations = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM tpLocations;");

            while (rs.next()) {
                tpLocations.add(new TPLocation(
                        rs.getString("name"),
                        Material.getMaterial(rs.getString("material")),
                        rs.getString("lore"),
                        rs.getDouble("x_location"),
                        rs.getDouble("z_location"),
                        rs.getDouble("y_location"),
                        rs.getString("world")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error querying database. " + e.getMessage());
        }
        return tpLocations;
    }

    private String makeConnectionString(FileConfiguration config) {
        return "jdbc:mysql://"
                + config.get("host")
                + "/"
                + config.get("database")
                + "?user="
                + config.get("user")
                + "&password="
                + config.get("password");
    }

}
