package solutions.misi.clymeskyblockcore.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

import java.util.HashMap;
import java.util.Map;

public class CommandUtil {

    @Getter private final Map<ClymePlayer, String> homeCreation = new HashMap<>();

    @Getter private final Map<ClymePlayer, ClymePlayer> teleportCache = new HashMap<>();
    @Getter private final Map<ClymePlayer, ClymePlayer> teleportHereCache = new HashMap<>();

    public ClymePlayer getTarget(ClymePlayer player) {
        for(Map.Entry<ClymePlayer, ClymePlayer> entry : teleportCache.entrySet()) if(entry.getValue() == player) return entry.getKey();
        for(Map.Entry<ClymePlayer, ClymePlayer> entry : teleportHereCache.entrySet()) if(entry.getValue() == player) return entry.getKey();
        return null;
    }

    public void startExpireScheduler(ClymePlayer player, ClymePlayer target) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            if(teleportCache.containsKey(player) || teleportHereCache.containsKey(player)) {
                if(teleportCache.get(player) == target || teleportHereCache.get(player) == target) {
                    teleportCache.remove(player);
                    teleportHereCache.remove(player);

                    player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Your teleport request to " + ClymeChatColor.SECONDARY() + target.getPlayer().getName() + ClymeChatColor.INFO() + " has expired!");
                    target.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The teleport request from " + ClymeChatColor.SECONDARY() + player.getPlayer().getName() + ClymeChatColor.INFO() + " has expired!");
                }
            }
        }, 20*60);
    }
}