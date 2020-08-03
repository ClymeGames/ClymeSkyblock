package solutions.misi.clymeskyblockcore.data.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateBukkitRunnable extends BukkitRunnable {

    private final DataSource dataSource;
    private final String statement;
    private final Callback<Integer, SQLException> callback;

    public UpdateBukkitRunnable(DataSource dataSource, String statement, @Nullable Callback<Integer, SQLException> callback) {
        if(dataSource == null) throw new IllegalArgumentException();
        if(statement == null) throw new IllegalArgumentException();

        this.dataSource = dataSource;
        this.statement = statement;
        this.callback = callback;
    }

    @Override
    public void run() {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            if(callback != null) callback.call(preparedStatement.executeUpdate(), null);
        } catch(SQLException exception) {
            if(callback != null) callback.call(null, exception);
        }
    }
}
