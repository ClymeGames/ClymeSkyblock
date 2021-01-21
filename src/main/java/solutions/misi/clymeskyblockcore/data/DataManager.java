package solutions.misi.clymeskyblockcore.data;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import redis.clients.jedis.BinaryShardedJedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.data.mysql.ClymeHomesTable;
import solutions.misi.clymeskyblockcore.data.mysql.ClymePlayersTable;
import solutions.misi.clymeskyblockcore.data.mysql.ClymeStatisticsTable;
import solutions.misi.clymeskyblockcore.data.vault.economy.EconomyStorage;

import java.io.File;
import java.io.IOException;

public class DataManager {

    @Getter private FileConfiguration databaseFileCfg;

    //> Tables
    @Getter private ClymePlayersTable clymePlayersTable;
    @Getter private ClymeStatisticsTable clymeStatisticsTable;
    @Getter private ClymeHomesTable clymeHomesTable;
    @Getter private EconomyStorage economyStorage;

    public DataManager() {
        try {
            File databaseFile = new File("database.yml");
            databaseFileCfg = new YamlConfiguration();
            if(!databaseFile.exists()) databaseFile.createNewFile();
            databaseFileCfg.load(databaseFile);
        } catch(IOException | InvalidConfigurationException ex) {}
    }

    public void initializeDatabases() {
        String sqlHost = databaseFileCfg.getString("mysql.host");
        String sqlPort = databaseFileCfg.getString("mysql.port");
        String sqlDatabase = databaseFileCfg.getString("mysql.database");
        String redisHost = databaseFileCfg.getString("redis.host");
        String redisPort = databaseFileCfg.getString("redis.port");

        ClymeSkyblockCore.getInstance().getDataSource().setJdbcUrl("jdbc:mysql://" + sqlHost + ":" + sqlPort + "/" + sqlDatabase);
        ClymeSkyblockCore.getInstance().getDataSource().addDataSourceProperty("user", databaseFileCfg.getString("mysql.user"));
        ClymeSkyblockCore.getInstance().getDataSource().addDataSourceProperty("password", databaseFileCfg.getString("mysql.password"));

        ClassLoader context = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(BinaryShardedJedis.class.getClassLoader());
        ClymeSkyblockCore.getInstance().setJedisPool(new JedisPool(new JedisPoolConfig(), redisHost, Integer.parseInt(redisPort), 5000));
        Thread.currentThread().setContextClassLoader(context);

        //> Create tables
        clymePlayersTable = new ClymePlayersTable();
        clymeStatisticsTable = new ClymeStatisticsTable();
        clymeHomesTable = new ClymeHomesTable();
        economyStorage = new EconomyStorage();
    }

    public void closeDatabases() {
        if(ClymeSkyblockCore.getInstance().getDataSource() != null)
            ClymeSkyblockCore.getInstance().getDataSource().close();
        if(ClymeSkyblockCore.getInstance().getJedisPool() != null)
            ClymeSkyblockCore.getInstance().getJedisPool().close();
    }
}
