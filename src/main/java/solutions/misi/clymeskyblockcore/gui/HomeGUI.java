package solutions.misi.clymeskyblockcore.gui;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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
import java.util.Map;

public class HomeGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Home");
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        List<Location> playerHomes = new ArrayList<>(clymePlayer.getHomes().keySet());

        ItemStack noHomeSet = new ItemStack(Material.WHITE_BED);
        ItemMeta noHomeSetMeta = noHomeSet.getItemMeta();
        noHomeSetMeta.setDisplayName("§fNo Home set");
        List<String> noHomeSetLore = new ArrayList<>();
        noHomeSetLore.add(" ");
        noHomeSetLore.add("§f➢ §cThis home slot is empty.");
        noHomeSetLore.add(" ");
        noHomeSetLore.add("§7Right-Click to set this home at your current position!");
        noHomeSetLore.add(" ");
        noHomeSetMeta.setLore(noHomeSetLore);
        noHomeSet.setItemMeta(noHomeSetMeta);

        List<ItemStack> playerHomeItems = new ArrayList<>();
        for(int i = 0; i < clymePlayer.getMaxHomes(); i++) {
            ItemStack home = noHomeSet.clone();

            if(playerHomes.size() > i) {
                home.setType(Material.ORANGE_BED);
                ItemMeta homeMeta = home.getItemMeta();
                homeMeta.setDisplayName(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.ACCENT() + "§l" + clymePlayer.getHomes().get(playerHomes.get(i))));
                List<String> homeMetaLore = new ArrayList<>();
                homeMetaLore.add(" ");
                homeMetaLore.add("§f➢ §7World: " + playerHomes.get(i).getWorld().getName());
                homeMetaLore.add("§f➢ §7X: " + playerHomes.get(i).getBlockX());
                homeMetaLore.add("§f➢ §7Y: " + playerHomes.get(i).getBlockY());
                homeMetaLore.add("§f➢ §7Z: " + playerHomes.get(i).getBlockZ());
                homeMetaLore.add(" ");
                homeMetaLore.add("§7Left-Click to teleport to this home!");
                homeMetaLore.add("§7Middle-Click to delete this home!");
                homeMetaLore.add("§7Right-Click to set this home at your current position!");
                homeMetaLore.add(" ");
                homeMeta.setLore(homeMetaLore);
                home.setItemMeta(homeMeta);
            }

            playerHomeItems.add(home);
        }

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);
        for(int i = 10; i < gui.getSize(); i++) {
            if(i == 17 || i == 18 || i > 25) continue;
            if(playerHomeItems.isEmpty()) break;
            gui.setItem(i, playerHomeItems.get(0));
            playerHomeItems.remove(0);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        try {
            if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Home")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return;
                if(event.getClickedInventory() != event.getView().getTopInventory()) return;

                ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
                String homeName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

                //> Set new home point
                if(event.getCurrentItem().getType() == Material.WHITE_BED) {
                    player.closeInventory();

                    if(!ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().containsKey(clymePlayer)) {
                        ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().put(clymePlayer, null);
                    }

                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Please enter the name for this Home!");
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Or " + ClymeChatColor.SECONDARY() + "EXIT" + ClymeChatColor.INFO() + " to cancel!");
                    return;
                }

                //> Change location of existing Home
                if(event.getCurrentItem().getType() == Material.ORANGE_BED && event.getClick() == ClickType.RIGHT) {
                    ClymeSkyblockCore.getInstance().getDataManager().getClymeHomesTable().deleteHome(player, homeName);
                    ClymeSkyblockCore.getInstance().getDataManager().getClymeHomesTable().setHome(player, player.getLocation(), homeName);
                    ClymeSkyblockCore.getInstance().getPlayersHandler().updatePlayerData(clymePlayer);

                    refresh(player);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully changed the location of " + ClymeChatColor.SECONDARY() + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()) + ClymeChatColor.SUCCESS() + "!");
                    return;
                }

                //> Delete existing Home
                if(event.getCurrentItem().getType() == Material.ORANGE_BED && event.getClick() == ClickType.MIDDLE) {
                    ClymeSkyblockCore.getInstance().getDataManager().getClymeHomesTable().deleteHome(player, homeName);
                    ClymeSkyblockCore.getInstance().getPlayersHandler().updatePlayerData(clymePlayer);

                    refresh(player);
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully deleted home " + ClymeChatColor.SECONDARY() + homeName + ClymeChatColor.SUCCESS() + "!");
                    return;
                }

                //> Teleport to home
                if(event.getCurrentItem().getType() == Material.ORANGE_BED && event.getClick() == ClickType.LEFT) {
                    Location home = ClymeSkyblockCore.getInstance().getPlayersHandler().getPlayerHomeFromName(clymePlayer, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully teleported to " + ClymeChatColor.SECONDARY() + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())  + ClymeChatColor.SUCCESS() + "..");

                    player.closeInventory();
                    player.teleportAsync(home);
                    player.playSound(player.getLocation(), Sound.ENTITY_FOX_TELEPORT, 1.0F, 1.0F);
                }
            }
        } catch(NullPointerException ex) {}
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String homeName = event.getMessage();

        //> Create player home
        if(!ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().containsKey(clymePlayer)) return;
        event.setCancelled(true);

        Map<Location, String> playerHomes = clymePlayer.getHomes();

        if(playerHomes.containsValue(homeName)) {
            if(!homeName.equals(ChatColor.stripColor(ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().get(clymePlayer)))) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You already have a home called " + ClymeChatColor.SECONDARY() + homeName);
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Please try something else or use " + ClymeChatColor.SECONDARY() + "EXIT" + ClymeChatColor.INFO() + " to cancel!");
                return;
            }
        }

        if(ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().get(clymePlayer) != null) {
            for(Map.Entry<Location, String> entry : clymePlayer.getHomes().entrySet()) {
                if(entry.getValue().equals(ChatColor.stripColor(ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().get(clymePlayer)))) {
                    playerHomes.remove(entry.getKey()); break;
                }
            }
        }

        playerHomes.put(player.getLocation(), homeName);
        clymePlayer.setHomes(playerHomes);
        ClymeSkyblockCore.getInstance().getDataManager().getClymeHomesTable().saveClymePlayerData(clymePlayer);
        ClymeSkyblockCore.getInstance().getDataManager().getClymeHomesTable().setHome(player, player.getLocation(), homeName);
        ClymeSkyblockCore.getInstance().getCommandUtil().getHomeCreation().remove(clymePlayer);
        ClymeSkyblockCore.getInstance().getPlayersHandler().updatePlayerData(clymePlayer);
        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully saved a new home called " + ClymeChatColor.SECONDARY() + homeName);
    }

    private void refresh(Player player) {
        player.closeInventory();
        open(player);
    }
}