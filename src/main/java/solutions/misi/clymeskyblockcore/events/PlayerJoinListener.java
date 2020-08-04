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
        event.setJoinMessage("");
        ClymeSkyblockCore.getInstance().getClymeMessage().clearChat(player);

        player.sendMessage(" ");
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.SECONDARY() + "× " + ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix().replace(" ➢ ", "") + ClymeChatColor.SECONDARY() + " // §fclyme.games"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.SECONDARY() + "× You are playing on ClymeGames Skyblock!"));
        player.sendMessage(" ");
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "➢ Website: " + ClymeChatColor.SECONDARY() + " www.clyme.games"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "➢ Shop: " + ClymeChatColor.SECONDARY() + " shop.clyme.games"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "➢ Discord: " + ClymeChatColor.SECONDARY() + " clyme.games/discord"));
        player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.PRIMARY() + "➢ Vote: " + ClymeChatColor.SECONDARY() + " clyme.games/vote"));
        player.sendMessage(" ");
    }
}
