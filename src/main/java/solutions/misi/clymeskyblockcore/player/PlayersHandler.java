package solutions.misi.clymeskyblockcore.player;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.sql.Timestamp;
import java.util.*;

public class PlayersHandler {

    @Getter private final List<ClymePlayer> playerList = new ArrayList<>();

    //> Player data Cache
    @Getter private final Map<ClymePlayer, Long> playtimeCache = new HashMap<>();
    @Getter private final Map<ClymePlayer, ClymePlayer> messagingCache = new HashMap<>();

    public ClymePlayer getClymePlayer(Player player) {
        for(ClymePlayer clymePlayer : playerList) if(clymePlayer.getPlayer() == player) return clymePlayer;
        return null;
    }

    public ClymePlayer getClymePlayer(UUID uuid) {
        for(ClymePlayer clymePlayer : playerList) if(clymePlayer.getUuid() == uuid) return clymePlayer;
        return null;
    }

    public ClymePlayer addClymePlayer(Player player) {
        for(ClymePlayer clymePlayer : playerList) {
            if(clymePlayer.getPlayer() == player) return clymePlayer;
        }

        ClymePlayer clymePlayer = new ClymePlayer(player);
        playerList.add(clymePlayer);
        return clymePlayer;
    }

    public void reloadClymePlayer(ClymePlayer clymePlayer) {
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().saveClymePlayerData(clymePlayer);

        //> Remove clymePlayer
        Player player = clymePlayer.getPlayer();
        playerList.remove(clymePlayer);

        //> Add clymePlayer again
        ClymePlayer newClymePlayer = new ClymePlayer(player);
        playerList.add(newClymePlayer);
    }

    public void removeClymePlayer(ClymePlayer clymePlayer) {
        playerList.remove(clymePlayer);
    }

    public void banPlayer(OfflinePlayer target, Timestamp duration, String reason) {
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().banPlayer(target, duration, reason);

        if(target.isOnline()) {
            ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target.getPlayer());
            if(clymeTarget == null) return;
            clymeTarget.setBanReason(reason);
            clymeTarget.setBanned(duration);

            Date today = new Date();
            Date banDate = duration;
            String timeLeft = ClymeSkyblockCore.getInstance().getTimeUtil().getTimeDifference(banDate, today);

            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                target.getPlayer().kickPlayer(" \n" +
                        ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + "\n" +
                        " \n" +
                        " \n" +
                        "§cYou are banned from the Server!\n" +
                        " \n" +
                        "§f§lReason: §7" + clymeTarget.getBanReason() + "\n" +
                        "§f§lTime left: §7" + timeLeft + "\n" +
                        "\n\n" +
                        "§c§oIf you think you have been wrongly punished,\n" +
                        "please contact our support team!\n" +
                        "§c§oor purchase a ban evasion in our store: §fshop.clyme.games§c§o!" +
                        "\n");
            });

            ClymeSkyblockCore.getInstance().getPlayersHandler().removeClymePlayer(clymeTarget);
        }
    }

    public void mutePlayer(OfflinePlayer target, Timestamp duration) {
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().mutePlayer(target, duration);

        if(target.isOnline()) {
            ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target.getPlayer());
            clymeTarget.setMuted(duration);

            Date today = new Date();
            Date muteDate = duration;
            String timeLeft = ClymeSkyblockCore.getInstance().getTimeUtil().getTimeDifference(muteDate, today);

            clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You got muted for " + ClymeChatColor.SECONDARY() + timeLeft + ClymeChatColor.INFO() + "!");
        }
    }
}
