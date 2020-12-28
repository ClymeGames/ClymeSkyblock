package solutions.misi.clymeskyblockcore.data.vault.economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.math.BigDecimal;

public class EconomyStorage {

    public void registerPlayer(Player player) {
        if (userBalanceExists(player)) return;
        setBalance(player, new BigDecimal(1000));
    }

    public boolean userBalanceExists(OfflinePlayer player) {
        Jedis jedis = null;
        Pipeline pipeline;
        String uuid = player.getUniqueId().toString();
        boolean result;

        try {
            jedis = ClymeSkyblockCore.getInstance().getJedisPool().getResource();
            jedis.auth(ClymeSkyblockCore.getInstance().getDataManager().getDatabaseFileCfg().getString("redis.password"));
            pipeline = jedis.pipelined();
            Response<Boolean> pipelineResponse = pipeline.exists("balance." + uuid);
            pipeline.sync();
            result = pipelineResponse.get();
        } finally {
            jedis.close();
        }

        return result;
    }

    public BigDecimal getBalance(OfflinePlayer player) {
        if(!userBalanceExists(player)) return null;

        Jedis jedis = null;
        Pipeline pipeline;
        String uuid = player.getUniqueId().toString();
        BigDecimal result;

        try {
            jedis = ClymeSkyblockCore.getInstance().getJedisPool().getResource();
            jedis.auth(ClymeSkyblockCore.getInstance().getDataManager().getDatabaseFileCfg().getString("redis.password"));
            pipeline = jedis.pipelined();
            Response<String> pipelineResponse = pipeline.get("balance." + uuid);
            pipeline.sync();
            result = new BigDecimal(pipelineResponse.get());
        } finally {
            jedis.close();
        }

        return result;
    }

    public void setBalance(OfflinePlayer player, BigDecimal balance) {
        Jedis jedis = null;
        Pipeline pipeline;
        String uuid = player.getUniqueId().toString();

        try {
            jedis = ClymeSkyblockCore.getInstance().getJedisPool().getResource();
            jedis.auth(ClymeSkyblockCore.getInstance().getDataManager().getDatabaseFileCfg().getString("redis.password"));
            pipeline = jedis.pipelined();
            pipeline.set("balance." + uuid, String.valueOf(balance));
            pipeline.sync();
        } finally {
            jedis.close();
        }
    }
}