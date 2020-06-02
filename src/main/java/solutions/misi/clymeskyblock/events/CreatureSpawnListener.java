package solutions.misi.clymeskyblock.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if(event.getLocation().getWorld().getName().contains("skyblock")) {
            if(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
                event.setCancelled(true);
            }
        }
    }
}
