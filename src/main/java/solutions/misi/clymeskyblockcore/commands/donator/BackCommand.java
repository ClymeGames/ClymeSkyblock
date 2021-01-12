package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class BackCommand implements CommandExecutor {

    //> /back

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.back")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if(args.length != 0) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/back" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        //> No entry
        if(!ClymeSkyblockCore.getInstance().getCommandUtil().getBackCache().containsKey(clymePlayer)) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "There is no location you can go back to!");
            return false;
        }

        Location backLoc = ClymeSkyblockCore.getInstance().getCommandUtil().getBackCache().get(clymePlayer);
        player.teleportAsync(backLoc);

        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            if(player.getWorld() == backLoc.getWorld() && player.getLocation().distance(backLoc) < 5) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully teleported to your last position..");
            }
        }, 5);
        return true;
    }
}