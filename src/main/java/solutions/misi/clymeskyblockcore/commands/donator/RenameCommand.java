package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class RenameCommand implements CommandExecutor {

    //> /rename <name>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.rename")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if(args.length == 0) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/rename <name>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You aren't holding a renamable item in hand!");
            return false;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for (String arg : args) nameBuilder.append(arg).append(" ");
        String itemName = nameBuilder.toString();

        if(ChatColor.stripColor(itemName).length() > 32) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "This name is too long! Please try something else");
            return false;
        }

        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if(player.isOp()) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', itemName));
        } else {
            itemMeta.setDisplayName(itemName);
        }
        player.getInventory().getItemInMainHand().setItemMeta(itemMeta);
        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully renamed the item in your hand");
        return true;
    }
}