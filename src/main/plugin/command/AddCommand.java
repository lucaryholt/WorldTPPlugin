package main.plugin.command;

import static main.plugin.WorldTPPlugin.logger;

import main.plugin.WorldTPPlugin;
import main.plugin.model.TPLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class AddCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                try {
                    TPLocation tpLocation;

                    if (strings.length == 5) tpLocation = withoutLocationAndMaterial(p, strings);
                    else if (strings.length == 6) tpLocation = withoutLocation(p, strings);
                    else tpLocation = fullObject(strings);

                    WorldTPPlugin.databaseHandler.insertTPLocation(tpLocation);
                    WorldTPPlugin.tpLocations = WorldTPPlugin.databaseHandler.getTPLocations();
                    p.sendMessage("Location " + tpLocation.getName() + " was added!");
                } catch (IndexOutOfBoundsException e) {
                    p.sendMessage("Not enough arguments");
                    logger.log(Level.WARNING, "Not enough arguments. " + e.getMessage());
                } catch (NullPointerException | NumberFormatException e) {
                    p.sendMessage("Something went wrong");
                    logger.log(Level.WARNING, "Something went wrong. " + e.getMessage());
                }
            }
        }
        return true;
    }

    private TPLocation fullObject(String[] strings) {
        String name = strings[1];
        Material material = Material.getMaterial(strings[2]);
        String lore = strings[3];
        double x = Double.parseDouble(strings[4]);
        double y = Double.parseDouble(strings[5]);
        double z = Double.parseDouble(strings[6]);
        String world = strings[7];
        boolean enabled = Boolean.parseBoolean(strings[8]);

        return new TPLocation(
                name,
                material,
                lore,
                x, z, y,
                world,
                enabled
        );
    }

    private TPLocation withoutLocation(Player p, String[] strings) {
        String name = strings[1];
        Material material = Material.getMaterial(strings[2]);
        String lore = strings[3];

        Location location = p.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = strings[4];
        boolean enabled = Boolean.parseBoolean(strings[5]);

        return new TPLocation(
                name,
                material,
                lore,
                x, z, y,
                world,
                enabled
        );
    }

    private TPLocation withoutLocationAndMaterial(Player p, String[] strings) {
        String name = strings[1];
        Material material = Material.GRASS_BLOCK;
        String lore = strings[2];

        Location location = p.getLocation();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = strings[3];
        boolean enabled = Boolean.parseBoolean(strings[4]);

        return new TPLocation(
                name,
                material,
                lore,
                x, z, y,
                world,
                enabled
        );
    }
}
