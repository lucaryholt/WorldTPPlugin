package main.plugin.command;

import main.plugin.WorldTPPlugin;
import main.plugin.model.TPLocation;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MenuCommand implements CommandExecutor {

    private List<ItemStack> instanciateItems(boolean isOp) {
        List<ItemStack> itemStacks = new ArrayList<>();

        for (TPLocation tpLocation : WorldTPPlugin.tpLocations) {
            if (!isOp && !tpLocation.isEnabled()) continue;
            ItemStack itemStack = new ItemStack(tpLocation.getMaterial(), 1);

            if (!tpLocation.isEnabled()) {
                itemStack = new ItemStack(Material.IRON_NUGGET, 1);
            }

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + tpLocation.getName());
            ArrayList<String> itemLore = new ArrayList<>();
            itemLore.add(ChatColor.YELLOW + tpLocation.getLore());
            itemMeta.setLore(itemLore);

            itemStack.setItemMeta(itemMeta);

            itemStacks.add(itemStack);
        }

        return itemStacks;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            List<ItemStack> itemStacks = instanciateItems(p.isOp());

            int inventorySize = Math.max(itemStacks.size(), 9);

            Inventory gui = Bukkit.createInventory(p, inventorySize, WorldTPPlugin.GUI_TITLE);

            for (ItemStack itemStack : itemStacks) gui.addItem(itemStack);

            p.openInventory(gui);
        }

        return true;
    }
}
