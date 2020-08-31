package solutions.misi.clymeskyblockcore.data;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.data.tables.EconomyTable;
import solutions.misi.clymeskyblockcore.data.tables.PlayersTable;

import java.io.File;
import java.io.IOException;

public class SqlManager {

    private FileConfiguration databaseFileCfg;

    //> Tables
    @Getter private PlayersTable playersTable;
    @Getter private EconomyTable economyTable;

    public SqlManager() {
        try {
            File databaseFile = new File("database.yml");
            databaseFileCfg = new YamlConfiguration();
            if(!databaseFile.exists()) databaseFile.createNewFile();
            databaseFileCfg.load(databaseFile);
        } catch(IOException | InvalidConfigurationException ex) {}
    }

    public void initializeDatabase() {
        String host = databaseFileCfg.getString("host");
        String port = databaseFileCfg.getString("port");
        String database = databaseFileCfg.getString("database");

        ClymeSkyblockCore.getInstance().getDataSource().setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        ClymeSkyblockCore.getInstance().getDataSource().addDataSourceProperty("user", databaseFileCfg.getString("user"));
        ClymeSkyblockCore.getInstance().getDataSource().addDataSourceProperty("password", databaseFileCfg.getString("password"));

        //> Create tables
        playersTable = new PlayersTable();
        economyTable = new EconomyTable();
    }

    public void closeDataSource() {
        if(ClymeSkyblockCore.getInstance().getDataSource() == null) return;
        ClymeSkyblockCore.getInstance().getDataSource().close();
    }
}
