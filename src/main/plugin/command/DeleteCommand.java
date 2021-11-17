package main.plugin.command;

import main.plugin.WorldTPPlugin;
import main.plugin.handler.TPLocationsHandler;
import main.plugin.model.TPLocation;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            String locationName = strings[1];
            List<TPLocation> matchingLocations = TPLocationsHandler
                    .getTPLocations(p.getName(), false).stream()
                    .filter(location -> location.getName().equals(locationName)).collect(Collectors.toList());

            if (!matchingLocations.isEmpty()) {
                WorldTPPlugin.databaseHandler.deleteTPLocation(p.getName(), locationName);
                TPLocationsHandler.refreshLocations();
                p.sendMessage("Location " + locationName + " was deleted");
            } else {
                p.sendMessage("Could not find location " + locationName);
            }
        }

        return false;
    }
}
