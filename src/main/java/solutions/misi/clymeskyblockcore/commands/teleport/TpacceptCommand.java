package solutions.misi.clymeskyblockcore.commands.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class TpacceptCommand implements CommandExecutor {

    //> Usage: /tpaccept

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if (args.length != 0) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/tpaccept" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        if(!ClymeSkyblockCore.getInstance().getCommandUtil().getTeleportCache().containsValue(clymePlayer) && !ClymeSkyblockCore.getInstance().getCommandUtil().getTeleportHereCache().containsValue(clymePlayer)) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "There is no tpa request you can accept!");
            return false;
        }

        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getCommandUtil().getTarget(clymePlayer);

        if(clymeTarget != null) {
            player.teleportAsync(clymeTarget.getPlayer().getLocation());
            ClymeSkyblockCore.getInstance().getCommandUtil().getTeleportHereCache().remove(clymeTarget);

            clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + player.getName() + ClymeChatColor.SUCCESS() + " has accepted your tpa request!");
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have accepted " + ClymeChatColor.SECONDARY() + clymeTarget.getPlayer().getName() + "'s" + ClymeChatColor.SUCCESS() + " tpa request!");
        }

        return true;
    }
}