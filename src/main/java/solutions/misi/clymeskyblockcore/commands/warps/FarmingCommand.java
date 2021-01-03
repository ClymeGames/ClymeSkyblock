package solutions.misi.clymeskyblockcore.commands.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class FarmingCommand implements CommandExecutor {

    //> Usage: /farming

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        Location farming = new Location(Bukkit.getWorld("farming"), -13.316, 130.0, -97.686);
        farming.setYaw((float) 171.7);
        farming.setPitch((float) 4.4);

        player.teleportAsync(farming);

        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            if(player.getWorld() == farming.getWorld() && player.getLocation().distance(farming) < 5) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully teleported to the farming world..");
            }
        }, 5);

        return true;
    }
}