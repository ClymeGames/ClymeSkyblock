package solutions.misi.clymeskyblockcore.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ClymePlayer {

    @Getter private final UUID uuid;
    @Getter private final String username;
    @Getter private final String ip;
    @Getter @Setter private long playtime;
    @Getter @Setter private Timestamp firstJoin;
    @Getter @Setter private Timestamp last_join;
    @Getter @Setter private Timestamp banned;
    @Getter @Setter private String banReason;

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

    public void checkBanStatus() {
        //> check if clymeplayer is banned
        if(getBanned() != null && getBanned().after(getLast_join())) {
            Date today = new Date();
            Date banDate = getBanned();
            long timeDifference = banDate.getTime() - today.getTime();

            long daysLeft = TimeUnit.MILLISECONDS.toDays(timeDifference);
            timeDifference -= TimeUnit.DAYS.toMillis(daysLeft);
            long hoursLeft = TimeUnit.MILLISECONDS.toHours(timeDifference);
            timeDifference -= TimeUnit.HOURS.toMillis(hoursLeft);
            long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
            timeDifference -= TimeUnit.MINUTES.toMillis(minutesLeft);
            long secondsLeft = TimeUnit.MILLISECONDS.toSeconds(timeDifference);

            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                getPlayer().kickPlayer(" \n" +
                        ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + "\n" +
                        " \n" +
                        " \n" +
                        "§cYou are banned from the Server!\n" +
                        " \n" +
                        "§f§lReason: §7" + getBanReason() + "\n" +
                        "§f§lTime left: §7" + daysLeft + " day(s) " + hoursLeft + " hour(s) " + minutesLeft + " minute(s) " + secondsLeft + " second(s)\n" +
                        "\n\n" +
                        "§c§oIf you think you have been wrongly punished,\n" +
                        "please contact our support team!\n" +
                        "§c§oor purchase a ban evasion in our store: §fshop.clyme.games§c§o!" +
                        "\n");

                Bukkit.getConsoleSender().sendMessage("[ClymeGames] §4Player " + getUsername() + " tried to join but is banned!");
            });
        }
    }
}
