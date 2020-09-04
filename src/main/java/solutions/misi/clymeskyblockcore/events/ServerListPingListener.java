package solutions.misi.clymeskyblockcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(ClymeSkyblockCore.getInstance().getClymeMessage().format(
                ClymeChatColor.PRIMARY() + "§m§l-----" + ClymeChatColor.ACCENT() + "§m§l-----§r" + "  " +
                ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + " " + ClymeChatColor.SECONDARY() + "[1.16.2]" + "  " +
                ClymeChatColor.ACCENT() + "§m§l-----" + ClymeChatColor.PRIMARY() + "§m§l-----§r\n" +
                "                      " + ClymeChatColor.INFO() + "Skyblock Release!" + "                    "));
    }
}
