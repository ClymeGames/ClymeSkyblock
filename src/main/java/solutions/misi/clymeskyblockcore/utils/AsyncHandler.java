package solutions.misi.clymeskyblockcore.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncHandler {

    public void sendPlaytimeTop(ClymePlayer clymePlayer, List<OfflinePlayer> allPlayers) {
        HashMap<OfflinePlayer, Integer> allPlayersPlaytime = new HashMap<>();
        for(OfflinePlayer offlinePlayer : allPlayers) allPlayersPlaytime.put(offlinePlayer, offlinePlayer.getStatistic(Statistic.PLAY_ONE_MINUTE));
        Map<OfflinePlayer, Integer> sortedAllPlayersPlaytime = SortingUtils.sortByValue(allPlayersPlaytime);

        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "The players with most playtime:");

        int topAmount = 1;
        for(Map.Entry<OfflinePlayer, Integer> result : sortedAllPlayersPlaytime.entrySet()) {
            if(topAmount > 5) break;

            String username = ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getNameFromUUID(result.getKey().getName());
            long hours = (result.getKey().getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 / 60;
            long minutes = (result.getKey().getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 % 60;

            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "#" + topAmount + " " + ClymeChatColor.SECONDARY() + username + ClymeChatColor.INFO() + " - " + ClymeChatColor.SECONDARY() + hours + " hours " + ClymeChatColor.INFO() + " and " + ClymeChatColor.SECONDARY() + minutes + " minutes");

            topAmount++;
        }
    }
}