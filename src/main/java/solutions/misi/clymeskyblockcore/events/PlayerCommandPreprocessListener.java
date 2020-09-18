package solutions.misi.clymeskyblockcore.events;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
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

        //> Island menu Command
        if(cmd.equalsIgnoreCase("/is") || cmd.equalsIgnoreCase("/island")) {
            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);

            if(superiorPlayer.getIsland() != null) {
                event.setCancelled(true);
                ClymeSkyblockCore.getInstance().getIslandGUI().open(player);
            }
        }

        //> Island settings Command
        if(cmd.equalsIgnoreCase("/is settings") || cmd.equalsIgnoreCase("/island settings")) {
            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);

            if(superiorPlayer.getIsland() != null) {
                event.setCancelled(true);
                ClymeSkyblockCore.getInstance().getIslandSettingsGUI().open(player);
            }
        }

        //> Anti Command spam
        ClymeSkyblockCore.getInstance().getCommandHandler().usedCommand(player);
        if(ClymeSkyblockCore.getInstance().getCommandHandler().getBlocked().contains(player.getUniqueId())) event.setCancelled(true);
    }
}