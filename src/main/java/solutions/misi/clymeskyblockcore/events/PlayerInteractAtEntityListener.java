package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if(!(event.getRightClicked() instanceof Player)) return;

        //> Send trade request
        Player target = (Player) event.getRightClicked();

        player.performCommand("trade offer " + target.getName());
    }
}
