package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.Date;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerPrefix = ClymeSkyblockCore.getInstance().getChat().getPlayerPrefix(player);
        String playerChatColor = ClymeSkyblockCore.getInstance().getChat().getPlayerSuffix(player);
        String playerPrefixColor = playerPrefix.substring(3,10);

        String message = event.getMessage();
        event.setFormat(ClymeSkyblockCore.getInstance().getClymeMessage().format(playerPrefix + " " + player.getName() + playerPrefixColor + " » " + playerChatColor) + message);

        //> Remove screenshare players from Chat
        for(Player screensharePlayer : ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing()) event.getRecipients().remove(screensharePlayer);
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(player)) event.setCancelled(true);

        //> Player muted
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        if(clymePlayer.getMuted() != null && clymePlayer.getMuted().after(new Date())) {
            event.setCancelled(true);

            Date today = new Date();
            Date muteDate = clymePlayer.getMuted();
            String timeLeft = ClymeSkyblockCore.getInstance().getTimeUtil().getTimeDifference(muteDate, today);

            clymePlayer.sendMessage(" ");
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You are muted!");
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Time left: " + ClymeChatColor.SECONDARY() + timeLeft);
            clymePlayer.sendMessage(" ");
        }
    }
}