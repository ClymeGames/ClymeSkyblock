package solutions.misi.clymeskyblockcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getFrozen().contains(event.getPlayer())) event.setCancelled(true);
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(event.getPlayer())) event.setCancelled(true);
    }
}
