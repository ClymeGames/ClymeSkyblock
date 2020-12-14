package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");

        Player player = event.getEntity();

        if(player.hasPermission("clymegames.keeplevel")) event.setKeepLevel(true);
    }
}