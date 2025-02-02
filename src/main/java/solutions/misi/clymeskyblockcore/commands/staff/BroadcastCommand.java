package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class BroadcastCommand implements CommandExecutor {

    //> Usage: /broadcast <message>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
            String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

            if(args.length < 1) {
                //> Wrong usage
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/broadcast <message>" + ClymeChatColor.ERROR() + "!");
                return false;
            }

            switch(playerRank) {
                case "admin":
                case "manager":
                case "owner":
                    StringBuilder messageBuilder = new StringBuilder();
                    for(int i = 0; i < args.length; i++) messageBuilder.append(args[i]).append(" ");
                    String message = messageBuilder.toString();

                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, message);
                    return true;
                default:
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                    return false;
            }
        } else {
            if(args.length < 1) {
                //> Wrong usage
                sender.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/broadcast <message>" + ClymeChatColor.ERROR() + "!");
                return false;
            }

            StringBuilder messageBuilder = new StringBuilder();
            for(int i = 0; i < args.length; i++) messageBuilder.append(args[i]).append(" ");
            String message = messageBuilder.toString();

            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, message);
            return true;
        }
    }
}