package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NicknameCommand implements CommandExecutor {

    //> /nickname (nick)

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.nickname")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        //> Show current nickname
        if(args.length == 0) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Your current nickname is " + ClymeChatColor.SECONDARY() + clymePlayer.getNickname());
            return true;
        }

        //> Change nickname
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("none")) {
                clymePlayer.setNickname(clymePlayer.getUsername());
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully disabled your nickname!");
                return true;
            }

            String nick = args[0];
            if(!player.hasPermission("clymegames.nickname.colors")) nick = ChatColor.stripColor(args[0]);
            String regex = "^[a-zA-Z0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(ChatColor.stripColor(nick));

            //> Not alphanumeric
            if(!matcher.matches()) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can only use alphanumeric characters!");
                return false;
            }

            //> Max. Nick length
            if(ChatColor.stripColor(nick).length() < 4 && ChatColor.stripColor(nick).length() > 20) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Your nickname must have 4 - 20 characters!");
                return false;
            }

            clymePlayer.setNickname(nick);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully changed your nickname to " + ClymeChatColor.SECONDARY() + nick);
            return true;
        }

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/nick <player>" + ClymeChatColor.ERROR() + "!");
        return false;
    }
}