package solutions.misi.clymeskyblockcore.security;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.HashMap;
import java.util.Map;

public class CombatLog {

    @Getter private final Map<ClymePlayer, BukkitTask> inCombat;

    public CombatLog() {
        inCombat = new HashMap<>();
    }

    public void trackPlayerCombat(ClymePlayer clymePlayer) {
        //> Player already tracked
        if(getInCombat().containsKey(clymePlayer)) {
            getInCombat().get(clymePlayer).cancel();
            getInCombat().put(clymePlayer, startCombatTimer(clymePlayer));
            return;
        }

        //> Track player
        getInCombat().put(clymePlayer, startCombatTimer(clymePlayer));
        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You are now in Combat! Logging out will result in punishment");
        clymePlayer.getPlayer().setFlying(false);
        clymePlayer.getPlayer().setAllowFlight(false);
    }

    private BukkitTask startCombatTimer(ClymePlayer clymePlayer) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            if(getInCombat().containsKey(clymePlayer)) {
                getInCombat().remove(clymePlayer);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You are out of Combat!");
            }
        }, 20*60);
    }
}