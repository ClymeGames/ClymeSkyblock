package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

        if(event.getBlock().getType() != Material.SUGAR_CANE) return;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        clymePlayer.getClymeStatistics().setSugarcaneBroken(clymePlayer.getClymeStatistics().getSugarcaneBroken() + 1);

        player.getWorld().spawnParticle(Particle.FLAME, event.getBlock().getLocation(), 15);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 0.6F, 0.6F);
    }
}
