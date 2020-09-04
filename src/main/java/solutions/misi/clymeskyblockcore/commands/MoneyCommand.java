package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;

        switch(args.length) {
            case 0:
                //> Usage: /money
                ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player);
                return true;
            case 1:
                //> Usage: /money (player)
                Player target = Bukkit.getPlayer(args[0]);

                if(target == null || !target.isOnline()) {
                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                double targetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.INFO() + " has " + ClymeChatColor.SECONDARY() + "$" + targetBalance);
                return true;
            case 2:
                //> Usage:
                return true;
            default:
                //> Wrong usage
                player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money" + ClymeChatColor.ERROR() + "!");
                return false;
        }
    }
}
