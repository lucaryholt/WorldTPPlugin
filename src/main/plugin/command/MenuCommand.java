package main.plugin.command;

import main.plugin.WorldTPPlugin;
import main.plugin.handler.TPLocationsHandler;
import main.plugin.model.TPLocation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuCommand implements CommandExecutor {

    private List<ItemStack> instanciateItems(String username) {
        List<ItemStack> itemStacks = new ArrayList<>();

        for (TPLocation tpLocation : TPLocationsHandler.getTPLocations(username, true)) {
            if (!tpLocation.isEnabled()) continue;
            ItemStack itemStack = new ItemStack(tpLocation.getMaterial(), 1);

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + tpLocation.getName());
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add(ChatColor.YELLOW + tpLocation.getWorld());
            itemMeta.setLore(itemLore);
            itemMeta.setLocalizedName(tpLocation.getName());

            itemStack.setItemMeta(itemMeta);

            itemStacks.add(itemStack);
        }

        return itemStacks;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            List<ItemStack> itemStacks = instanciateItems(p.getName());

            int inventorySize = inventorySize(itemStacks.size());

            Inventory gui = Bukkit.createInventory(p, inventorySize, WorldTPPlugin.GUI_TITLE);

            if (!itemStacks.isEmpty()) for (ItemStack itemStack : itemStacks) gui.addItem(itemStack);

            p.openInventory(gui);
        }

        return true;
    }

    private int inventorySize(int numberOfItems) {
        double a = numberOfItems / 9;

        return ((int) a + 1) * 9;
    }
}
