package main.plugin.handler;

import main.plugin.WorldTPPlugin;
import main.plugin.model.TPLocation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class MenuHandler implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals(WorldTPPlugin.GUI_TITLE)) {
            for (TPLocation tpLocation : WorldTPPlugin.tpLocations) {
                if (Objects.requireNonNull(e.getCurrentItem()).getType().equals(tpLocation.getMaterial())) {
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
