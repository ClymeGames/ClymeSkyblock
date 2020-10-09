package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().addClymePlayer(player);


        //> Welcome message
        event.setJoinMessage("");
        ClymeSkyblockCore.getInstance().getClymeMessage().clearChat(player);

        clymePlayer.sendMessage(" ");
        clymePlayer.sendMessage(ClymeChatColor.SECONDARY() + "× " + ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + ClymeChatColor.SECONDARY() + " // §fclyme.games");
        clymePlayer.sendMessage(ClymeChatColor.SECONDARY() + "× You are playing Skyblock!");
        clymePlayer.sendMessage(" ");
        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "➢ Website: " + ClymeChatColor.SECONDARY() + " www.clyme.games");
        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "➢ Shop: " + ClymeChatColor.SECONDARY() + " shop.clyme.games");
        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "➢ Discord: " + ClymeChatColor.SECONDARY() + " clyme.games/discord");
        clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "➢ Vote: " + ClymeChatColor.SECONDARY() + " clyme.games/vote");
        clymePlayer.sendMessage(" ");
    }
}
