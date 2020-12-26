package solutions.misi.clymeskyblockcore.islands.events;

import com.bgsoftware.superiorskyblock.api.events.IslandUpgradeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandUpgradeListener implements Listener {

    @EventHandler
    public void onIslandUpgrade(IslandUpgradeEvent event) {
        if(!event.getUpgradeName().equals("BorderSize")) return;
        //ClymeSkyblockCore.getInstance().getClymeIslandManager().redefineIslandRegion(event.getIsland());
    }
}