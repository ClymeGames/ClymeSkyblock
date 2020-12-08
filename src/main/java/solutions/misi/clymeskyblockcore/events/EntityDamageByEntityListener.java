package solutions.misi.clymeskyblockcore.events;

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
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) return;
        event.setCancelled(true);

        //> Combat-Log
        if(!(event.getEntity() instanceof Player && event.getDamager() instanceof Player)) return;
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer((Player) event.getEntity());
        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer((Player) event.getDamager());

        ClymeSkyblockCore.getInstance().getCombatLog().trackPlayerCombat(clymePlayer);
        ClymeSkyblockCore.getInstance().getCombatLog().trackPlayerCombat(clymeTarget);
    }
}