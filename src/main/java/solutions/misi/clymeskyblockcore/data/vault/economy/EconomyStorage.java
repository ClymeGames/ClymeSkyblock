package solutions.misi.clymeskyblockcore.data.vault.economy;

import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class EconomyStorage {

    public void registerPlayer(Player player) {
        if (userBalanceExists(player)) return;
        setBalance(player, 1000);
    }

    public boolean userBalanceExists(Player player) {
        Jedis jedis = null;
        Pipeline pipeline;
        String uuid = player.getUniqueId().toString();
        boolean result;

        try {
            jedis = ClymeSkyblockCore.getInstance().getJedisPool().getResource();
            pipeline = jedis.pipelined();
            Response<Boolean> pipelineResponse = pipeline.exists("balance." + uuid);
            pipeline.sync();
            result = pipelineResponse.get();
        } finally {
            jedis.close();
        }

        return result;
    }

    public double getBalance(Player player) {
        if(!userBalanceExists(player)) return -1;

        Jedis jedis = null;
        Pipeline pipeline;
        String uuid = player.getUniqueId().toString();
        double result;

        try {
            jedis = ClymeSkyblockCore.getInstance().getJedisPool().getResource();
            pipeline = jedis.pipelined();
            Response<String> pipelineResponse = pipeline.get("balance." + uuid);
            pipeline.sync();
            result = Double.parseDouble(pipelineResponse.get());
        } finally {
            jedis.close();
        }

        return result;
    }

    public void setBalance(Player player, double balance) {
        Jedis jedis = null;
        Pipeline pipeline;
        String uuid = player.getUniqueId().toString();

        try {
            jedis = ClymeSkyblockCore.getInstance().getJedisPool().getResource();
            pipeline = jedis.pipelined();
            pipeline.set("balance." + uuid, String.valueOf(balance));
            pipeline.sync();
        } finally {
            jedis.close();
        }
    }
}