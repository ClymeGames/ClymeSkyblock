package solutions.misi.clymeskyblockcore.security;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.sql.Timestamp;
import java.util.Calendar;

public class Screenshare {

    public void start(Player target) {
        Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), () -> {
            if(target.isOnline()) {
                if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(target)) {
                    banPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
                }
            }
        }, 20*60*5);
    }

    public void banPlayer(OfflinePlayer target) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Timestamp duration = new Timestamp(calendar.getTime().getTime());

        ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().remove(target.getPlayer());
        ClymeSkyblockCore.getInstance().getPlayersHandler().banPlayer(target, duration, "Refusal of presence in the Support");
    }
}
