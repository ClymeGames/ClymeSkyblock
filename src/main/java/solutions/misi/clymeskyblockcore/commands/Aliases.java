package solutions.misi.clymeskyblockcore.commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Aliases implements Listener {

    @EventHandler (priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        //> /pay => /money pay
        if(event.getMessage().toLowerCase().startsWith("/pay ")) {
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

        //> /fixall => /fix all
        if(event.getMessage().toLowerCase().startsWith("/fixall")) {
            event.setMessage(event.getMessage().replaceFirst("/fixall", "/fix all"));
            return;
        }

        //> /cosmetic => /cosmetics
        if(event.getMessage().toLowerCase().startsWith("/cosmetic")) {
            event.setMessage(event.getMessage().replaceFirst("/cosmetic", "/cosmetics"));
            return;
        }

        //> /lobby => /server lobby
        if(event.getMessage().toLowerCase().startsWith("/lobby")) {
            event.setMessage(event.getMessage().replaceFirst("/lobby", "/server lobby"));
            return;
        }

        //> /hub => /server lobby
        if(event.getMessage().toLowerCase().startsWith("/hub")) {
            event.setMessage(event.getMessage().replaceFirst("/hub", "/server lobby"));
            return;
        }
    }
}
