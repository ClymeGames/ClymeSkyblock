package solutions.misi.clymeskyblockcore.gui.staffpanel;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffpanelGUI implements Listener {

    @Getter private final List<Player> enteringPlayerManager = new ArrayList<>();

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Staffpanel");

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack managePlayer = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta managePlayerMeta = managePlayer.getItemMeta();
        managePlayerMeta.setDisplayName("§aManage a player");
        List<String> managePlayerLore = new ArrayList<>();
        managePlayerLore.add(" ");
        managePlayerLore.add("§7Right-Click and enter a username");
        managePlayerLore.add("§7to manage the selected player.");
        managePlayerLore.add(" ");
        managePlayerMeta.setLore(managePlayerLore);
        managePlayer.setItemMeta(managePlayerMeta);

        ItemStack manageServer = new ItemStack(Material.COMMAND_BLOCK);
        ItemMeta manageServerMeta = manageServer.getItemMeta();
        manageServerMeta.setDisplayName("§aManage the server");
        List<String> manageServerLore = new ArrayList<>();
        manageServerLore.add(" ");
        manageServerLore.add("§7Right-Click to open the menu for");
        manageServerLore.add("§7editing the server you are playing on.");
        manageServerLore.add(" ");
        manageServerMeta.setLore(manageServerLore);
        manageServer.setItemMeta(manageServerMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        gui.setItem(4, managePlayer);
        gui.setItem(6, manageServer);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Staffpanel")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        try {
            if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aManage a player")) {
                if(!ClymeSkyblockCore.getInstance().getStaffpanelGUI().getEnteringPlayerManager().contains(player)) ClymeSkyblockCore.getInstance().getStaffpanelGUI().getEnteringPlayerManager().add(player);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Please enter the desired username!");
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§aManage the server")) {
                //> TODO: make staff open the server gui
            }
        } catch(NullPointerException exception) { }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(!ClymeSkyblockCore.getInstance().getStaffpanelGUI().getEnteringPlayerManager().contains(player)) return;

        //> Staff entered playername in chat
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String targetName = event.getMessage();
        UUID targetUuid = ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getUuidFromName(targetName);

        ClymeSkyblockCore.getInstance().getStaffpanelGUI().getEnteringPlayerManager().remove(player);
        event.setCancelled(true);

        if(targetUuid == null) {
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + targetName + ClymeChatColor.ERROR() + " does not exist!");
            return;
        }

        ClymeSkyblockCore.getInstance().getStaffpanelPlayerGui().open(player, targetUuid);
    }
}
