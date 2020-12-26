package solutions.misi.clymeskyblockcore.islands.events;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandDisbandListener implements Listener {

    @EventHandler
    public void onDisband(IslandDisbandEvent event) {
        //ClymeSkyblockCore.getInstance().getClymeIslandManager().deleteIslandRegion(event.getIsland());
    }
}