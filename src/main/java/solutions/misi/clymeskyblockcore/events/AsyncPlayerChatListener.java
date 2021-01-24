package solutions.misi.clymeskyblockcore.events;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
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
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);

        //> Remove screenshare players from Chat
        for (Player screensharePlayer : ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing())
            event.getRecipients().remove(screensharePlayer);
        if (ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(player)) {
            event.setCancelled(true);
            return;
        }

        //> Player muted
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        if (clymePlayer.getMuted() != null && clymePlayer.getMuted().after(new Date())) {
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

        //> Disable default chat while in Island Chat
        if(superiorPlayer.hasTeamChatEnabled()) return;

        //> Chat Format
        event.setCancelled(true);

        if(event.getMessage().length() > 128) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Your message is too long!");
            return;
        }

        String playerNickname = clymePlayer.getNickname();
        String playerPrefix = ClymeSkyblockCore.getInstance().getChat().getPlayerPrefix(player);
        String playerChatColor = ClymeSkyblockCore.getInstance().getChat().getPlayerSuffix(player);
        String playerPrefixColor = playerPrefix.substring(3, 10);
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        String deluxeTag = "%deluxetags_tag%";
        deluxeTag = PlaceholderAPI.setPlaceholders(player, deluxeTag);
        if (!deluxeTag.equals("")) deluxeTag += " ";
        long hours = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 / 60;
        long minutes = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 % 60;

        LegacyComponentSerializer ampersandRGB = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().build();

        String legacyHover = ClymeSkyblockCore.getInstance().getClymeMessage().convertLegacyToAdventure(
                        "§f➢ §7Name: " + player.getName() + "\n" +
                        "§f➢ §7Rank: " + playerPrefix + "\n" +
                        "§f➢ §7Money: " + clymePlayer.getBalance() + "\n" +
                        "§f➢ §7Island Level: " + clymePlayer.getIslandLevel() + "\n" +
                        "§f➢ §7Playtime: " + hours + " hours and " + minutes + " minutes");
        Component hover = LegacyComponentSerializer.legacyAmpersand().deserialize(legacyHover);

        String legacyFormat = ClymeSkyblockCore.getInstance().getClymeMessage().convertLegacyToAdventure(deluxeTag + playerPrefix + " " + playerNickname + playerPrefixColor + " » ");
        Component format = ampersandRGB.deserialize(legacyFormat);

        String legacyMessage = event.getMessage();
        if(player.hasPermission("clymegames.chatcolor")) legacyMessage = ChatColor.translateAlternateColorCodes('&', event.getMessage());
        Component message = LegacyComponentSerializer.legacyAmpersand().deserialize(playerChatColor + legacyMessage);

        TextComponent formattedMessage;

        if(itemStack != null && itemStack.getType() != Material.AIR) {
            //> [item]
            Key item = Key.key("minecraft", itemStack.getType().getKey().getKey());
            String itemName = itemStack.getItemMeta().getDisplayName();
            if(itemName.equals("")) itemName = itemStack.getType().name();
            Component chatItem = ampersandRGB.deserialize("§7[ " + "§fx" + itemStack.getAmount() + " " + itemName + " §7]");
            TextReplacementConfig itemReplacement = TextReplacementConfig.builder()
                    .matchLiteral("[item]")
                    .replacement(matchResult -> Component.text()
                            .append(chatItem)
                            .hoverEvent(HoverEvent.showItem(item, 1))
                    ).build();

            formattedMessage = Component.text()
                    .append(format.hoverEvent(HoverEvent.showText(hover)))
                    .append(message.replaceText(itemReplacement))
                    .build();
        } else {
            formattedMessage = Component.text()
                    .append(format.hoverEvent(HoverEvent.showText(hover)))
                    .append(message)
                    .build();
        }

        BukkitAudiences audienceAdapter = BukkitAudiences.create(ClymeSkyblockCore.getInstance());
        for(Player target : event.getRecipients()) {
            Audience targetAudience = audienceAdapter.player(target);
            targetAudience.sendMessage(formattedMessage);
        }
    }
}