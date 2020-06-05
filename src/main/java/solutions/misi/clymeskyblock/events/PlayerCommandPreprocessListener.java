package solutions.misi.clymeskyblock.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import solutions.misi.clymeskyblock.ClymeSkyblock;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage();

        if(cmd.equalsIgnoreCase("/is") || cmd.equalsIgnoreCase("/island")) {
            event.setCancelled(true);
            ClymeSkyblock.getInstance().getIslandGUI().open(player);
        }
    }
}
