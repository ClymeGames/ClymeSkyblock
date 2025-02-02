package solutions.misi.clymeskyblockcore.data.mysql;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClymeHomesTable {

    public ClymeHomesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS clymeHomes"
                + " (uuid VARCHAR(36),"
                + "world VARCHAR(255),"
                + "x DOUBLE,"
                + "y DOUBLE,"
                + "z DOUBLE,"
                + "yaw DOUBLE,"
                + "pitch DOUBLE,"
                + "name VARCHAR(255))";

        try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
             PreparedStatement createTable = connection.prepareStatement(sql)) {
            createTable.execute();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setHome(Player player, Location location, String name) {
        String uuid = player.getUniqueId().toString();
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        double yaw = location.getYaw();
        double pitch = location.getPitch();

        String sql = "INSERT INTO clymeHomes (uuid, world, x, y, z, yaw, pitch, name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement insert = connection.prepareStatement(sql)) {
                insert.setString(1, uuid);
                insert.setString(2, world);
                insert.setDouble(3, x);
                insert.setDouble(4, y);
                insert.setDouble(5, z);
                insert.setDouble(6, yaw);
                insert.setDouble(7, pitch);
                insert.setString(8, name);
                insert.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void changeHomeLocation(Player player, Location newLocation, String name) {
        String uuid = player.getUniqueId().toString();
        String world = newLocation.getWorld().getName();
        double x = newLocation.getX();
        double y = newLocation.getY();
        double z = newLocation.getZ();
        double yaw = newLocation.getYaw();
        double pitch = newLocation.getPitch();

        String sql = "UPDATE clymeHomes SET world = ?, x = ?, y = ?, z = ?, yaw = ?, pitch = ? WHERE uuid = ? AND name = ?";

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement update = connection.prepareStatement(sql)) {
                update.setString(1, world);
                update.setDouble(2, x);
                update.setDouble(3, y);
                update.setDouble(4, z);
                update.setDouble(5, yaw);
                update.setDouble(6, pitch);
                update.setString(7, uuid);
                update.setString(8, name);
                update.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void deleteHome(Player player, String name) {
        String uuid = player.getUniqueId().toString();
        String sql = "DELETE FROM clymeHomes WHERE uuid = ? AND name = ?";

        Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> {
            try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
                 PreparedStatement delete = connection.prepareStatement(sql)) {
                delete.setString(1, uuid);
                delete.setString(2, name);
                delete.executeUpdate();
            } catch(SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public Map<Location, String> getPlayerHomes(Player player) {
        Map<Location, String> playerHomes = new HashMap<>();

        try (Connection connection = ClymeSkyblockCore.getInstance().getDataSource().getConnection();
             PreparedStatement select = connection.prepareStatement("SELECT * FROM clymeHomes WHERE uuid = ?")) {
            select.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = select.executeQuery();
            while(resultSet.next()) {
                String world = resultSet.getString("world");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                double yaw = resultSet.getDouble("yaw");
                double pitch = resultSet.getDouble("pitch");

                Location location = new Location(Bukkit.getWorld(world), x, y, z);
                location.setYaw((float) yaw);
                location.setPitch((float) pitch);
                String name = resultSet.getString("name");

                playerHomes.put(location, name);
            }

            resultSet.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }

        return playerHomes;
    }

    public List<Location> getPlayerHomesList(Player player) {
        Map<Location, String> playerHomesMap = getPlayerHomes(player);
        List<Location> playerHomesList = new ArrayList<>();
        for(Map.Entry<Location, String> home : playerHomesMap.entrySet())  { playerHomesList.add(home.getKey()); }

        return playerHomesList;
    }

    public Location getPlayerHomeFromName(Player player, String name) {
        Map<Location, String> playerHomes = getPlayerHomes(player);

        for(Map.Entry<Location, String> home : playerHomes.entrySet()) {
            if(home.getValue().equals(name)) return home.getKey();
        }

        return null;
    }
}