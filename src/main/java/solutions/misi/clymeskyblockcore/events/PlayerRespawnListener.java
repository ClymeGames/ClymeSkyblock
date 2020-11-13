package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Location spawn = new Location(Bukkit.getWorld("world"), 75.432, 175.0, 102.524);
        spawn.setYaw((float) -0.7);
        spawn.setPitch((float) -0.0);
        event.setRespawnLocation(spawn);
    }
}
