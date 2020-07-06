package solutions.misi.clymeskyblockcore.islands.settings.flags;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.config.WorldConfiguration;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.savagelabs.skyblockx.core.Island;
import net.savagelabs.skyblockx.core.IslandKt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Island island = IslandKt.getIslandFromLocation(event.getTo());
        WorldConfiguration wcfg = WorldGuard.getInstance().getPlatform().getGlobalStateManager().get(BukkitAdapter.adapt(event.getTo().getWorld()));

        if(island == null) return;
        if(!wcfg.useRegions) return;
        if(island.getMembers().contains(player.getUniqueId().toString())) return;

        RegionQuery regionQuery = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

        //> Visitor flag
        if(!regionQuery.testState(BukkitAdapter.adapt(event.getTo()), null, ClymeSkyblockCore.getInstance().getVisitorsFlag())) {
            event.setCancelled(true);
            player.sendMessage(ClymeSkyblockCore.getInstance().getMessages().getPrefix() + "§cThis Island has disabled Visitors in their Settings!");
        }
    }
}