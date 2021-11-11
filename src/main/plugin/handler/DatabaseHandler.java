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
                        rs.getString("world"),
                        rs.getBoolean("enabled")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error querying database. " + e.getMessage());
        }
        return tpLocations;
    }

    public void insertTPLocation(TPLocation tpLocation) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tpLocations " +
                            "(name, material, lore, x_location, y_location, z_location, world, enabled) " +
                            "VALUES " +
                            "(?, ?, ?, ?, ?, ?, ? ,?);"
            );

            statement.setString(1, tpLocation.getName());
            statement.setString(2, tpLocation.getMaterial().toString());
            statement.setString(3, tpLocation.getLore());
            statement.setDouble(4, tpLocation.getX());
            statement.setDouble(5, tpLocation.getY());
            statement.setDouble(6, tpLocation.getZ());
            statement.setString(7, tpLocation.getWorld());
            statement.setBoolean(8, tpLocation.isEnabled());

            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error querying database. " + e.getMessage());
        }
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
