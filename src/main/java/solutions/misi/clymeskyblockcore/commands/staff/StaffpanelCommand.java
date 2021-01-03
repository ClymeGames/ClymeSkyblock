package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class StaffpanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        switch (playerRank) {
            case "helper":
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

        if (args.length == 0) {
            ClymeSkyblockCore.getInstance().getStaffpanelGUI().open(player);
        }

        if (args.length != 1) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/staffpanel <player>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().open(player, target.getUniqueId());
        return true;
    }
}