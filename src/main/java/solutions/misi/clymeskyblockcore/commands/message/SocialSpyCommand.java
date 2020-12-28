package solutions.misi.clymeskyblockcore.commands.message;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class SocialSpyCommand implements CommandExecutor {

    //> Usage: /socialspy

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        if(args.length < 1) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/socialspy" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        switch(playerRank) {
            case "seniormod":
            case "admin":
            case "manager":
            case "owner":
                if(ClymeSkyblockCore.getInstance().getCommandUtil().getSocialSpy().contains(clymePlayer)) {
                    ClymeSkyblockCore.getInstance().getCommandUtil().getSocialSpy().remove(clymePlayer);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully disabled SocialSpy!");
                } else {
                    ClymeSkyblockCore.getInstance().getCommandUtil().getSocialSpy().add(clymePlayer);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully enabled SocialSpy!");
                }

                return true;
            default:
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
        }
    }
}