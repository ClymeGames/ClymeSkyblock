package solutions.misi.clymeskyblockcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class PlayerToggleFlightListener implements Listener {

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(event.getPlayer())) event.setCancelled(true);
    }
}
