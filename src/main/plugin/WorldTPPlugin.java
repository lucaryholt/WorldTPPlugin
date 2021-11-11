package main.plugin;

import main.plugin.command.MenuCommand;
import main.plugin.command.RefreshCommand;
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

        FileConfiguration customDatabaseConfig = createCustomConfig("mysqlConfig.yml");

        // Establish Database connection
        databaseHandler = new DatabaseHandler(customDatabaseConfig);

        // Get locations from DB
        tpLocations = databaseHandler.getTPLocations();

        Objects.requireNonNull(getCommand("wtp")).setExecutor(new MenuCommand());
        Objects.requireNonNull(getCommand("wtp-refresh")).setExecutor(new RefreshCommand());

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
