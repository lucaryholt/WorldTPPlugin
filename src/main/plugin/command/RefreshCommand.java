package main.plugin.command;

import main.plugin.WorldTPPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RefreshCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                WorldTPPlugin.tpLocations = WorldTPPlugin.databaseHandler.getTPLocations();
                p.sendMessage("Refreshed TPLocations.");
            } else {
                p.sendMessage("You are not op. Not allowed.");
            }
        }

        return true;
    }
}
