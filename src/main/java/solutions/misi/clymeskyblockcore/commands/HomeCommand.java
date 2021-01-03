package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class HomeCommand implements CommandExecutor {

    //> Usage: /home

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        //> Open GUI
        if(args.length == 0) {
            ClymeSkyblockCore.getInstance().getHomeGUI().open(player);
            return true;
        }

        if(args.length != 1) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/home <name>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        String homeName = args[0];

        Location home = ClymeSkyblockCore.getInstance().getDataManager().getClymeHomesTable().getPlayerHomeFromName(player, homeName);

        if(home == null) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You don't have a home called " + ClymeChatColor.SECONDARY() + homeName);
            return false;
        }

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully teleported to " + ClymeChatColor.SECONDARY() + homeName  + ClymeChatColor.SUCCESS() + "..");
        player.teleportAsync(home);
        return true;
    }
}