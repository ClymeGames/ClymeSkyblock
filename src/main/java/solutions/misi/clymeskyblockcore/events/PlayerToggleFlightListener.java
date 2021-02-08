package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class PlayerToggleFlightListener implements Listener {

    @EventHandler
    public void onFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(event.getPlayer())) event.setCancelled(true);
        if(player.getWorld().getName().equals("pvp")) {
            if(player.isOp()) return;
            player.setFlying(false);
            player.setAllowFlight(false);
            event.setCancelled(true);
        }
    }
}
