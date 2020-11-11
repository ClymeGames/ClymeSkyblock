package solutions.misi.clymeskyblockcore.commands.message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class ReplyCommand implements CommandExecutor {

    //> Usage: /reply <message>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if (args.length < 1) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/reply <message>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getMessagingCache().get(clymePlayer);

        if(clymeTarget == null) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "There is no player you can reply to!");
            return false;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for(int i = 0; i < args.length; i++) messageBuilder.append(args[i]).append(" ");
        String message = messageBuilder.toString();

        ClymeSkyblockCore.getInstance().getPlayersHandler().getMessagingCache().put(clymePlayer, clymeTarget);
        ClymeSkyblockCore.getInstance().getPlayersHandler().getMessagingCache().put(clymeTarget, clymePlayer);

        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "✉ " + ClymeChatColor.SECONDARY() + "(me ➥ " + ClymeChatColor.INFO() + clymeTarget.getPlayer().getName() + ClymeChatColor.SECONDARY() + ") " + ClymeChatColor.ACCENT() + "» §f" + message);
        clymeTarget.sendMessage(ClymeChatColor.ACCENT() + "✉ " + ClymeChatColor.SECONDARY() + "(" + ClymeChatColor.INFO() + player.getName() + ClymeChatColor.SECONDARY() + " ➥ me) " + ClymeChatColor.ACCENT() + "» §f" + message);
        return true;
    }
}