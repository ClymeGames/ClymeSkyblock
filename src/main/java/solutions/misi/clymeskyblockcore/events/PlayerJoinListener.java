package solutions.misi.clymeskyblockcore.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        ClymeSkyblockCore.getInstance().getSqlManager().getPlayersTable().registerPlayer(player);

        //> Welcome message
        ClymeSkyblockCore.getInstance().getClymeMessage().clearChat(player);

        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "╔══════════════╗"));
        player.sendMessage(" ");
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.SECONDARY() + "§oWelcome to ClymeGames, " + ClymeChatColor.ACCENT() + player.getName() + ClymeChatColor.SECONDARY() + "!"));
        player.sendMessage(" ");
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.ACCENT() + "➢ " + ClymeChatColor.PRIMARY() + "Website: " + ClymeChatColor.SECONDARY() + "clyme.games"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.ACCENT() + "➢ " + ClymeChatColor.PRIMARY() + "Shop: " + ClymeChatColor.SECONDARY() + "clyme.games/shop"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.ACCENT() + "➢ " + ClymeChatColor.PRIMARY() + "Discord: " + ClymeChatColor.SECONDARY() + "clyme.games/discord"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.ACCENT() + "➢ " + ClymeChatColor.PRIMARY() + "Vote: " + ClymeChatColor.SECONDARY() + "clyme.games/vote"));
        player.sendMessage(" ");
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "╚══════════════╝"));

    }
}
