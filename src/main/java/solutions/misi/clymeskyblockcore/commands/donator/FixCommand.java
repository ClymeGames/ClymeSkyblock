package solutions.misi.clymeskyblockcore.commands.donator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class FixCommand implements CommandExecutor {

    //> /fix
    //> /fix hand
    //> /fix all

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[ClymeGames] You can't execute this command through console!");
            return false;
        }

        Player player = (Player) sender;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

        //> /fix - /fix hand
        if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("hand"))) {
            if(!(player.hasPermission("clymegames.fix") || player.hasPermission("clymegames.perk.fix"))) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
            }

            if(ClymeSkyblockCore.getInstance().getCombatLog().getInCombat().containsKey(clymePlayer)) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can't fix items during Combat");
                return false;
            }

            final String command = "fix";
            int cooldown = 0; // in minutes

            switch(playerRank) {
                case "dazzle":
                    cooldown = 60;
                    break;
                case "grover":
                    cooldown = 45;
                case "olympic":
                    cooldown = 30;
                case "ninja":
                    cooldown = 15;
                case "supreme":
                    cooldown = 5;
                default:
                    break;
            }

            //> fix hand perk (10min cooldown)
            if(player.hasPermission("clymegames.perk.fix") && !playerRank.equals("supreme")) {
                cooldown = 10;
            }

            String commandCooldownFormat = player.getName() + ":" + command;
            if(ClymeSkyblockCore.getInstance().getCommandUtil().getCommandCooldowns().contains(commandCooldownFormat)) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Please wait! This command has a " + ClymeChatColor.SECONDARY() + cooldown + "m " + ClymeChatColor.ERROR() + "cooldown");
                return false;
            }

            if(!(player.getInventory().getItemInMainHand().getType().getMaxDurability() > 0)) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can not repair this item!");
                return false;
            }

            repairItem(player.getInventory().getItemInMainHand());
            ClymeSkyblockCore.getInstance().getCommandUtil().startCommandCooldown(clymePlayer, command, cooldown);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully repaired the item in your hand!");

            return true;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("all")) {
            if(!(player.hasPermission("clymegames.fix.all"))) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
                return false;
            }

            final String command = "fixall";
            int cooldown = 0; // in minutes

            switch(playerRank) {
                case "olympic":
                    cooldown = 1440;
                case "ninja":
                    cooldown = 720;
                case "supreme":
                    cooldown = 360;
                default:
                    break;
            }

            String commandCooldownFormat = player.getName() + ":" + command;
            if(ClymeSkyblockCore.getInstance().getCommandUtil().getCommandCooldowns().contains(commandCooldownFormat)) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Please wait! This command has a " + ClymeChatColor.SECONDARY() + cooldown + "m " + ClymeChatColor.ERROR() + "cooldown");
                return false;
            }

            ItemStack[] inventoryContent = player.getInventory().getContents();
            ItemStack[] armorContent = player.getInventory().getArmorContents();
            ItemStack[] extraContent = player.getInventory().getExtraContents();

            for(ItemStack item : inventoryContent) repairItem(item);
            for(ItemStack item : armorContent) repairItem(item);
            for(ItemStack item : extraContent) repairItem(item);
            ClymeSkyblockCore.getInstance().getCommandUtil().startCommandCooldown(clymePlayer, command, cooldown);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully repaired all items in your inventory!");

            return true;
        }

        //> Wrong usage
        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/fix (all)" + ClymeChatColor.ERROR() + "!");
        return false;
    }

    private void repairItem(ItemStack item) {
        try {
            if(!(item.getType().getMaxDurability() > 0)) return;
            item.setDurability((short) 0);
        } catch(NullPointerException exception) { }
    }
}