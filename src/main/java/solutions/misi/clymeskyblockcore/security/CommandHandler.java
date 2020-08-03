package solutions.misi.clymeskyblockcore.security;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.util.*;

public class CommandHandler {

    @Getter public Map<UUID, Integer> usedCommands = new HashMap<>();
    @Getter public List<UUID> blocked = new ArrayList<>();

    public void usedCommand(Player player) {
        if(!usedCommands.containsKey(player.getUniqueId())) {
            usedCommands.put(player.getUniqueId(), 1);
            removeUsedCommandScheduler(player);
        } else {
            if(usedCommands.get(player.getUniqueId()) <= 6) {
                usedCommands.put(player.getUniqueId(), usedCommands.get(player.getUniqueId())+1);
                removeUsedCommandScheduler(player);
            }
        }

        if(usedCommands.get(player.getUniqueId()) >= 3) {
            if(!blocked.contains(player.getUniqueId())) {
                blocked.add(player.getUniqueId());
                removeBlockedScheduler(player);
            }

            player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getCommandSpam());
        }
    }

    private void removeUsedCommandScheduler(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                usedCommands.put(player.getUniqueId(), usedCommands.get(player.getUniqueId())-1);
                if(usedCommands.get(player.getUniqueId()) == 0) usedCommands.remove(player.getUniqueId());
            }
        }.runTaskLaterAsynchronously(ClymeSkyblockCore.getInstance(), 50);
    }

    private void removeBlockedScheduler(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                blocked.remove(player.getUniqueId());
            }
        }.runTaskLaterAsynchronously(ClymeSkyblockCore.getInstance(), 20*5);
    }
}
