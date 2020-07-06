package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        Location spawn = new Location(Bukkit.getWorld("world"), 75.432, 175.0, 102.524);
        spawn.setYaw((float) -0.7);
        spawn.setPitch((float) -0.0);

        player.teleport(spawn);
        player.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0F, 1.0F);
        player.sendMessage(ClymeSkyblockCore.getInstance().getMessages().getPrefix() + "§aSuccessfully teleported to the spawn..");

        return false;
    }
}
