package solutions.misi.clymeskyblockcore.gui.staffpanel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class StaffpanelInventoryInspectorGUI implements Listener {

    public void open(Player player, Player target) {
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Inventory - " + target.getName());
        gui.setContents(target.getInventory().getContents());
        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getView().getTitle().startsWith(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Inventory - ")) event.setCancelled(true);
    }
}