package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("");

        if(ClymeSkyblockCore.getInstance().isRestarting()) {
            player.kickPlayer("&cThe server is currently restarting!");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().addClymePlayer(player);

            if(!player.isOp()) {
                ClymeSkyblockCore.getInstance().getClymeMessage().clearChat(player);

                //> Welcome message
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
        });

        //> Teleport player to spawn
        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            player.teleport(new Location(Bukkit.getWorld("world"), 75.432, 175.0, 102.524));
        }, 2);

        player.setFlying(false);
        player.removePotionEffect(PotionEffectType.BLINDNESS);

        //> Disable PvP Cooldown
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);

        Bukkit.getConsoleSender().sendMessage("§a" + player.getName() + " has joined the Server!");
    }
}