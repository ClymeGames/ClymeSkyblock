package solutions.misi.clymeskyblockcore.events;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        //> Disable sweep attack
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            event.setCancelled(true);
            return;
        }

        //> Combat-Log
        if(!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) return;

        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer((Player) event.getEntity());
        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer((Player) event.getDamager());
        LocalPlayer localTarget = WorldGuardPlugin.inst().wrapPlayer(clymeTarget.getPlayer());

        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery regionQuery = regionContainer.createQuery();

        if(regionQuery.testState(localTarget.getLocation(), localTarget, Flags.PVP)) {
            ClymeSkyblockCore.getInstance().getCombatLog().trackPlayerCombat(clymePlayer);
            ClymeSkyblockCore.getInstance().getCombatLog().trackPlayerCombat(clymeTarget);
        }
    }
}