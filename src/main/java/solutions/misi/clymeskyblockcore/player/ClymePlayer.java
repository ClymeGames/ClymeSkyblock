package solutions.misi.clymeskyblockcore.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.sql.Timestamp;
import java.util.UUID;

public class ClymePlayer {

    @Getter private final UUID uuid;
    @Getter private final String username;
    @Getter private final String ip;
    @Getter @Setter private long playtime;
    @Getter @Setter private Timestamp firstJoin;
    @Getter @Setter private Timestamp last_join;

    public ClymePlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.username = getPlayer().getName();
        this.ip = getPlayer().getAddress().getAddress().toString();

        //> register player to databases
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().registerPlayer(getPlayer());
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().registerPlayer(getPlayer());

        //> async load data from clymeplayers database
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().loadClymePlayerData(this);

        //> save data in cache
        ClymeSkyblockCore.getInstance().getPlayersHandler().getPlaytimeCache().put(this, System.currentTimeMillis());

        Bukkit.getConsoleSender().sendMessage("[ClymeGames] §aPlayer data from " + getUsername() + " has been loaded!");
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(message));
    }
}