package solutions.misi.clymeskyblockcore.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class DisposalGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Disposal");
        player.openInventory(gui);
    }
}
