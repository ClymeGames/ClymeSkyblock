package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;
import solutions.misi.clymeskyblockcore.utils.NumberFormatter;

import java.util.Map;

public class SugarcaneCommand implements CommandExecutor {

    //> /sugarcane

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        Player target;
        ClymePlayer clymeTarget;
        String sugarcane;

        switch(args.length) {
            case 0:
                //> Usage: /sugarcane
                sugarcane = NumberFormatter.formatText(clymePlayer.getClymeStatistics().getSugarcaneBroken());
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have broken " + ClymeChatColor.SECONDARY() + sugarcane + ClymeChatColor.INFO() + " sugarcane!");
                return true;
            case 1:
                //> Usage: /sugarcane top
                if(args[0].equalsIgnoreCase("top")) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The players with most broken sugarcane:");

                    int topAmount = 1;
                    for(Map.Entry<String, Long> sugarcaneMap : ClymeSkyblockCore.getInstance().getSugarcaneLeaderboard().getLeaderboard().entrySet()) {
                        sugarcane = NumberFormatter.formatText(sugarcaneMap.getValue());
                        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "#" + topAmount + " " + ClymeChatColor.SECONDARY() + sugarcaneMap.getKey() + ClymeChatColor.INFO() + " - " + ClymeChatColor.SECONDARY() + sugarcane + " sugarcane");
                        topAmount++;
                    }

                    return true;
                }

                //> Usage: /sugarcane (player)
                target = Bukkit.getPlayer(args[0]);

                if(target == null || !target.isOnline()) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);
                sugarcane = NumberFormatter.formatText(clymeTarget.getClymeStatistics().getSugarcaneBroken());

                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.INFO() + " has broken " + ClymeChatColor.SECONDARY() + sugarcane + " sugarcane");
                return true;
            case 2:
                //> Usage: /sugarcane reset (player)
                target = Bukkit.getPlayer(args[1]);

                if(target == null || !target.isOnline()) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                if(!args[0].equalsIgnoreCase("reset")) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/sugarcane" + ClymeChatColor.ERROR() + "!");
                    return false;
                }

                clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);
                clymeTarget.getClymeStatistics().setSugarcaneBroken(0);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully reset the sugarcane statistic of " + ClymeChatColor.SECONDARY() + target.getName());
                return true;
            default:
                //> Wrong usage
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/sugarcane" + ClymeChatColor.ERROR() + "!");
                return false;
        }
    }
}