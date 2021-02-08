package solutions.misi.clymeskyblockcore.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        event.setMotd(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + "                              §8[1.8-1.16]\n" +
                ClymeChatColor.INFO() + "A unique Skyblock Experience.                         §f§lS3"));
    }
}