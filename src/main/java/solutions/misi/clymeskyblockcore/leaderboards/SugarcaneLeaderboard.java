package solutions.misi.clymeskyblockcore.leaderboards;

import org.bukkit.Bukkit;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.util.Map;

public class SugarcaneLeaderboard extends ClymeLeaderboard {

    public SugarcaneLeaderboard() {
        super(15);
    }

    @Override
    public void startCalculation() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            ClymeSkyblockCore.getInstance().getDataManager().getClymeStatisticsTable().querySugarcaneTop();
        }, 10, 20L *60*getTimerInMinutes());

    }

    @Override
    public void update(Map<String, Long> queryData) {
        getLeaderboard().clear();

        for(Map.Entry<String, Long> entry : queryData.entrySet()) {
            String username = ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getNameFromUUID(entry.getKey());
            getLeaderboard().put(username, entry.getValue());
        }

        Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§7Sugarcane Leaderboard has been updated!");
    }
}