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

public class PvPCommand implements CommandExecutor {

    //> Usage: /pvp

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        Location pvp = new Location(Bukkit.getWorld("pvp"), 351.471, 45.0, 512.798);
        pvp.setYaw((float) -0.2);
        pvp.setPitch((float) -0.0);

        player.teleportAsync(pvp);

        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            if(player.getWorld() == pvp.getWorld() && player.getLocation().distance(pvp) < 5) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully teleported to the PvP Arena..");
            }
        }, 5);

        return true;
    }
}