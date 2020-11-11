package solutions.misi.clymeskyblockcore.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class TpaCommand implements CommandExecutor {

    //> Usage: /tpa <player>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if (args.length != 1) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/tpa <player>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null || !target.isOnline()) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!");
            return false;
        }

        if(target == player) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can not send yourself a tpa request!");
            return false;
        }

        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);

        if(ClymeSkyblockCore.getInstance().getCommandUtil().getTeleportCache().containsValue(clymeTarget)) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + clymeTarget.getPlayer().getName() +  ClymeChatColor.ERROR() + " already received a tpa request!");
            return false;
        }

        ClymeSkyblockCore.getInstance().getCommandUtil().getTeleportCache().put(clymePlayer, clymeTarget);
        ClymeSkyblockCore.getInstance().getCommandUtil().startExpireScheduler(clymePlayer, clymeTarget);

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have sent a tpa request to " + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + "!");
        clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + player.getName() +  ClymeChatColor.INFO() + " has sent you a teleport request!");
        clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Accept with " + ClymeChatColor.SECONDARY() + "/tpaccept" + ClymeChatColor.INFO() + " or deny with " + ClymeChatColor.SECONDARY() + "/tpdeny" + ClymeChatColor.INFO() + "!");
        return true;
    }
}