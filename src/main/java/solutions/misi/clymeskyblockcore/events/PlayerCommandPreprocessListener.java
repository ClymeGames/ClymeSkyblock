package solutions.misi.clymeskyblockcore.events;

import net.savagelabs.skyblockx.core.IPlayer;
import net.savagelabs.skyblockx.core.IPlayerKt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String cmd = event.getMessage();

        if(cmd.equalsIgnoreCase("/is") || cmd.equalsIgnoreCase("/island")) {
            IPlayer iPlayer = IPlayerKt.getIPlayer(player);

            if(iPlayer.hasIsland()) {
                event.setCancelled(true);
                ClymeSkyblockCore.getInstance().getIslandGUI().open(player);
            }
        }

        //> Anti Command spam
        ClymeSkyblockCore.getInstance().getCommandHandler().usedCommand(player);
        if(ClymeSkyblockCore.getInstance().getCommandHandler().getBlocked().contains(player.getUniqueId())) event.setCancelled(true);
    }
}