package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class UpdateCommand implements CommandExecutor {

    //> Usage: /update

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        if(args.length != 0) {
            //> Wrong usage
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/restart" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        switch(playerRank) {
            case "manager":
            case "owner":
                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true,"The Server is restarting due to new Updates!");
                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 5 minutes...");
                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, "");

                Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                    ClymeSkyblockCore.getInstance().setRestarting(true);
                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 60 seconds...");
                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                    Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                        ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                        ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 30 seconds...");
                        ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 15 seconds...");
                            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                            Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 10 seconds...");
                                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                                Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 5 seconds...");
                                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                                    Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                        ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                                        ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 4 seconds...");
                                        ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                                        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                                            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 3 seconds...");
                                            ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                                            Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                                                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 2 seconds...");
                                                ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                                                Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");
                                                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(true, "Server Restart in 1 seconds...");
                                                    ClymeSkyblockCore.getInstance().getClymeMessage().broadcastMessage(false, " ");

                                                    Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                                        for(Player target : Bukkit.getOnlinePlayers()) {
                                                            target.kickPlayer("§cThe Server is being restarted for new Updates!\n" +
                                                                    "§fYou can join again shortly..");
                                                        }

                                                        //> RESTART
                                                        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
                                                            Bukkit.getServer().shutdown();
                                                        }, 20*5);
                                                    }, 20);
                                                }, 20);
                                            }, 20);
                                        }, 20);
                                    }, 20);
                                }, 20*5);
                            }, 20*5);
                        }, 20*15);
                    }, 20*30);
                }, 20*60*4);

                return true;
            default:
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
        }
    }
}