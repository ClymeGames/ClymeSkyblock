package solutions.misi.clymeskyblockcore.islands.events;

import net.savagelabs.skyblockx.event.IslandCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class IslandCreateListener implements Listener {

    @EventHandler
    public void onIslandCreate(IslandCreateEvent event) {
        ClymeSkyblockCore.getInstance().getClymeIslandManager().redefineIslandRegion(event.getIsland());
    }
}
