package solutions.misi.clymeskyblockcore.commands.teleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class TpToggleCommand implements CommandExecutor {

    //> /tptoggle

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.tptoggle")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if (ClymeSkyblockCore.getInstance().getCommandUtil().getTpToggle().contains(clymePlayer)) {
            ClymeSkyblockCore.getInstance().getCommandUtil().getTpToggle().remove(clymePlayer);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully enabled teleport requests!");
        } else {
            ClymeSkyblockCore.getInstance().getCommandUtil().getTpToggle().add(clymePlayer);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully disabled teleport requests!");
        }

        return true;
    }
}