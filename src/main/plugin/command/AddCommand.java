package main.plugin.command;

import static main.plugin.WorldTPPlugin.logger;

import main.plugin.WorldTPPlugin;
import main.plugin.handler.TPLocationsHandler;
import main.plugin.model.TPLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class AddCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            String chosenName = strings[1];

            List<TPLocation> existingWithName = TPLocationsHandler.getTPLocations(p.getName(), true)
                    .stream().filter(location -> location.getName().equals(chosenName)).collect(Collectors.toList());

            if (existingWithName.isEmpty()) {
                try {
                    Location location = p.getLocation();
                    TPLocation tpLocation = new TPLocation(
                            chosenName,
                            Material.GRASS_BLOCK,
                            location.getX(),
                            location.getZ(),
                            location.getY(),
                            p.getWorld().getName(),
                            true
                    );

                    WorldTPPlugin.databaseHandler.insertTPLocation(tpLocation, p.getName());
                    TPLocationsHandler.refreshLocations();
                    p.sendMessage("Location " + tpLocation.getName() + " was added!");
                } catch (IndexOutOfBoundsException e) {
                    p.sendMessage("Not enough arguments");
                    logger.log(Level.WARNING, "Not enough arguments. " + e.getMessage());
                } catch (NullPointerException | NumberFormatException e) {
                    p.sendMessage("Something went wrong");
                    logger.log(Level.WARNING, "Something went wrong. " + e.getMessage());
                }
            } else {
                p.sendMessage("Location with that name already exists, please choose another");
            }
        }
        return true;
    }
}
