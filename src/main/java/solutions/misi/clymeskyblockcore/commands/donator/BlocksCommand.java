package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class BlocksCommand implements CommandExecutor {

    //> /blocks

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.blocks")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            boolean itemsCondensed = false;

            if(condenseItems(player, Material.DIAMOND, Material.DIAMOND_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.EMERALD, Material.EMERALD_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.COAL, Material.COAL_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.CHARCOAL, Material.COAL_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.REDSTONE, Material.REDSTONE_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.IRON_INGOT, Material.IRON_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.NETHERITE_INGOT, Material.NETHERITE_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.LAPIS_LAZULI, Material.LAPIS_BLOCK, 9)) itemsCondensed = true;
            if(condenseItems(player, Material.QUARTZ, Material.QUARTZ_BLOCK, 4)) itemsCondensed = true;
            if(condenseItems(player, Material.GLOWSTONE_DUST, Material.GLOWSTONE, 4)) itemsCondensed = true;

            if(itemsCondensed) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully condensed all gems into blocks!");
            } else {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Couldn't find gems to condense!");
            }
        });

        return true;
    }

    private boolean condenseItems(Player player, Material item, Material result, int spliterator) {
        int foundItems = 0;

        for(int i = 0; i < player.getInventory().getSize(); i++) {
            try {
                if(player.getInventory().getItem(i).getType() == item) {
                    foundItems += player.getInventory().getItem(i).getAmount();
                    player.getInventory().getItem(i).setAmount(0);
                }
            } catch(NullPointerException ex) { }
        }

        if(foundItems >= spliterator) {
            int remainingItems = foundItems % spliterator;
            int resultAmount = foundItems / spliterator;

            player.getInventory().addItem(new ItemStack(result, resultAmount));
            player.getInventory().addItem(new ItemStack(item, remainingItems));

            return true;
        }

        return false;
    }
}