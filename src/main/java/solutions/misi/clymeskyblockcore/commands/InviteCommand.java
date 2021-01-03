package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class InviteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(args.length != 1) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/invite <player>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        //> Accept invite
        if(args[0].equalsIgnoreCase("accept")) {
            if(!ClymeSkyblockCore.getInstance().getInviteManager().getInviting().containsValue(clymePlayer)) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "There is no invite you can accept!");
                return false;
            }

            ClymePlayer inviter = ClymeSkyblockCore.getInstance().getInviteManager().getInviter(clymePlayer);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + clymePlayer.getUsername() + " parent addtemp dazzle 7d");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + inviter.getUsername() + " permission set clymegames.invites.redeemed");
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully accepted the invite from " + ClymeChatColor.SECONDARY() + inviter.getUsername());
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have received " + ClymeChatColor.ACCENT() + "1 week free Dazzle Rank");

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + inviter.getUsername() + " parent addtemp dazzle 7d");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + inviter.getUsername() + " permission set clymegames.invites.invited");
            inviter.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully invited " + ClymeChatColor.SECONDARY() + clymePlayer.getUsername());
            inviter.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have received " + ClymeChatColor.ACCENT() + "1 week free Dazzle Rank");

            ClymeSkyblockCore.getInstance().getInviteManager().getInviting().remove(inviter);
            return true;
        }

        //> Deny invite
        if(args[0].equalsIgnoreCase("deny")) {
            if(!ClymeSkyblockCore.getInstance().getInviteManager().getInviting().containsValue(clymePlayer)) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "There is no invite you can accept!");
                return false;
            }

            ClymePlayer inviter = ClymeSkyblockCore.getInstance().getInviteManager().getInviter(clymePlayer);

            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have denied the invite request from " + ClymeChatColor.SECONDARY() + inviter.getUsername());
            inviter.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + clymePlayer.getUsername() + ClymeChatColor.INFO() + " has denied your invite request!");
            ClymeSkyblockCore.getInstance().getInviteManager().getInviting().remove(inviter);
        }

        //> Send invite
        Player target = Bukkit.getPlayer(args[0]);
        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);

        if(target == null || !target.isOnline()) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
            return false;
        }

        //> Player invites himself
        if(target == player) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can not invite yourself!");
            return false;
        }

        //> Player already inviting someone
        if(ClymeSkyblockCore.getInstance().getInviteManager().getInviting().containsKey(clymePlayer)) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You are already inviting someone!");
            return false;
        }

        //> Target is being invited already
        if(ClymeSkyblockCore.getInstance().getInviteManager().getInviting().containsValue(clymeTarget)) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + clymeTarget.getUsername() + ClymeChatColor.ERROR() + " is being invited already!");
            return false;
        }

        ClymeSkyblockCore.getInstance().getInviteManager().sendPlayerInvite(clymePlayer, clymeTarget);
        return true;
    }
}