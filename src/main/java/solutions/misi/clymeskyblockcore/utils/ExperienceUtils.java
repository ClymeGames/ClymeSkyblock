package solutions.misi.clymeskyblockcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

public class ExperienceUtils {

    //> Calculate amount of exp needed to level up
    private int getExpToLevelUp(int level) {
        if(level <= 15) {
            return 2 * level + 7;
        } else if(level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    //> Calculate total exp up to a level
    private int getExpAtLevel(int level) {
        if(level <= 16) {
            return (int) (Math.pow(level, 2) + 6 * level);
        } else if(level <= 31) {
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }
    }

    //> Calculate player's current xp amount
    private int getPlayerExp(Player player) {
        int exp = 0;
        int level = player.getLevel();

        //> Get the amount of exp in the past levels
        exp += getExpAtLevel(level);

        //> Get the amount of exp towards the next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    //> Give or take exp
    public int changePlayerExp(Player player, int exp) {
        //> Get the player's current exp
        int currentExp = getPlayerExp(player);

        //> Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        //> Give the player his exp back, with the difference
        int newExp = currentExp + exp;
        player.giveExp(newExp);

        //> Return the player's new exp amount
        return newExp;
    }

    public void convertExpToBottles(Player player) {
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        int currentExp = getPlayerExp(player);
        int remainingExp = currentExp % 7;
        int xpBottlesAmount = (currentExp - remainingExp) / 7;
        int droppedXpBottles = 0;

        changePlayerExp(player, -(currentExp - remainingExp));

        for(int i = 0; i < xpBottlesAmount; i++) {
            if(player.getInventory().firstEmpty() == -1) { droppedXpBottles++; continue; }

            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE));
            });
        }

        if(droppedXpBottles > 0) {
            int finalDroppedXpBottles = droppedXpBottles;
            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                for(int i = 0; i < finalDroppedXpBottles; i++) {
                    player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.EXPERIENCE_BOTTLE));
                }
            });

            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Your inventory is full and " + ClymeChatColor.SECONDARY() + droppedXpBottles + ClymeChatColor.ERROR() + " XP Bottles have been dropped!");
        }
    }
}