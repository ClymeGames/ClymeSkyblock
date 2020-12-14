package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class SpeedCommand implements CommandExecutor {

    //> /speed

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.speed")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if(player.getWalkSpeed() == (float) 0.2) {
            player.setFlySpeed((float) 0.2);
            player.setWalkSpeed((float) 0.4);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully enabled Speed!");
        } else {
            player.setFlySpeed((float) 0.1);
            player.setWalkSpeed((float) 0.2);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully disabled Speed!");
        }

        return true;
    }
}