package solutions.misi.clymeskyblockcore.data.mysql;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.player.ClymeStatistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClymeStatisticsTable {

    public ClymeStatisticsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS clymeStatistics"
                + " (uuid VARCHAR(36) PRIMARY KEY,"
                + "sugarcane_broken BIGINT)";

        try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
             PreparedStatement createTable = connection.prepareStatement(sql)) {
            createTable.execute();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void registerPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        long sugarcaneBroken = 0;

        String sql = "INSERT INTO clymeStatistics (uuid, sugarcane_broken) VALUES (?, ?)";

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement insert = connection.prepareStatement(sql)) {
                insert.setString(1, uuid);
                insert.setLong(2, sugarcaneBroken);
                insert.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void loadClymeStatistics(ClymeStatistics clymeStatistics) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement select = connection.prepareStatement("SELECT * FROM clymeStatistics WHERE uuid = ?")) {
                select.setString(1, clymeStatistics.getClymePlayer().getUuid().toString());
                ResultSet resultSet = select.executeQuery();
                if(resultSet.next()) {
                    clymeStatistics.setSugarcaneBroken(resultSet.getLong("sugarcane_broken"));
                }

                resultSet.close();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void saveClymeStatistics(ClymePlayer clymePlayer) {
        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement update = connection.prepareStatement("UPDATE clymeStatistics SET sugarcane_broken = ? WHERE uuid = ?")) {
                update.setLong(1, clymePlayer.getClymeStatistics().getSugarcaneBroken());
                update.setString(2, clymePlayer.getUuid().toString());
                update.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public Map<String, Long> querySugarcaneTop() {
        Map<String, Long> sugarcaneTop = new LinkedHashMap<>();

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement select = connection.prepareStatement("SELECT uuid, sugarcane_broken FROM clymeStatistics ORDER BY sugarcane_broken LIMIT 5")) {
                ResultSet resultSet = select.executeQuery();
                while(resultSet.next()) sugarcaneTop.put(resultSet.getString("uuid"), resultSet.getLong("sugarcane_broken"));
                resultSet.close();
                ClymeSkyblockCore.getInstance().getSugarcaneLeaderboard().update(sugarcaneTop);
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });

        return sugarcaneTop;
    }
}
