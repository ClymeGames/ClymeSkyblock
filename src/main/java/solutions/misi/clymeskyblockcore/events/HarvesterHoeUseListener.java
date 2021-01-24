package solutions.misi.clymeskyblockcore.events;

import com.bgsoftware.wildtools.api.events.HarvesterHoeUseEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

import java.util.List;

public class HarvesterHoeUseListener implements Listener {

    @EventHandler
    public void onHoeUse(HarvesterHoeUseEvent event) {
        List<Location> blocks = event.getBlocks();
        Player player = event.getPlayer();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        for(Location block : blocks) {
            try {
                if(block.getBlock().getType() != Material.SUGAR_CANE) continue;
            } catch(NullPointerException exception) { continue; }

            clymePlayer.getClymeStatistics().setSugarcaneBroken(clymePlayer.getClymeStatistics().getSugarcaneBroken() + 1);
            player.getWorld().spawnParticle(Particle.CRIT, block, 5);
            player.playSound(player.getLocation(), Sound.ITEM_AXE_STRIP, 1.0F, 1.0F);
        }
    }
}