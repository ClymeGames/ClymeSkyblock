package solutions.misi.clymeskyblockcore.gui.islandmenu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.ArrayList;
import java.util.List;

public class IslandCreationGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Island Creation");

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        List<String> defaultIslandLore = new ArrayList<>();
        defaultIslandLore.add(" ");
        defaultIslandLore.add("§7Left-Click to create your new island.");
        defaultIslandLore.add("§7Right-Click for more details.");
        defaultIslandLore.add(" ");

        ItemStack basicIsland = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta basicIslandMeta = basicIsland.getItemMeta();
        basicIslandMeta.setDisplayName("§aBasic Island");
        basicIslandMeta.setLore(defaultIslandLore);
        basicIsland.setItemMeta(basicIslandMeta);

        ItemStack jungleIsland = new ItemStack(Material.VINE);
        ItemMeta jungleIslandMeta = jungleIsland.getItemMeta();
        jungleIslandMeta.setDisplayName("§aJungle Island");
        jungleIslandMeta.setLore(defaultIslandLore);
        jungleIsland.setItemMeta(jungleIslandMeta);

        ItemStack snowyMountainsIsland = new ItemStack(Material.SNOWBALL);
        ItemMeta snowyMountainsIslandMeta = snowyMountainsIsland.getItemMeta();
        snowyMountainsIslandMeta.setDisplayName("§aSnowy Mountains Island");
        snowyMountainsIslandMeta.setLore(defaultIslandLore);
        snowyMountainsIsland.setItemMeta(snowyMountainsIslandMeta);

        ItemStack stoneGazeIsland = new ItemStack(Material.COBBLESTONE);
        ItemMeta stoneGazeIslandMeta = stoneGazeIsland.getItemMeta();
        stoneGazeIslandMeta.setDisplayName("§aStone Gaze Island");
        stoneGazeIslandMeta.setLore(defaultIslandLore);
        stoneGazeIsland.setItemMeta(stoneGazeIslandMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);
        gui.setItem(2, basicIsland);
        gui.setItem(4, jungleIsland);
        gui.setItem(5, snowyMountainsIsland);
        gui.setItem(6, stoneGazeIsland);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        try {
            if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Island Creation")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.closeInventory();

                if(event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.SHIFT_RIGHT) {
                    player.closeInventory();

                    switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                        case "§aBasic Island":
                            clymePlayer.sendMessage(" ");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Island Creation - Details " + ClymeChatColor.SECONDARY() + "(Basic)");
                            clymePlayer.sendMessage(ClymeChatColor.SECONDARY() + "§m==============================================");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Description: \n" +
                                    "" + ClymeChatColor.SECONDARY() + "The default SkyBlock Experience with everything you need to start.");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Avg. Temperature: " + ClymeChatColor.SECONDARY() + "80 °F // 26.6 °C");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Biomes: " + ClymeChatColor.SECONDARY() + "ALL");
                            //clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Boost: " + ClymeChatColor.SECONDARY() + "NONE");
                            break;
                        case "§aJungle Island":
                            clymePlayer.sendMessage(" ");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Island Creation - Details " + ClymeChatColor.SECONDARY() + "(Jungle)");
                            clymePlayer.sendMessage(ClymeChatColor.SECONDARY() + "§m==============================================");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Description: \n" +
                                    "" + ClymeChatColor.SECONDARY() + "Amazing Jungle experience in a hot biome and a lot of plants.");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Avg. Temperature: " + ClymeChatColor.SECONDARY() + "100 °F // 37.8 °C");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Biomes: " + ClymeChatColor.SECONDARY() + "JUNGLE");
                            //clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Boost: " + ClymeChatColor.SECONDARY() + "+6% Growth Rate for Cocoa");
                            break;
                        case "§aSnowy Mountains Island":
                            clymePlayer.sendMessage(" ");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Island Creation - Details " + ClymeChatColor.SECONDARY() + "(Snowy Mountains)");
                            clymePlayer.sendMessage(ClymeChatColor.SECONDARY() + "§m==============================================");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Description: \n" +
                                    "" + ClymeChatColor.SECONDARY() + "High mountain island in snowy area. Be aware of the coldness!");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Avg. Temperature: " + ClymeChatColor.SECONDARY() + "20 °F // -6.7 °C");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Biomes: " + ClymeChatColor.SECONDARY() + "TAIGA");
                            //clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Boost: " + ClymeChatColor.SECONDARY() + "+6% Growth Rate for Flowers");
                            break;
                        case "§aStone Gaze Island":
                            clymePlayer.sendMessage(" ");
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Island Creation - Details " + ClymeChatColor.SECONDARY() + "(Stone Gaze)");
                            clymePlayer.sendMessage(ClymeChatColor.SECONDARY() + "§m==============================================");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Description: \n" +
                                    "" + ClymeChatColor.SECONDARY() + "A mystical oak island surrounded by flying stones.");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Avg. Temperature: " + ClymeChatColor.SECONDARY() + "70 °F // 21.1 °C");
                            clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Biomes: " + ClymeChatColor.SECONDARY() + "ALL");
                            //clymePlayer.sendMessage(ClymeChatColor.ACCENT() + "Boost: " + ClymeChatColor.SECONDARY() + "+6% Growth Rate for Sugarcane");
                            break;
                    }
                } else {
                    switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                        case "§aBasic Island":
                            player.performCommand("is create basic");
                            break;
                        case "§aJungle Island":
                            player.performCommand("is create jungle");
                            break;
                        case "§aSnowy Mountains Island":
                            player.performCommand("is create snowymountains");
                            break;
                        case "§aStone Gaze Island":
                            player.performCommand("is create stonegaze");
                            break;
                    }
                }
            }
        } catch(NullPointerException ex) {}
    }
}