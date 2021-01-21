package solutions.misi.clymeskyblockcore.leaderboards;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.SortingUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaytimeLeaderboard extends ClymeLeaderboard {

    public PlaytimeLeaderboard() {
        super(15);
    }
    @Override
    public void startCalculation() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().queryAllPlayers();
        }, 10, 20L *60*getTimerInMinutes());
    }

    @Override
    public void update(Map<String, Long> queryData) {
        HashMap<OfflinePlayer, Long> allPlayersPlaytime = new HashMap<>();

        for(String uuid : queryData.keySet()) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
            allPlayersPlaytime.put(target, (long) target.getStatistic(Statistic.PLAY_ONE_MINUTE));
        }

        Map<OfflinePlayer, Long> sortedAllPlayersPlaytime = SortingUtils.sortByValue(allPlayersPlaytime);

        getLeaderboard().clear();

        int topAmount = 1;
        for(Map.Entry<OfflinePlayer, Long> result : sortedAllPlayersPlaytime.entrySet()) {
            if(topAmount > 5) break;

            String username = ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getNameFromUUID(result.getKey().getUniqueId().toString());

            //> Exclude players
            if(username.equals("1camou")) continue;

            getLeaderboard().put(username, result.getValue());

            topAmount++;
        }


        Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§7Playtime Leaderboard has been updated!");
    }
}