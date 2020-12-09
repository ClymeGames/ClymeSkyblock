package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class GamemodeCommand implements CommandExecutor {

    //> Usage: /gamemode <0/1/2/3> (player)

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        if(args.length != 1 && args.length != 2) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/gm <0/1/2/3> (player)" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        int gamemode = 0;

        try {
            gamemode = Integer.parseInt(args[0]);
        } catch(NumberFormatException exception) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/gm <0/1/2/3>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        Player target = null;

        if(args.length == 2) {
            target = Bukkit.getPlayer(args[1]);

            if(target == null || !target.isOnline()) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                return false;
            }
        }

        switch(playerRank) {
            case "admin":
            case "manager":
            case "owner":
                switch(gamemode) {
                    case 0:
                        if(target != null) {
                            target.setGameMode(GameMode.SURVIVAL);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "SURVIVAL");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + "'s" + ClymeChatColor.SUCCESS() + " gamemode has been updated to " + ClymeChatColor.SECONDARY() + "SURVIVAL");
                        } else {
                            player.setGameMode(GameMode.SURVIVAL);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "SURVIVAL");
                        }

                        return true;
                    case 1:
                        if(target != null) {
                            target.setGameMode(GameMode.CREATIVE);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "CREATIVE");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + "'s" + ClymeChatColor.SUCCESS() + " gamemode has been updated to " + ClymeChatColor.SECONDARY() + "CREATIVE");
                        } else {
                            player.setGameMode(GameMode.CREATIVE);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "CREATIVE");
                        }

                        return true;
                    case 2:
                        if(target != null) {
                            target.setGameMode(GameMode.ADVENTURE);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "ADVENTURE");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + "'s" + ClymeChatColor.SUCCESS() + " gamemode has been updated to " + ClymeChatColor.SECONDARY() + "ADVENTURE");
                        } else {
                            player.setGameMode(GameMode.ADVENTURE);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "ADVENTURE");
                        }

                        return true;
                    case 3:
                        if(target != null) {
                            target.setGameMode(GameMode.SPECTATOR);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "SPECTATOR");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + "'s" + ClymeChatColor.SUCCESS() + " gamemode has been updated to " + ClymeChatColor.SECONDARY() + "SPECTATOR");
                        } else {
                            player.setGameMode(GameMode.SPECTATOR);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Your gamemode has been updated to " + ClymeChatColor.SECONDARY() + "SPECTATOR");
                        }

                        return true;
                    default:
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/gm <0/1/2/3>" + ClymeChatColor.ERROR() + "!");
                        return false;
                }
            default:
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
        }
    }
}