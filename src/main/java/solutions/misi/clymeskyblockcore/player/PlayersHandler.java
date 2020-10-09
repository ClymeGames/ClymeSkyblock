package solutions.misi.clymeskyblockcore.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PlayersHandler {

    private final List<ClymePlayer> playerList = new ArrayList<>();

    //> Player data Cache
    @Getter private final Map<ClymePlayer, Long> playtimeCache = new HashMap<>();

    public ClymePlayer getClymePlayer(Player player) {
        for(ClymePlayer clymePlayer : playerList) if(clymePlayer.getPlayer() == player) return clymePlayer;
        return null;
    }

    public ClymePlayer addClymePlayer(Player player) {
        ClymePlayer clymePlayer = new ClymePlayer(player);
        playerList.add(clymePlayer);
        return clymePlayer;
    }

    public void removeClymePlayer(ClymePlayer clymePlayer) {
        playerList.remove(clymePlayer);
    }

    public void updatePlayerData(ClymePlayer clymePlayer) {
        //> Playtime
        long addedPlaytime = System.currentTimeMillis() - playtimeCache.get(clymePlayer);
        clymePlayer.setPlaytime(clymePlayer.getPlaytime()+addedPlaytime);
    }

    public void banPlayer(OfflinePlayer target, Timestamp duration, String reason) {
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().banPlayer(target, duration, reason);

        if(target.isOnline()) {
            ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target.getPlayer());
            clymeTarget.setBanReason(reason);
            clymeTarget.setBanned(duration);

            Date today = new Date();
            Date banDate = duration;
            long timeDifference = banDate.getTime() - today.getTime();

            long daysLeft = TimeUnit.MILLISECONDS.toDays(timeDifference);
            timeDifference -= TimeUnit.DAYS.toMillis(daysLeft);
            long hoursLeft = TimeUnit.MILLISECONDS.toHours(timeDifference);
            timeDifference -= TimeUnit.HOURS.toMillis(hoursLeft);
            long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
            timeDifference -= TimeUnit.MINUTES.toMillis(minutesLeft);
            long secondsLeft = TimeUnit.MILLISECONDS.toSeconds(timeDifference);

            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                target.getPlayer().kickPlayer(" \n" +
                        ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + "\n" +
                        " \n" +
                        " \n" +
                        "§cYou are banned from the Server!\n" +
                        " \n" +
                        "§f§lReason: §7" + clymeTarget.getBanReason() + "\n" +
                        "§f§lTime left: §7" + daysLeft + " day(s) " + hoursLeft + " hour(s) " + minutesLeft + " minute(s) " + secondsLeft + " second(s)\n" +
                        "\n\n" +
                        "§c§oIf you think you have been wrongly punished,\n" +
                        "please contact our support team!\n" +
                        "§c§oor purchase a ban evasion in our store: §fshop.clyme.games§c§o!" +
                        "\n");
            });
        }
    }
}
