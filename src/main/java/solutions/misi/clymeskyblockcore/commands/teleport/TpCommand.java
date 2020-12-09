package solutions.misi.clymeskyblockcore.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class TpCommand implements CommandExecutor {

    //> Usage: /tp <player> [player]

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        //> Permission check
        switch(playerRank) {
            case "mod":
            case "seniormod":
            case "admin":
            case "manager":
            case "owner":
                break;
            default:
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
        }

        if (args.length != 1 && args.length != 2) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/tp <player> [player]" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
            return false;
        }

        if (target == player) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can not teleport to yourself!");
            return false;
        }

        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);

        //> /tp <player> <player>
        if (args.length == 2) {
            Player target2 = Bukkit.getPlayer(args[1]);

            if (target2 == null || !target2.isOnline()) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!");
                return false;
            }

            target.teleportAsync(target2.getLocation());

            Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                if(target.getLocation().distance(target2.getLocation()) < 5) {
                    clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have been teleported to " + ClymeChatColor.SECONDARY() + target2.getName());
                    target.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0F, 1.0F);
                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have teleported " + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " to " + ClymeChatColor.SECONDARY() + target2);
                } else {
                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.ERROR() + " could not get teleported");
                }
            }, 5);

            return true;
        }

        //> /tp <player>
        player.teleportAsync(target.getLocation());

        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            if(player.getLocation().distance(target.getLocation()) < 5) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have been teleported to " + ClymeChatColor.SECONDARY() + target.getName());
                player.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0F, 1.0F);
            }
        }, 5);

        return true;
    }
}