package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerPrefix = ClymeSkyblockCore.getInstance().getChat().getPlayerPrefix(player);
        String playerChatColor = ClymeSkyblockCore.getInstance().getChat().getPlayerSuffix(player);
        String playerPrefixColor = playerPrefix.substring(3,10);

        String message = event.getMessage();
        event.setFormat(ClymeSkyblockCore.getInstance().getClymeMessage().format(playerPrefix + " " + player.getName() + playerPrefixColor + " » " + playerChatColor) + message);
    }
}