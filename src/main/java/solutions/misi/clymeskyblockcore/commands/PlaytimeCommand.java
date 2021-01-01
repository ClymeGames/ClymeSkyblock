package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class PlaytimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        ClymePlayer clymeTarget;
        Player target;
        long hours;
        long minutes;

        switch(args.length) {
            case 0:
                //> Usage: /playtime
                hours = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 / 60;
                minutes = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 % 60;

                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Your playtime: " + ClymeChatColor.SECONDARY() + hours + " hours " + ClymeChatColor.INFO() + "and " + ClymeChatColor.SECONDARY() + minutes + " minutes");
                return true;
            case 1:
                //> Usage: /playtime top
                if(args[0].equalsIgnoreCase("top")) {
                    String commandCooldownFormat = player.getName() + ":playtimetop";

                    //> Check cooldown
                    if(ClymeSkyblockCore.getInstance().getCommandUtil().getCommandCooldowns().contains(commandCooldownFormat)) {
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Please wait! This command has a " + ClymeChatColor.SECONDARY() + "1m " + ClymeChatColor.ERROR() + "cooldown");
                        return false;
                    }

                    ClymeSkyblockCore.getInstance().getCommandUtil().startCommandCooldown(clymePlayer, "playtimetop", 1);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Loading playtime of all players..");
                    ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getAllUniquePlayers(clymePlayer);
                    return true;
                }

                //> Usage: /playtime (player)
                target = Bukkit.getPlayer(args[0]);

                if(target == null || !target.isOnline()) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                hours = (target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 / 60;
                minutes = (target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 % 60;

                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + "'s" + ClymeChatColor.INFO() + " playtime: " + ClymeChatColor.SECONDARY() + hours + " hours " + ClymeChatColor.INFO() + "and " + ClymeChatColor.SECONDARY() + minutes + " minutes");
                return true;
            case 2:
                //> Usage: /playtime reset (player)
                target = Bukkit.getPlayer(args[1]);

                if(target == null || !target.isOnline()) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                if(!args[0].equalsIgnoreCase("reset")) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/playtime" + ClymeChatColor.ERROR() + "!");
                    return false;
                }

                target.setStatistic(Statistic.PLAY_ONE_MINUTE, 0);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully reset the playtime of " + ClymeChatColor.SECONDARY() + target.getName());
                return true;
            default:
                //> Wrong usage
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/playtime" + ClymeChatColor.ERROR() + "!");
                return false;
        }
    }
}