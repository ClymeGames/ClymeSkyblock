package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if(block.getType() != Material.SUGAR_CANE) return;
        block.setMetadata("PLACED", new FixedMetadataValue(ClymeSkyblockCore.getInstance(), "TRUE"));
    }
}
