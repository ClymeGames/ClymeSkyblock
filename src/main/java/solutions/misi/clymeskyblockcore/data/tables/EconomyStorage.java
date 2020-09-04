package solutions.misi.clymeskyblockcore.data.tables;

import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
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
            result = pipeline.exists("balance." + uuid).get();
            pipeline.sync();
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
            result = Double.parseDouble(pipeline.get("balance." + uuid).get());
            pipeline.sync();
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

    public void addBalance(Player player, double balance) {
        setBalance(player, getBalance(player) + balance);
    }

    public void removeBalance(Player player, double balance) {
        setBalance(player, getBalance(player) - balance);
    }
}