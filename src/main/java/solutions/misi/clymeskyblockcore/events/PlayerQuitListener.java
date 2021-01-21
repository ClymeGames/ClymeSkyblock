package solutions.misi.clymeskyblockcore.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        event.setQuitMessage("");

        //> Save Player data
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().saveClymePlayerData(clymePlayer);
        ClymeSkyblockCore.getInstance().getDataManager().getClymeStatisticsTable().saveClymeStatistics(clymePlayer);

        //> Screenshare Ban
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getScreensharing().contains(player))
            ClymeSkyblockCore.getInstance().getScreenshare().banPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));

        //> Attack Speed Base Value
        if (player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getBaseValue() != 4.0D)
            player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0D);

        //> Combat Log
        if(ClymeSkyblockCore.getInstance().getCombatLog().getInCombat().containsKey(clymePlayer))
            player.setHealth(0.0);

        Bukkit.getConsoleSender().sendMessage("§c" + player.getName() + " has left the Server!");
    }
}
