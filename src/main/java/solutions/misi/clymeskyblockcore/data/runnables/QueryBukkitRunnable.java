package solutions.misi.clymeskyblockcore.data.runnables;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class QueryBukkitRunnable extends BukkitRunnable {

    @Getter private Object result;

    private final DataSource dataSource;
    private final String statement;
    private final String objective;
    private final CompletableFuture<Object> completableFuture;

    public QueryBukkitRunnable(DataSource dataSource, String statement, String objective, CompletableFuture<Object> completableFuture) {
        if(dataSource == null) throw new IllegalArgumentException();
        if(statement == null) throw new IllegalArgumentException();

        this.dataSource = dataSource;
        this.statement = statement;
        this.objective = objective;
        this.completableFuture = completableFuture;
        this.result = null;
    }

    @Override
    public void run() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                result = completableFuture.complete(resultSet.getObject(objective));
        } catch(SQLException exception) {
            completableFuture.completeExceptionally(exception);
        }
    }
}
