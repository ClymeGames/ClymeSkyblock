package solutions.misi.clymeskyblockcore.data.tables;

import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.data.runnables.ExecuteBukkitRunnable;
import solutions.misi.clymeskyblockcore.data.runnables.QueryBukkitRunnable;
import solutions.misi.clymeskyblockcore.data.runnables.UpdateBukkitRunnable;

import java.util.concurrent.CompletableFuture;

public class EconomyTable {

    private final String TABLE_NAME = "economy";

    public EconomyTable() {
        String statement = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (uuid VARCHAR(36) PRIMARY KEY,"
                + "balance FLOAT(53))";

        new ExecuteBukkitRunnable(ClymeSkyblockCore.getInstance().getDataSource(), statement).runTaskAsynchronously(ClymeSkyblockCore.getInstance());
    }

    public void registerPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        double balance = 1000.0;

        String statement = "INSERT INTO " + TABLE_NAME + " (uuid, balance) VALUES "
                + "('" + uuid + "',"
                + "'" + balance + "',"
                + " ON DUPLICATE KEY UPDATE balance = '" + balance + "'";

        new UpdateBukkitRunnable(ClymeSkyblockCore.getInstance().getDataSource(), statement).runTaskAsynchronously(ClymeSkyblockCore.getInstance());
    }

    @SneakyThrows
    public double getBalance(Player player) {
        String uuid = player.getUniqueId().toString();

        String statement = "SELECT * FROM " + TABLE_NAME + " WHERE uuid = "
                + "'" + uuid + "'";

        String objective = "balance";

        CompletableFuture<Object> result = new CompletableFuture<>();

        new QueryBukkitRunnable(ClymeSkyblockCore.getInstance().getDataSource(), statement, objective, result).runTaskAsynchronously(ClymeSkyblockCore.getInstance());
        return (double) result.get();
    }

    public void setBalance(Player player, double balance) {
        String uuid = player.getUniqueId().toString();

        String statement = "UPDATE " + TABLE_NAME + " SET balance = "
                + "'" + balance + "' WHERE uuid = "
                + "'" + uuid + "'";

        new UpdateBukkitRunnable(ClymeSkyblockCore.getInstance().getDataSource(), statement).runTaskAsynchronously(ClymeSkyblockCore.getInstance());
    }

    public void addBalance(Player player, double balance) {
        double currentBalance = getBalance(player);
        setBalance(player, currentBalance+balance);
    }

    public void removeBalance(Player player, double balance) {
        double currentBalance = getBalance(player);
        setBalance(player, currentBalance-balance);
    }
}
