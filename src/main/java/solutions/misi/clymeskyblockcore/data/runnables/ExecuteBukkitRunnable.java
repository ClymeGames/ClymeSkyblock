package solutions.misi.clymeskyblockcore.data.runnables;

import org.bukkit.scheduler.BukkitRunnable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecuteBukkitRunnable extends BukkitRunnable {

    private final DataSource dataSource;
    private final String statement;

    public ExecuteBukkitRunnable(DataSource dataSource, String statement) {
        if(dataSource == null) throw new IllegalArgumentException();
        if(statement == null) throw new IllegalArgumentException();

        this.dataSource = dataSource;
        this.statement = statement;
    }

    @Override
    public void run() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
            preparedStatement.execute();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }
}
