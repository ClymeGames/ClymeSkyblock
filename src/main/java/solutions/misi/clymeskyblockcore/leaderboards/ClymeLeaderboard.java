package solutions.misi.clymeskyblockcore.leaderboards;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ClymeLeaderboard {

    @Getter private int timerInMinutes = 15;
    @Getter @Setter private Map<String, Long> leaderboard = new LinkedHashMap<>();

    public ClymeLeaderboard(int timerInMinutes) {
        this.timerInMinutes = timerInMinutes;
    }

    public abstract void startCalculation();
    public abstract void update(Map<String, Long> queryData);

}