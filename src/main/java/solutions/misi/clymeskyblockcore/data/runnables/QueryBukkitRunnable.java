package solutions.misi.clymeskyblockcore.data.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryBukkitRunnable extends BukkitRunnable {

    private final DataSource dataSource;
    private final String statement;
    private final Callback<ResultSet, SQLException> callback;

    public QueryBukkitRunnable(DataSource dataSource, String statement, Callback<ResultSet, SQLException> callback) {
        if(dataSource == null) throw new IllegalArgumentException();
        if(statement == null) throw new IllegalArgumentException();

        this.dataSource = dataSource;
        this.statement = statement;
        this.callback = callback;
    }

    @Override
    public void run() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            callback.call(resultSet, null);
        } catch(SQLException exception) {
            callback.call(null, exception);
        }
    }
}
