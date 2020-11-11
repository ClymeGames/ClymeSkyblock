package solutions.misi.clymeskyblockcore.data.mysql;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ClymePlayersTable {

    public ClymePlayersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS clymePlayers"
                            + " (uuid VARCHAR(36) PRIMARY KEY,"
                            + "username VARCHAR(255),"
                            + "playtime BIGINT,"
                            + "first_join TIMESTAMP NULL DEFAULT NULL,"
                            + "last_join TIMESTAMP NULL DEFAULT NULL,"
                            + "ip VARCHAR(255),"
                            + "banned TIMESTAMP NULL DEFAULT NULL,"
                            + "banReason VARCHAR(255),"
                            + "muted TIMESTAMP NULL DEFAULT NULL,"
                            + "maxHomes INT)";

        try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                PreparedStatement createTable = connection.prepareStatement(sql)) {
            createTable.execute();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
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
        int maxHomes = 3;

        String sql = "INSERT INTO clymePlayers (uuid, username, playtime, first_join, last_join, ip, maxHomes) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE last_join = ?";

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement insertOrUpdate = connection.prepareStatement(sql)) {
                insertOrUpdate.setString(1, uuid);
                insertOrUpdate.setString(2, username.toLowerCase());
                insertOrUpdate.setLong(3, playtime);
                insertOrUpdate.setTimestamp(4, joined);
                insertOrUpdate.setTimestamp(5, joined);
                insertOrUpdate.setString(6, ip);
                insertOrUpdate.setTimestamp(7, joined);
                insertOrUpdate.setInt(8, maxHomes);
                insertOrUpdate.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void loadClymePlayerData(ClymePlayer clymePlayer) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                    PreparedStatement select = connection.prepareStatement("SELECT * FROM clymePlayers WHERE uuid = ?")) {
                select.setString(1, clymePlayer.getUuid().toString());
                ResultSet resultSet = select.executeQuery();
                if(resultSet.next()) {
                    clymePlayer.setPlaytime(resultSet.getLong("playtime"));
                    clymePlayer.setFirstJoin(resultSet.getTimestamp("first_join"));
                    clymePlayer.setLast_join(resultSet.getTimestamp("last_join"));
                    clymePlayer.setBanned(resultSet.getTimestamp("banned"));
                    clymePlayer.setBanReason(resultSet.getString("banReason"));
                    clymePlayer.setMuted(resultSet.getTimestamp("muted"));
                    clymePlayer.setMaxHomes(resultSet.getInt("maxHomes"));
                    clymePlayer.checkBanStatus();
                }
                resultSet.close();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void saveClymePlayerData(ClymePlayer clymePlayer) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                    PreparedStatement update = connection.prepareStatement("UPDATE clymePlayers SET playtime = ?, ip = ?, maxHomes = ? WHERE uuid = ?")) {
                update.setLong(1, clymePlayer.getPlaytime());
                update.setString(2, clymePlayer.getIp());
                update.setString(3, clymePlayer.getUuid().toString());
                update.setInt(4, clymePlayer.getMaxHomes());
                update.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public UUID getUuidFromName(String username) {
        try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                PreparedStatement select = connection.prepareStatement("SELECT uuid FROM clymePlayers WHERE username = ?")) {
            select.setString(1, username.toLowerCase());
            ResultSet resultSet = select.executeQuery();
            if(resultSet.next()) return UUID.fromString(resultSet.getString("uuid"));
            resultSet.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void banPlayer(OfflinePlayer player, Timestamp duration, String reason) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement update = connection.prepareStatement("UPDATE clymePlayers SET banned = ?, banReason = ? WHERE uuid = ?")) {
                update.setTimestamp(1, duration);
                update.setString(2, reason);
                update.setString(3, player.getUniqueId().toString());
                update.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void unbanPlayer(OfflinePlayer player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            Timestamp currentTime = new Timestamp(new Date().getTime());
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                    PreparedStatement update = connection.prepareStatement("UPDATE clymePlayers SET banned = ?, banReason = ? WHERE uuid = ?")) {
                update.setTimestamp(1, currentTime);
                update.setString(2, "");
                update.setString(3, player.getUniqueId().toString());
                update.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void mutePlayer(OfflinePlayer player, Timestamp duration) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
           try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                    PreparedStatement update = connection.prepareStatement("UPDATE clymePlayers SET muted = ? WHERE uuid = ?")) {
               update.setTimestamp(1, duration);
               update.setString(2, player.getUniqueId().toString());
               update.executeUpdate();
           } catch(SQLException exception) {
               exception.printStackTrace();
           }
        });
    }

    public void unmutePlayer(OfflinePlayer player) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            Timestamp currentTime = new Timestamp(new Date().getTime());
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                    PreparedStatement update = connection.prepareStatement("UPDATE clymePlayers SET muted = ? WHERE uuid = ?")) {
                update.setTimestamp(1, currentTime);
                update.setString(2, player.getUniqueId().toString());
                update.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
}
