package main.plugin.command;

import static main.plugin.WorldTPPlugin.logger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;
import java.util.logging.Level;

public class WTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            return new MenuCommand().onCommand(commandSender, command, s, strings);
        }

        switch (strings[0].toUpperCase(Locale.ROOT)) {
            case "REFRESH"  :   return new RefreshCommand().onCommand(commandSender, command, s, strings);
            case "ADD"      :   return new AddCommand().onCommand(commandSender, command, s, strings);
        }

        return true;
    }
}
