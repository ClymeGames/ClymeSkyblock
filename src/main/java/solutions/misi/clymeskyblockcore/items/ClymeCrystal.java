package solutions.misi.clymeskyblockcore.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.ArrayList;
import java.util.List;

public class ClymeCrystal {

    public static ItemStack getItemStack(Player player) {
        ItemStack clymeCrystal = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta clymeCrystalMeta = clymeCrystal.getItemMeta();
        clymeCrystalMeta.setDisplayName(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "§lCLYME CRYSTAL"));
        List<String> clymeCrystalLore = new ArrayList<>();
        clymeCrystalLore.add(" ");
        clymeCrystalLore.add("§7Drag & Drop this crystal onto a tool");
        clymeCrystalLore.add("§7or weapon, to give it a unique");
        clymeCrystalLore.add("§7power!");
        clymeCrystalLore.add(" ");
        clymeCrystalLore.add("§f➢ §6Owner:");
        clymeCrystalLore.add("§7" + player.getName());
        clymeCrystalLore.add(" ");
        clymeCrystalMeta.setLore(clymeCrystalLore);
        clymeCrystal.setItemMeta(clymeCrystalMeta);
        return clymeCrystal;
    }
}
