package main.plugin.handler;

import main.plugin.WorldTPPlugin;
import main.plugin.model.TPLocation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MenuHandler implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() != null) {
            if (e.getView().getTitle().equals(WorldTPPlugin.GUI_TITLE)) {
                for (TPLocation tpLocation : TPLocationsHandler.getTPLocations(p.getName(), true)) {
                    String name0 = tpLocation.getName();
                    String name1 = e.getCurrentItem().getItemMeta().getLocalizedName();

                    if (name0.equals(name1) && tpLocation.isEnabled()) {
                        Location location = new Location(
                                WorldTPPlugin.getPlugin(WorldTPPlugin.class).getServer().getWorld(tpLocation.getWorld()),
                                tpLocation.getX(), tpLocation.getY(), tpLocation.getZ());

                        p.teleport(location);
                        p.sendMessage(ChatColor.YELLOW + "Welcome to " + ChatColor.BOLD + ChatColor.GOLD + tpLocation.getName() + ChatColor.RESET + ChatColor.YELLOW + "!");
                    }
                }
                e.setCancelled(true);
            }
        }
    }

}
