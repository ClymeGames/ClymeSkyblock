package solutions.misi.clymeskyblockcore.player;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class ClymePlayer {

    @Getter private final UUID uuid;
    @Getter private final String username;
    @Getter private final String ip;
    @Getter @Setter private Timestamp firstJoin;
    @Getter @Setter private Timestamp last_join;
    @Getter @Setter private Timestamp banned;
    @Getter @Setter private String banReason;
    @Getter @Setter private Timestamp muted;
    @Getter @Setter private int maxHomes;
    @Getter @Setter private String nickname;

    public ClymePlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.username = getPlayer().getName();
        this.ip = getPlayer().getAddress().getAddress().toString();

        Bukkit.getConsoleSender().sendMessage("[ClymeGames] §7Loading player data from " + getUsername() + "..");

        //> register player to databases
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().registerPlayer(getPlayer());
        ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().registerPlayer(getPlayer());

        //> async load data from database
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().loadClymePlayerData(this);

        //> Evading Errors
        if(nickname == null || nickname.equalsIgnoreCase("NULL") || nickname.equals("")) {
            setNickname(this.username);
        }

        //> save data in cache
        ClymeSkyblockCore.getInstance().getPlayersHandler().getPlaytimeCache().put(this, System.currentTimeMillis());

        //> update player permissions
        updatePermissions();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void sendMessage(String message) {
       getPlayer().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().format(message));
    }

    public void checkBanStatus() {
        //> check if ClymePlayer is banned
        if(getBanned() != null && getBanned().after(getLast_join())) {
            Date today = new Date();
            Date banDate = getBanned();
            String timeLeft = ClymeSkyblockCore.getInstance().getTimeUtil().getTimeDifference(banDate, today);

            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                getPlayer().kickPlayer(" \n" +
                        ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + "\n" +
                        " \n" +
                        " \n" +
                        "§cYou are banned from the Server!\n" +
                        " \n" +
                        "§f§lReason: §7" + getBanReason() + "\n" +
                        "§f§lTime left: §7" + timeLeft + "\n" +
                        "\n\n" +
                        "§c§oIf you think you have been wrongly punished,\n" +
                        "please contact our support team!\n" +
                        "§c§oor purchase a ban evasion in our store: §fshop.clyme.games§c§o!" +
                        "\n");

                Bukkit.getConsoleSender().sendMessage("[ClymeGames] §4Player " + getUsername() + " tried to join but is banned!");
            });
        }
    }

    public void updatePermissions() {
        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(getPlayer());

        //> Update maxHomes
        switch(playerRank) {
            case "dazzle":
                setMaxHomes(3);
                break;
            case "grover":
                setMaxHomes(4);
                break;
            case "olympic":
                setMaxHomes(5);
                break;
            case "ninja":
                setMaxHomes(6);
                break;
            case "supreme":
                setMaxHomes(10);
            case "owner":
                setMaxHomes(14);
                break;
            default:
                setMaxHomes(2);
                break;
        }
    }

    public String getBalance() {
        BigDecimal balance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(getPlayer());
        return ClymeSkyblockCore.getInstance().getNumberFormatter().formatToCurrency(balance);
    }

    public int getIslandLevel() {
        try {
            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(getPlayer());
            return Math.round(superiorPlayer.getIsland().getIslandLevel().intValue());
        } catch(NullPointerException exception) {
            return 0;
        }
    }
}
