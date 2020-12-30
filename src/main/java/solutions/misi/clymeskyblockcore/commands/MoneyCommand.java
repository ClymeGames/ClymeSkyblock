package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.math.BigDecimal;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
            Player target;

            switch(args.length) {
                case 0:
                    //> Usage: /money
                    BigDecimal balance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have " + ClymeChatColor.SECONDARY() + "$" + balance);

                    return true;
                case 1:
                    //> Usage: /money (player)
                    target = Bukkit.getPlayer(args[0]);

                    if(target == null || !target.isOnline()) {
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!");
                        return false;
                    }

                    if(args[0].equalsIgnoreCase("pay")) {
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money pay <player> <amount>" + ClymeChatColor.ERROR() + "!");
                        return false;
                    }

                    BigDecimal bigTargetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                    double targetBalance = Math.round(bigTargetBalance.doubleValue() * 100.0) / 100.0;
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.INFO() + " has " + ClymeChatColor.SECONDARY() + "$" + targetBalance);
                    return true;
                case 3:
                    //> Usage: /money set|add|remove|pay (player) (amount)
                    target = Bukkit.getPlayer(args[1]);
                    ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target);

                    if(target == null || !target.isOnline()) {
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!");
                        return false;
                    }

                    BigDecimal amount;

                    try {
                        amount = new BigDecimal(args[2]);
                    } catch(NumberFormatException ex) {
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use a real number!");
                        return false;
                    }

                    BigDecimal currentTargetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                    BigDecimal changedTargetBalance = currentTargetBalance;

                    switch(args[0]) {
                        case "pay":
                            BigDecimal currentPlayerBalance;
                            BigDecimal changedPlayerBalance;

                            if(player == target) {
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can't pay money to yourself!");
                                return false;
                            }

                            currentPlayerBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player);
                            changedPlayerBalance = currentPlayerBalance.subtract(amount);
                            changedTargetBalance = changedTargetBalance.add(amount);

                            if(changedPlayerBalance.compareTo(BigDecimal.ZERO) < 0) {
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You don't have " + ClymeChatColor.SECONDARY() + "$" + amount + ClymeChatColor.ERROR() + "!");
                                return false;
                            }

                            ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(player, changedPlayerBalance);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You paid " + ClymeChatColor.SECONDARY() + "$" + amount + ClymeChatColor.SUCCESS() + " to " + ClymeChatColor.SECONDARY() + target.getName());
                            clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You received " + ClymeChatColor.SECONDARY() + "$" + amount + ClymeChatColor.SUCCESS() + " from " + ClymeChatColor.SECONDARY() + player.getName());
                            break;
                        case "set":
                            if(!player.hasPermission("clymegames.economy.set")) {
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                                return false;
                            }

                            changedTargetBalance = amount;
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "The balance of " + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been set to " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance);
                            break;
                        case "add":
                            if(!player.hasPermission("clymegames.economy.add")) {
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                                return false;
                            }

                            changedTargetBalance = changedTargetBalance.add(amount);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You added " + ClymeChatColor.SECONDARY() + "$" + amount + ClymeChatColor.SUCCESS() + " to " + ClymeChatColor.SECONDARY() + target.getName());
                            break;
                        case "remove":
                            if(!player.hasPermission("clymegames.economy.remove")) {
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                                return false;
                            }

                            changedTargetBalance = changedTargetBalance.subtract(amount);
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You removed " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance + ClymeChatColor.SUCCESS() + " from " + ClymeChatColor.SECONDARY() + target.getName());
                            break;
                        default:
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money pay <player> <amount>" + ClymeChatColor.ERROR() + "!");
                            return false;
                    }

                    ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(target, changedTargetBalance);
                    return true;
                default:
                    //> Wrong usage
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money" + ClymeChatColor.ERROR() + "!");
                    return false;
            }
        } else {
            Player target;

            switch (args.length) {
                case 1:
                    //> Usage: /money (player)
                    target = Bukkit.getPlayer(args[0]);

                    if (target == null || !target.isOnline()) {
                        Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!"));
                        return false;
                    }

                    BigDecimal bigTargetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                    double targetBalance = Math.round(bigTargetBalance.doubleValue() * 100.0) / 100.0;
                    Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.INFO() + " has " + ClymeChatColor.SECONDARY() + "$" + targetBalance));
                    return true;
                case 3:
                    //> Usage: /money set|add|remove|pay (player) (amount)
                    target = Bukkit.getPlayer(args[1]);

                    if (target == null || !target.isOnline()) {
                        Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!"));
                        return false;
                    }

                    BigDecimal amount;

                    try {
                        amount = new BigDecimal(args[2]);
                    } catch (NumberFormatException ex) {
                        Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use a real number!"));
                        return false;
                    }

                    BigDecimal currentTargetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                    BigDecimal changedTargetBalance = currentTargetBalance;

                    switch (args[0]) {
                        case "set":
                            changedTargetBalance = amount;
                            Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "The balance of " + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been set to " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance));
                            break;
                        case "add":
                            changedTargetBalance = changedTargetBalance.add(amount);
                            Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You added " + ClymeChatColor.SECONDARY() + "$" + amount + ClymeChatColor.SUCCESS() + " to " + ClymeChatColor.SECONDARY() + target.getName()));
                            break;
                        case "remove":
                            changedTargetBalance = changedTargetBalance.subtract(amount);
                            Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You removed " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance + ClymeChatColor.SUCCESS() + " from " + ClymeChatColor.SECONDARY() + target.getName()));
                            break;
                        default:
                            Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money" + ClymeChatColor.ERROR() + "!"));
                            return false;
                    }

                    ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(target, changedTargetBalance);
                    return true;
                default:
                    //> Wrong usage
                    Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money" + ClymeChatColor.ERROR() + "!"));
                    return false;
            }
        }
    }
}