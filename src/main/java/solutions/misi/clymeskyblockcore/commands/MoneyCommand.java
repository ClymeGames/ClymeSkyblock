package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        Player target;

        switch(args.length) {
            case 0:
                //> Usage: /money
                double balance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player);
                player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You have " + ClymeChatColor.SECONDARY() + "$" + balance));

                return true;
            case 1:
                //> Usage: /money (player)
                target = Bukkit.getPlayer(args[0]);

                if(target == null || !target.isOnline()) {
                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.ERROR() + " is not online!"));
                    return false;
                }

                double targetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The player " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.INFO() + " has " + ClymeChatColor.SECONDARY() + "$" + targetBalance));
                return true;
            case 2:
                //> Usage: /money pay (player) (amount)
            case 3:
                //> Usage: /money set|add|remove|pay (player) (amount)
                target = Bukkit.getPlayer(args[1]);

                if(target == null || !target.isOnline()) {
                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + args[1] + ClymeChatColor.ERROR() + " is not online!"));
                    return false;
                }

                double amount;

                try {
                    amount = Double.parseDouble(args[2]);
                } catch(NumberFormatException ex) {
                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use a real number!"));
                    return false;
                }

                double currentTargetBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(target);
                double changedTargetBalance = currentTargetBalance;

                switch(args[0]) {
                    case "pay":
                        double currentPlayerBalance;
                        double changedPlayerBalance;

                        if(player == target) {
                            player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can't pay money to yourself!"));
                            return false;
                        }

                        currentPlayerBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player);
                        changedPlayerBalance = currentPlayerBalance - amount;
                        changedTargetBalance += amount;

                        if(changedPlayerBalance < 0) {
                            player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You don't have " + ClymeChatColor.SECONDARY() + "$" + amount + ClymeChatColor.ERROR() + "!"));
                            return false;
                        }

                        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(player, changedPlayerBalance);
                        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You paid " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance + ClymeChatColor.INFO() + " to " + ClymeChatColor.SECONDARY() + target.getName()));
                        break;
                    case "set":
                        changedTargetBalance = amount;
                        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The balance of " + ClymeChatColor.SECONDARY() + args[0] + ClymeChatColor.INFO() + " has been set to " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance));
                        break;
                    case "add":
                        changedTargetBalance += amount;
                        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You added " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance + ClymeChatColor.INFO() + " to " + ClymeChatColor.SECONDARY() + target.getName()));
                        break;
                    case "remove":
                        changedTargetBalance -= amount;
                        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You removed " + ClymeChatColor.SECONDARY() + "$" + changedTargetBalance + ClymeChatColor.INFO() + " from " + ClymeChatColor.SECONDARY() + target.getName()));
                        break;
                    default:
                        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money" + ClymeChatColor.ERROR() + "!"));
                        return false;
                }

                ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(target, changedTargetBalance);
                return true;
            default:
                //> Wrong usage
                player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/money" + ClymeChatColor.ERROR() + "!"));
                return false;
        }
    }
}
