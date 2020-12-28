package solutions.misi.clymeskyblockcore.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
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
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        //> Format chat
        String deluxeTag = "%deluxetags_tag%";
        deluxeTag = PlaceholderAPI.setPlaceholders(player, deluxeTag);
        String message = deluxeTag + " " + playerPrefix + " %s ";
        message = message + playerPrefixColor + "» " + playerChatColor;
        message = ClymeSkyblockCore.getInstance().getClymeMessage().format(message) + "%s";
        if(player.hasPermission("clymeskyblock.chatcolor")) message = ChatColor.translateAlternateColorCodes('&', message);
        event.setFormat(message);

        //> Remove screenshare players from Chat
        for(Player screensharePlayer : ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing()) event.getRecipients().remove(screensharePlayer);
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(player)) {
            event.setCancelled(true);
            return;
        }

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
            return;
        }

        //> [item] in Chat

    }
}