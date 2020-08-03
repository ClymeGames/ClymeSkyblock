package solutions.misi.clymeskyblockcore.data.tables;

import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.data.runnables.ExecuteBukkitRunnable;
import solutions.misi.clymeskyblockcore.data.runnables.UpdateBukkitRunnable;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class PlayersTable {

    private final String TABLE_NAME = "players";

    public PlayersTable() {
        String statement = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                            + " (uuid VARCHAR(36) PRIMARY KEY,"
                            + "username VARCHAR(255),"
                            + "playtime BIGINT,"
                            + "first_join TIMESTAMP NULL DEFAULT NULL,"
                            + "last_join TIMESTAMP NULL DEFAULT NULL,"
                            + "ip VARCHAR(15))";

        new ExecuteBukkitRunnable(ClymeSkyblockCore.getInstance().getDataSource(), statement, (result, thrown) -> {
        }).runTaskAsynchronously(ClymeSkyblockCore.getInstance());
    }

    public void registerPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        String username = player.getName();
        String ip = player.getAddress().getAddress().toString();
        long playtime = 0;
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        long currentTime = currentDate.getTime();
        Timestamp joined = new Timestamp(currentTime);

        String statement = "INSERT INTO " + TABLE_NAME + " (uuid, username, playtime, first_join, last_join, ip) VALUES "
                                                        + "('" + uuid + "',"
                                                        + "'" + username + "',"
                                                        + "'" + playtime + "',"
                                                        + "'" + joined + "',"
                                                        + "'" + joined + "',"
                                                        + "'" + ip + "')"
                                                        + " ON DUPLICATE KEY UPDATE 'username' = '" + username + "'";

        new UpdateBukkitRunnable(ClymeSkyblockCore.getInstance().getDataSource(), statement, ((result, thrown) -> {
        })).runTaskAsynchronously(ClymeSkyblockCore.getInstance());
    }
}
