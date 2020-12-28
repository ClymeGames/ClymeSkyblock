package solutions.misi.clymeskyblockcore.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandsUtil {

    @Getter private final Map<ClymePlayer, String> homeCreation = new HashMap<>();
    @Getter private final Map<ClymePlayer, ClymePlayer> teleportCache = new HashMap<>();
    @Getter private final Map<ClymePlayer, ClymePlayer> teleportHereCache = new HashMap<>();

    @Getter private final List<ClymePlayer> tpToggle = new ArrayList<>();
    @Getter private final List<ClymePlayer> msgToggle = new ArrayList<>();

    @Getter private final List<String> commandCooldowns = new ArrayList<>();

    public ClymePlayer getTarget(ClymePlayer player) {
        for(Map.Entry<ClymePlayer, ClymePlayer> entry : teleportCache.entrySet()) if(entry.getValue().getPlayer() == player.getPlayer()) return entry.getKey();
        for(Map.Entry<ClymePlayer, ClymePlayer> entry : teleportHereCache.entrySet()) if(entry.getValue().getPlayer() == player.getPlayer()) return entry.getKey();
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

    public void startCommandCooldown(ClymePlayer player, String command, int duration) {
        String commandCooldownFormat = player.getUsername() + ":" + command;

        //> Reset cooldown
        if(commandCooldowns.contains(commandCooldownFormat)) {
            commandCooldowns.remove(commandCooldownFormat);
            startCommandCooldown(player, command, duration);
            return;
        }

        commandCooldowns.add(commandCooldownFormat);

        Bukkit.getScheduler().runTaskLaterAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            commandCooldowns.remove(commandCooldownFormat);
        }, duration * 20L * 60);
    }
}