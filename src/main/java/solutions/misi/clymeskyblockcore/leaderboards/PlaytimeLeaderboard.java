package solutions.misi.clymeskyblockcore.leaderboards;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.SortingUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlaytimeLeaderboard {

    final int timerInMinutes = 15;
    public Map<String, Integer> playtimeTop = new LinkedHashMap<>();

    public void startPlaytimeCalc() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().queryAllPlayers();
        }, 10, 20*60*timerInMinutes);
    }

    public void update(List<OfflinePlayer> allPlayers) {
        HashMap<OfflinePlayer, Integer> allPlayersPlaytime = new HashMap<>();
        for(OfflinePlayer offlinePlayer : allPlayers) allPlayersPlaytime.put(offlinePlayer, offlinePlayer.getStatistic(Statistic.PLAY_ONE_MINUTE));
        Map<OfflinePlayer, Integer> sortedAllPlayersPlaytime = SortingUtils.sortByValue(allPlayersPlaytime);

        playtimeTop.clear();

        int topAmount = 1;
        for(Map.Entry<OfflinePlayer, Integer> result : sortedAllPlayersPlaytime.entrySet()) {
            if(topAmount > 5) break;

            String username = ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getNameFromUUID(result.getKey().getName());
            if(username.equals("1camou")) continue;
            playtimeTop.put(username, result.getValue());

            topAmount++;
        }


        Bukkit.broadcastMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§aPlaytime Leaderboard has been updated!");
    }
}