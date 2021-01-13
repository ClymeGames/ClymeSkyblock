package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class EnchantCommand implements CommandExecutor {

    //> Usage: /enchant <enchantment> <level>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        if(args.length != 2) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/enchant <enchantment> <level>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        ItemStack item;

        try {
            item = player.getInventory().getItemInMainHand();

            if(item.getType() == Material.AIR) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You need to hold the item you want to enchant!");
                return false;
            }
        } catch(NullPointerException exception) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You need to hold the item you want to enchant!");
            return false;
        }

        String enchantmentName = args[0];
        Enchantment enchantment;

        try {
            enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName));
        } catch(NullPointerException exception) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "This enchantment is invalid!");
            return false;
        }

        int level;

        try {
            level = Integer.parseInt(args[1]);
        } catch(NumberFormatException exception) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The level has to be a number!");
            return false;
        }

        switch(playerRank) {
            case "admin":
            case "manager":
            case "owner":
                player.getInventory().getItemInMainHand().addUnsafeEnchantment(enchantment, level);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully enchanted " + item.getItemMeta().getDisplayName());
                return true;
            default:
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
        }
    }
}