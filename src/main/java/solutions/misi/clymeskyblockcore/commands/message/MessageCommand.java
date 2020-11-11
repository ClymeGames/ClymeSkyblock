package solutions.misi.clymeskyblockcore.commands.message;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class MessageCommand implements CommandExecutor {

    //> Usage: /message <player> <message>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if (args.length < 2) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/msg <player> <message>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null || !target.isOnline()) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!");
            return false;
        }

        if(target == player) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can not message yourself!");
            return false;
        }

        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);
        StringBuilder messageBuilder = new StringBuilder();
        for(int i = 1; i < args.length; i++) messageBuilder.append(args[i]).append(" ");
        String message = messageBuilder.toString();

        ClymeSkyblockCore.getInstance().getPlayersHandler().getMessagingCache().put(clymePlayer, clymeTarget);
        ClymeSkyblockCore.getInstance().getPlayersHandler().getMessagingCache().put(clymeTarget, clymePlayer);

        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "✉ " + ClymeChatColor.SECONDARY() + "(me ➥ " + ClymeChatColor.INFO() + target.getName() + ClymeChatColor.SECONDARY() + ") " + ClymeChatColor.ACCENT() + "» §f" + message);
        clymeTarget.sendMessage(ClymeChatColor.ACCENT() + "✉ " + ClymeChatColor.SECONDARY() + "(" + ClymeChatColor.INFO() + player.getName() + ClymeChatColor.SECONDARY() + " ➥ me) " + ClymeChatColor.ACCENT() + "» §f" + message);
        return true;
    }
}