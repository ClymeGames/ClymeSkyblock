package solutions.misi.clymeskyblockcore.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onChange(SignChangeEvent event) {
        Player player = event.getPlayer();

        if(player.hasPermission("clymegames.coloredsigns")) {
            for(int i = 0; i < event.getLines().length; i++) {
                if(event.getLine(i) == null) continue;
                event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLine(i)));
            }
        }
    }
}
