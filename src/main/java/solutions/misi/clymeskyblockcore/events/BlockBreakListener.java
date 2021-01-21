package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if(block.getType() != Material.SUGAR_CANE) return;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        //> Check if block has been broken recently
        if(block.hasMetadata("PLACED")) return;

        clymePlayer.getClymeStatistics().setSugarcaneBroken(clymePlayer.getClymeStatistics().getSugarcaneBroken() + 1);
        player.getWorld().spawnParticle(Particle.CRIT, block.getLocation(), 5);
        player.playSound(player.getLocation(), Sound.ITEM_AXE_STRIP, 1.0F, 1.0F);

        //> Check for sugarcane above
        for(int i = block.getY(); i < 256; i++) {
            Location tempLocation = block.getLocation().clone();
            tempLocation.setY(i);

            try {
                if(tempLocation.getBlock().getType() != Material.SUGAR_CANE) break;
            } catch(NullPointerException exception) { break; }

            clymePlayer.getClymeStatistics().setSugarcaneBroken(clymePlayer.getClymeStatistics().getSugarcaneBroken() + 1);
            player.getWorld().spawnParticle(Particle.CRIT, tempLocation, 5);
            player.playSound(player.getLocation(), Sound.ITEM_AXE_STRIP, 1.0F, 1.0F);
        }
    }
}
