package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getFrom().getWorld() == event.getTo().getWorld()) return;

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
        }, 2L);
    }
}
