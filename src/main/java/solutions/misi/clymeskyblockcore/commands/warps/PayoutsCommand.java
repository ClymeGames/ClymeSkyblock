package solutions.misi.clymeskyblockcore.commands.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class PayoutsCommand implements CommandExecutor {

    //> Usage: /payouts

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        Location payouts = new Location(Bukkit.getWorld("world"), 84.448, 171.0, 93.570);
        payouts.setYaw((float) -134.4);
        payouts.setPitch((float) -5.4);

        player.teleportAsync(payouts);

        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            if(player.getWorld() == payouts.getWorld() && player.getLocation().distance(payouts) < 5) {
                player.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0F, 1.0F);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully teleported to the payouts..");
            }
        }, 5);

        return true;
    }
}