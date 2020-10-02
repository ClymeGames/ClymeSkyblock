package solutions.misi.clymeskyblockcore.events;

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

        ClymeSkyblockCore.getInstance().getPlayersHandler().updatePlayerData(clymePlayer);
        ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().saveClymePlayerData(clymePlayer);
        ClymeSkyblockCore.getInstance().getPlayersHandler().removeClymePlayer(clymePlayer);
    }
}
