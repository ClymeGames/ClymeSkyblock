package solutions.misi.clymeskyblockcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        try {
            if(!event.getCursor().getItemMeta().getDisplayName().equals(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "§lCLYME CRYSTAL"))) return;
            event.setCursor(null);
            event.setCancelled(true);
        } catch(NullPointerException exception) {}
    }
}