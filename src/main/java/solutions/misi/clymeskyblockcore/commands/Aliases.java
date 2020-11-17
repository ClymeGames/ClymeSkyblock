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

        //> /money top => /baltop
        if(event.getMessage().toLowerCase().startsWith("/money top")) {
            event.setMessage(event.getMessage().replaceFirst("/money top", "/moneytop"));
            return;
        }

        //> /? => /help
        if(event.getMessage().startsWith("/?")) {
            event.setMessage(event.getMessage().replaceFirst("/?", "/help"));
            return;
        }
    }
}
