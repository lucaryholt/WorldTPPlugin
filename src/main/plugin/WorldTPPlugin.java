package main.plugin;

import main.plugin.command.WTPCommand;
import main.plugin.handler.DatabaseHandler;
import main.plugin.handler.MenuHandler;
import main.plugin.model.TPLocation;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class WorldTPPlugin extends JavaPlugin {

    public static Logger logger;

    public static String GUI_TITLE = ChatColor.BLUE + "Where do you want to go?";
    public static List<TPLocation> tpLocations;
    public static DatabaseHandler databaseHandler;

    @Override
    public void onEnable() {
        logger = getLogger();

        // Read DB config
        FileConfiguration customDatabaseConfig = createCustomConfig("mysqlConfig.yml");

        // Establish DB connection
        databaseHandler = new DatabaseHandler(customDatabaseConfig);

        // Get locations from DB
        tpLocations = databaseHandler.getTPLocations();

        // Enable command
        Objects.requireNonNull(getCommand("wtp")).setExecutor(new WTPCommand());

        // Register event
        getServer().getPluginManager().registerEvents(new MenuHandler(), this);
    }

    private FileConfiguration createCustomConfig(String customConfigFileName) {
        File customConfigFile = new File(getDataFolder(), customConfigFileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource(customConfigFileName, false);
        }

        FileConfiguration customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return customConfig;
    }
}
