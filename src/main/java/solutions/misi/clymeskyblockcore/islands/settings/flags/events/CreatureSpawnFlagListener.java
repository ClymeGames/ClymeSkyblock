package solutions.misi.clymeskyblockcore.islands.settings.flags.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.config.WorldConfiguration;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class CreatureSpawnFlagListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        WorldConfiguration wcfg = WorldGuard.getInstance().getPlatform().getGlobalStateManager().get(BukkitAdapter.adapt(event.getLocation().getWorld()));

        if(!wcfg.useRegions) return;

        RegionQuery regionQuery = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

        //> Monster Spawn Flag
        if(event.getEntity() instanceof Monster) {
            if(!regionQuery.testState(BukkitAdapter.adapt(event.getLocation()), null, ClymeSkyblockCore.getInstance().getFlags().getMonstersSpawningFlag())) {
                event.setCancelled(true);
            }

            return;
        }

        //> Animal Spawn Flag
        if(!regionQuery.testState(BukkitAdapter.adapt(event.getLocation()), null, ClymeSkyblockCore.getInstance().getFlags().getAnimalsSpawningFlag())) {
            event.setCancelled(true);
        }
    }
}
