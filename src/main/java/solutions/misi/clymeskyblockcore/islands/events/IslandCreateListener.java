package solutions.misi.clymeskyblockcore.islands.events;

import com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandCreateListener implements Listener {

    @EventHandler
    public void onIslandCreate(IslandCreateEvent event) {
        //ClymeSkyblockCore.getInstance().getClymeIslandManager().redefineIslandRegion(event.getIsland());
    }
}
