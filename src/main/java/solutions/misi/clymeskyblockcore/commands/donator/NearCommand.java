package solutions.misi.clymeskyblockcore.commands.donator;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class NearCommand implements CommandExecutor {

    //> /near

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!player.hasPermission("clymegames.near")) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        Location playerLocation = player.getLocation();
        Map<Player, Integer> nearbyPlayers = new HashMap<>();

        for(Player target : Bukkit.getOnlinePlayers()) {
            if(target == player) continue;
            if(target.getWorld() != player.getWorld() || target.getLocation().distance(playerLocation) > 50) continue;
            if(target.hasPermission("clymegames.near.bypass")) continue;

            nearbyPlayers.put(target, (int) Math.round(target.getLocation().distance(playerLocation)));
        }

        if(nearbyPlayers.isEmpty()) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "No players are nearby!");
            return true;
        }

        //> Sort map
        Object[] nearbyPlayersArray = nearbyPlayers.entrySet().toArray();
        Arrays.sort(nearbyPlayersArray, (Comparator) (o1, o2) -> ((Map.Entry<Player, Integer>) o2).getValue().compareTo(((Map.Entry<Player, Integer>) o1).getValue()));
        ArrayUtils.reverse(nearbyPlayersArray);

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The following players are nearby:");
        for(Object nearbyPlayer : nearbyPlayersArray) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix()
                    + ClymeChatColor.SECONDARY()
                    + ((Map.Entry<Player, Integer>) nearbyPlayer).getKey().getName()
                    + ClymeChatColor.ACCENT()
                    + " (" + ((Map.Entry<Player, Integer>) nearbyPlayer).getValue() + "m)");
        }

        return true;
    }
}