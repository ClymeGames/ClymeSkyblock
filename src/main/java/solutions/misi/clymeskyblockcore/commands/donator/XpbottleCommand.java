package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class XpbottleCommand implements CommandExecutor {

    //> /xpbottle

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.xpbottle")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if(player.getLevel() == 0 && player.getExp() < 7) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You have no Exp that can be converted!");
            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            ClymeSkyblockCore.getInstance().getExperienceUtils().convertExpToBottles(player);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully converted your Exp into XP Bottles!");
        });

        return true;
    }
}