package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class PlayerTeleportListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        //> Disable teleporting while in combat
        if(ClymeSkyblockCore.getInstance().getCombatLog().getInCombat().containsKey(clymePlayer)) {
            String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);

            switch(playerRank) {
                case "admin":
                case "manager":
                case "owner":
                    break;
                default:
                    event.setCancelled(true);
                    player.setFlying(false);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can't teleport during Combat!");
                    return;
            }
        }

        //> Teleport effects
        player.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0F, 1.0F);
        player.playEffect(EntityEffect.WITCH_MAGIC);

        //> Save last position
        ClymeSkyblockCore.getInstance().getCommandUtil().getBackCache().put(clymePlayer, event.getFrom());

        //> Attack Speed (disable 1.9+ CD)
        if (event.getFrom().getWorld() == event.getTo().getWorld()) return;
        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
        }, 2L);
    }
}
