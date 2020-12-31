package solutions.misi.clymeskyblockcore.gui.menu;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MenuGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Menu");
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        int ping = 0;
        long hours;
        long minutes;
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException exception) { exception.printStackTrace(); }
        hours = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 / 60;
        minutes = (player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20) / 60 % 60;
        ItemStack playerInfo = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerInfoMeta = (SkullMeta) playerInfo.getItemMeta();
        playerInfoMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
        playerInfoMeta.setDisplayName(ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeChatColor.ACCENT() + "§l" + player.getName()));
        List<String> playerInfoLore = new ArrayList<>();
        playerInfoLore.add(" ");
        playerInfoLore.add("§f➢ §7Rank: " + ClymeSkyblockCore.getInstance().getClymeMessage().format(ClymeSkyblockCore.getInstance().getChat().getPlayerPrefix(player)));
        playerInfoLore.add("§f➢ §7Balance: $" + ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player));
        playerInfoLore.add("§f➢ §7Level: " + player.getLevel());
        playerInfoLore.add("§f➢ §7Ping: " + ping);
        playerInfoLore.add("§f➢ §7Playtime: " + hours + " hours and " + minutes + " minutes");
        if(superiorPlayer.getIsland() != null)
            playerInfoLore.add("§f➢ §7Island Level: " + superiorPlayer.getIsland().getIslandLevel());
        playerInfoLore.add(" ");
        playerInfoMeta.setLore(playerInfoLore);
        playerInfo.setItemMeta(playerInfoMeta);

        ItemStack tutorial = new ItemStack(Material.BOOK);
        ItemMeta tutorialMeta = tutorial.getItemMeta();
        tutorialMeta.setDisplayName("§6Tutorial");
        List<String> tutorialLore = new ArrayList<>();
        tutorialLore.add(" ");
        tutorialLore.add("§7Learn the basics of our Skyblock Server");
        tutorialLore.add("§7and get started with your own Island!");
        tutorialLore.add(" ");
        tutorialMeta.setLore(tutorialLore);
        tutorial.setItemMeta(tutorialMeta);

        ItemStack crates = new ItemStack(Material.CHEST);
        ItemMeta cratesMeta = crates.getItemMeta();
        cratesMeta.setDisplayName("§6Crates");
        List<String> cratesLore = new ArrayList<>();
        cratesLore.add(" ");
        cratesLore.add("§7Teleport to the place where");
        cratesLore.add("§7the Crates can be opened");
        cratesLore.add(" ");
        cratesMeta.setLore(cratesLore);
        crates.setItemMeta(cratesMeta);

        ItemStack pvp = new ItemStack(Material.IRON_SWORD);
        ItemMeta pvpMeta = pvp.getItemMeta();
        pvpMeta.setDisplayName("§6PvP Arena");
        List<String> pvpLore = new ArrayList<>();
        pvpLore.add(" ");
        pvpLore.add("§7Teleport to the dangerous");
        pvpLore.add("§7PvP Arena where you can fight");
        pvpLore.add("§7against other players!");
        pvpLore.add(" ");
        pvpMeta.setLore(pvpLore);
        pvp.setItemMeta(pvpMeta);

        ItemStack shop = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta shopMeta = shop.getItemMeta();
        shopMeta.setDisplayName("§6Shop");
        List<String> shopLore = new ArrayList<>();
        shopLore.add(" ");
        shopLore.add("§7Opens our our Shop");
        shopLore.add("§7where you can buy");
        shopLore.add("§7Crates, Ranks and more!");
        shopLore.add(" ");
        shopMeta.setLore(shopLore);
        shop.setItemMeta(shopMeta);

        ItemStack auctionhouse = new ItemStack(Material.WOODEN_AXE);
        ItemMeta auctionhouseMeta = auctionhouse.getItemMeta();
        auctionhouseMeta.setDisplayName("§6Auctionhouse");
        List<String> auctionhouseLore = new ArrayList<>();
        auctionhouseLore.add(" ");
        auctionhouseLore.add("§7Opens the auctionhouse");
        auctionhouseLore.add("§7where you can buy and sell");
        auctionhouseLore.add("§7items from other players!");
        auctionhouseLore.add(" ");
        auctionhouseMeta.setLore(auctionhouseLore);
        auctionhouse.setItemMeta(auctionhouseMeta);

        ItemStack vote = new ItemStack(Material.PAPER);
        ItemMeta voteMeta = vote.getItemMeta();
        voteMeta.setDisplayName("§6Vote");
        List<String> voteLore = new ArrayList<>();
        voteLore.add(" ");
        voteLore.add("§7Shows you the links to");
        voteLore.add("§7vote for us and gives you");
        voteLore.add("§7some special rewards!");
        voteLore.add(" ");
        voteMeta.setLore(voteLore);
        vote.setItemMeta(voteMeta);

        ItemStack cosmetics = new ItemStack(Material.FIREWORK_ROCKET);
        ItemMeta cosmeticsMeta = cosmetics.getItemMeta();
        cosmeticsMeta.setDisplayName("§6Cosmetics");
        List<String> cosmeticsLore = new ArrayList<>();
        cosmeticsLore.add(" ");
        cosmeticsLore.add("§7Activate your cosmetic items");
        cosmeticsLore.add("§7such as Particles, Pets or");
        cosmeticsLore.add("§7disguises!");
        cosmeticsLore.add(" ");
        cosmeticsMeta.setLore(cosmeticsLore);
        cosmetics.setItemMeta(cosmeticsMeta);

        ItemStack topIslands = new ItemStack(Material.OAK_SAPLING);
        ItemMeta topIslandsMeta = topIslands.getItemMeta();
        topIslandsMeta.setDisplayName("§6Top Islands");
        List<String> topIslandsLore = new ArrayList<>();
        topIslandsLore.add(" ");
        topIslandsLore.add("§7Look at the current");
        topIslandsLore.add("§7best Islands on this");
        topIslandsLore.add("§7server!");
        topIslandsLore.add(" ");
        topIslandsMeta.setLore(topIslandsLore);
        topIslands.setItemMeta(topIslandsMeta);

        ItemStack richestPlayers = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta richestPlayersMeta = richestPlayers.getItemMeta();
        richestPlayersMeta.setDisplayName("§6Top Players");
        List<String> richestPlayersLore = new ArrayList<>();
        richestPlayersLore.add(" ");
        richestPlayersLore.add("§7Look at the current");
        richestPlayersLore.add("§7richest Players on this");
        richestPlayersLore.add("§7server!");
        richestPlayersLore.add(" ");
        richestPlayersMeta.setLore(richestPlayersLore);
        richestPlayers.setItemMeta(richestPlayersMeta);

        ItemStack dailyRewards = new ItemStack(Material.BELL);
        ItemMeta dailyRewardsMeta = dailyRewards.getItemMeta();
        dailyRewardsMeta.setDisplayName("§6Daily Rewards");
        List<String> dailyRewardsLore = new ArrayList<>();
        dailyRewardsLore.add(" ");
        dailyRewardsLore.add("§7Earn rewards for playing");
        dailyRewardsLore.add("§7daily on ClymeGames. The");
        dailyRewardsLore.add("§7higher the streak, the higher");
        dailyRewardsLore.add("§7the rewards!");
        dailyRewardsLore.add(" ");
        dailyRewardsMeta.setLore(dailyRewardsLore);
        dailyRewards.setItemMeta(dailyRewardsMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        gui.setItem(10, playerInfo);
        gui.setItem(12, tutorial);
        gui.setItem(13, shop);
        gui.setItem(14, crates);
        gui.setItem(15, auctionhouse);
        gui.setItem(16, cosmetics);
        gui.setItem(21, vote);
        gui.setItem(22, topIslands);
        gui.setItem(23, richestPlayers);
        gui.setItem(24, pvp);
        gui.setItem(25, dailyRewards);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        try {
            if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Menu")) {
                ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
                event.setCancelled(true);

                if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.closeInventory();
                    player.closeInventory();

                    switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                        case "§6Tutorial":
                            player.performCommand("tutorial tutorial");
                            break;
                        case "§6Top Islands":
                            player.performCommand("is top");
                            break;
                        case "§6Top Players":
                            player.performCommand("baltop");
                            break;
                        case "§6Crates":
                            player.performCommand("crates");
                            break;
                        case "§6PvP Arena":
                            player.performCommand("pvp");
                            break;
                        case "§6Shop":
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Visit " + ClymeChatColor.SECONDARY() + "https://store.clyme.games/ " + ClymeChatColor.INFO() + "to support this Server and purchase Ranks, Crates, Perks & More!");
                            break;
                        case "§6Auctionhouse":
                            player.performCommand("auctionhouse");
                            break;
                        case "§6Cosmetics":
                            player.performCommand("cosmetics");
                            break;
                        case "§6Vote":
                            player.performCommand("vote");
                            break;
                        case "§6Daily Rewards":
                            player.performCommand("dailybonus");
                            break;
                        default:
                            break;
                    }
            }
        } catch(NullPointerException ex) {}
    }
}