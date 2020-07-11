package solutions.misi.clymeskyblockcore.islands.events;

import net.savagelabs.skyblockx.event.IslandUpgradeEvent;
import net.savagelabs.skyblockx.upgrade.UpgradeType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class IslandUpgradeListener implements Listener {

    @EventHandler
    public void onIslandUpgrade(IslandUpgradeEvent event) {
        if(event.getUpgradeType() != UpgradeType.BORDER) return;
        ClymeSkyblockCore.getInstance().getClymeIslandManager().redefineIslandRegion(event.getIsland());
    }
}
