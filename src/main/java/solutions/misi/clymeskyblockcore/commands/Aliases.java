package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Aliases implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        //> /pay => /money pay
        if(event.getMessage().toLowerCase().startsWith("/pay")) {
            event.setMessage(event.getMessage().replaceFirst("/pay", "/money pay"));
            return;
        }


    }
}
