package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class ClearinventoryCommand implements CommandExecutor {

    //> /clearinventory [player]

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(args.length >= 2) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/clearinventory" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        //> /clearinventory (player)
        if(args.length == 1) {
            if(!player.hasPermission("clymegames.clearinventory.others")) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null || !target.isOnline()) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                return false;
            }

            target.getInventory().clear();
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully cleared " + ClymeChatColor.SECONDARY() + target.getName() + "'s " + ClymeChatColor.SUCCESS() + "inventory!");
            return true;
        }

        //> /clearinventory
        if(!player.hasPermission("clymegames.clearinventory")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if(ClymeSkyblockCore.getInstance().getCombatLog().getInCombat().containsKey(clymePlayer)) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can't clear your inventory during Combat!");
            return false;
        }

        player.getInventory().clear();
        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully cleared your inventory!");
        return true;
    }
}