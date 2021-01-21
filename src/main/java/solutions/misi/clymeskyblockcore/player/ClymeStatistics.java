package solutions.misi.clymeskyblockcore.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class ClymeStatistics {

    @Getter private final ClymePlayer clymePlayer;
    @Getter @Setter private long sugarcaneBroken;

    public ClymeStatistics(ClymePlayer clymePlayer) {
        this.clymePlayer = clymePlayer;

        //> async load data from database
        ClymeSkyblockCore.getInstance().getDataManager().getClymeStatisticsTable().loadClymeStatistics(this);
        Bukkit.getConsoleSender().sendMessage("                §7..Statistics loaded!");
    }
}
