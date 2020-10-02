package solutions.misi.clymeskyblockcore.player;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
