package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class SunCommand implements CommandExecutor {

    //> Usage: /sun

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        if(args.length != 0) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/sun" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        switch(playerRank) {
            case "admin":
            case "manager":
            case "owner":
                player.getWorld().setStorm(false);
                player.getWorld().setThundering(false);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have set the weather to " + ClymeChatColor.SECONDARY() + "SUNNY");
                return true;
            default:
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
        }
    }
}