package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.items.ClymeCrystal;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class ClymeCrystalCommand implements CommandExecutor {

    //> Usage: /clymecrystal (player)

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = null;
        ClymePlayer clymePlayer = null;
        Player target;

        if(sender instanceof Player) {
            player = (Player) sender;
            clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
            String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

            switch(playerRank) {
                case "manager":
                case "owner":
                    break;
                default:
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                    return false;
            }
        }

        if(args.length == 0) {
            if(sender instanceof Player) {
                giveClymeCrystal(player);
                return true;
            } else {
                Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/clymecrystal (player)" + ClymeChatColor.ERROR() + "!");
                return false;
            }
        } else if(args.length == 1) {
            //> Usage: /clymecrystal (player)
            target = Bukkit.getPlayer(args[0]);

            if(sender instanceof Player) {
                if(target == null || !target.isOnline()) {
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                giveClymeCrystal(target);
                return true;
            } else {
                if(target == null || !target.isOnline()) {
                    Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                    return false;
                }

                giveClymeCrystal(target);
                return true;
            }
        } else {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/clymecrystal (player)" + ClymeChatColor.ERROR() + "!");
            return false;
        }
    }

    private void giveClymeCrystal(Player player) {
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(player.getInventory().firstEmpty() == -1) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Your inventory is full! ClymeCrystal has been dropped at your position.");
            player.getWorld().dropItem(player.getLocation(), ClymeCrystal.getItemStack(player));
            return;
        }

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have received a ClymeCrystal!");
        player.getInventory().addItem(ClymeCrystal.getItemStack(player));
    }
}