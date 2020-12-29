package solutions.misi.clymeskyblockcore.gui.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;
import solutions.misi.clymeskyblockcore.utils.SkullCreator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MinionShopGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Minions");

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack balance = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta balanceMeta = balance.getItemMeta();
        balanceMeta.setDisplayName("§f➢ §6Your balance");
        List<String> balanceLore = new ArrayList<>();
        balanceLore.add("§7$" + ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player).toString());
        balanceMeta.setLore(balanceLore);
        balance.setItemMeta(balanceMeta);

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("§f➢ §cGo Back");
        List<String> backLore = new ArrayList<>();
        backLore.add(" ");
        backLore.add("§7Left-Click to return");
        backLore.add("§7to the main menu");
        backLore.add(" ");
        backMeta.setLore(backLore);
        back.setItemMeta(backMeta);

        ItemStack oak = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIzZTBiYmYwN2FmZGE0OGYyMmM3OWJjZDYyNDk0MWY0YWI3ZmJlZjE5NWI0MjFkOWFjNDJhNWY4MjExYTk0MiJ9fX0=");
        ItemMeta oakMeta = oak.getItemMeta();
        oakMeta.setDisplayName("§aOak Minion I");
        List<String> oakLore = new ArrayList<>();
        oakLore.add("§7Price: $5,000");
        oakLore.add(" ");
        oakLore.add("§7Place this minion and it will");
        oakLore.add("§7start generating and mining");
        oakLore.add("§7oak logs! Requires an open area to");
        oakLore.add("§7place oak logs.");
        oakLore.add(" ");
        oakLore.add("§a§lCOMMON");
        oakMeta.setLore(oakLore);
        oak.setItemMeta(oakMeta);

        ItemStack cobblestone = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjljMzhmZTRmYzk4YTI0ODA3OWNkMDRjNjViNmJmZjliNDUwMTdmMTY0NjBkYWIzYzM0YzE3YmZjM2VlMWQyZiJ9fX0=");
        ItemMeta cobblestoneMeta = cobblestone.getItemMeta();
        cobblestoneMeta.setDisplayName("§aCobblestone Minion I");
        List<String> cobblestoneLore = new ArrayList<>();
        cobblestoneLore.add("§7Price: $7,500");
        cobblestoneLore.add(" ");
        cobblestoneLore.add("§7Place this minion and it will");
        cobblestoneLore.add("§7start generating and mining");
        cobblestoneLore.add("§7cobblestone! Requires an open area to");
        cobblestoneLore.add("§7place cobblestone.");
        cobblestoneLore.add(" ");
        cobblestoneLore.add("§a§lCOMMON");
        cobblestoneMeta.setLore(cobblestoneLore);
        cobblestone.setItemMeta(cobblestoneMeta);

        ItemStack coal = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU3MDVjZjg2NGRmMmMxODJlMzJjNDg2YjcxNDdjYmY3ODJhMGFhM2RmOGE2ZDYxNDUzOTM5MGJmODRmYjE1ZCJ9fX0=");
        ItemMeta coalMeta = coal.getItemMeta();
        coalMeta.setDisplayName("§aCoal Minion I");
        List<String> coalLore = new ArrayList<>();
        coalLore.add("§7Price: $10,000");
        coalLore.add(" ");
        coalLore.add("§7Place this minion and it will");
        coalLore.add("§7start generating and mining");
        coalLore.add("§7coal! Requires an open area to");
        coalLore.add("§7place coal.");
        coalLore.add(" ");
        coalLore.add("§a§lCOMMON");
        coalMeta.setLore(coalLore);
        coal.setItemMeta(coalMeta);

        ItemStack iron = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTczMDkyY2FlYTg5ZGQxMmQzNjgzNzVmMWEwOTc3OTZlM2JjODhmODA5YWNjZjMyMDcwNmJkYzA0OGNkNGM1ZSJ9fX0=");
        ItemMeta ironMeta = iron.getItemMeta();
        ironMeta.setDisplayName("§aIron Minion I");
        List<String> ironLore = new ArrayList<>();
        ironLore.add("§7Price: $15,000");
        ironLore.add(" ");
        ironLore.add("§7Place this minion and it will");
        ironLore.add("§7start generating and mining");
        ironLore.add("§7iron! Requires an open area to");
        ironLore.add("§7place iron.");
        ironLore.add(" ");
        ironLore.add("§a§lCOMMON");
        ironMeta.setLore(ironLore);
        iron.setItemMeta(ironMeta);

        ItemStack clay = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRhMjc5ZTFjYzdkOThmYWE0YjgwY2ZkNGVkMTg2YTFhOTRiNmMxMTkxMGM0ZjdiZDcyMWY4Y2Q0NGI0NTg4YSJ9fX0=");
        ItemMeta clayMeta = clay.getItemMeta();
        clayMeta.setDisplayName("§9Clay Minion I");
        List<String> clayLore = new ArrayList<>();
        clayLore.add("§7Price: $20,000");
        clayLore.add(" ");
        clayLore.add("§7Place this minion and it will");
        clayLore.add("§7start generating and mining");
        clayLore.add("§7clay! Requires an open area to");
        clayLore.add("§7place iron.");
        clayLore.add(" ");
        clayLore.add("§9§lRARE");
        clayMeta.setLore(clayLore);
        clay.setItemMeta(clayMeta);

        ItemStack quartz = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU3MmJlMWY3OTFkOTIzOTVjNGNmMDEzMzVkMDc4ZGU1YWY0M2Q5YzZmY2JjM2EzODc1M2RjNTY5NDJmNGE0NiJ9fX0=");
        ItemMeta quartzMeta = quartz.getItemMeta();
        quartzMeta.setDisplayName("§9Quartz Minion I");
        List<String> quartzLore = new ArrayList<>();
        quartzLore.add("§7Price: $25,000");
        quartzLore.add(" ");
        quartzLore.add("§7Place this minion and it will");
        quartzLore.add("§7start generating and mining");
        quartzLore.add("§7quartz! Requires an open area to");
        quartzLore.add("§7place quartz.");
        quartzLore.add(" ");
        quartzLore.add("§9§lRARE");
        quartzMeta.setLore(quartzLore);
        quartz.setItemMeta(quartzMeta);

        ItemStack potato = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjM0ZTZlZmVjZTBhMjk3MzE0YThlOTAzM2U1YmUxODcwNTU0YmNhOWM5NmExNDYyY2E2NmRiZDZlZDZlNjM4YSJ9fX0=");
        ItemMeta potatoMeta = potato.getItemMeta();
        potatoMeta.setDisplayName("§aPotato Minion I");
        List<String> potatoLore = new ArrayList<>();
        potatoLore.add("§7Price: $5,000");
        potatoLore.add(" ");
        potatoLore.add("§7Place this minion and it will");
        potatoLore.add("§7start generating and mining");
        potatoLore.add("§7potatoes! Requires an open area to");
        potatoLore.add("§7place potatoes.");
        potatoLore.add(" ");
        potatoLore.add("§a§lCOMMON");
        potatoMeta.setLore(potatoLore);
        potato.setItemMeta(potatoMeta);

        ItemStack rabbit = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZmODM3YTEzMDM3MjUzZmVhODljNjUzODJlMjQ4YTM2N2Y5OTM0M2M4YzZkZmQyZjEzYzYzNDRhYTM4MjVkNSJ9fX0=");
        ItemMeta rabbitMeta = rabbit.getItemMeta();
        rabbitMeta.setDisplayName("§aRabbit Minion I");
        List<String> rabbitLore = new ArrayList<>();
        rabbitLore.add("§7Price: $7,500");
        rabbitLore.add(" ");
        rabbitLore.add("§7Place this minion and it will");
        rabbitLore.add("§7start spawning");
        rabbitLore.add("§7rabbits! Requires an open area to");
        rabbitLore.add("§7spawn rabbits.");
        rabbitLore.add(" ");
        rabbitLore.add("§a§lCOMMON");
        rabbitMeta.setLore(rabbitLore);
        rabbit.setItemMeta(rabbitMeta);

        ItemStack sheep = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRlMmFjOWM0YmFhODI0YThiMTY2ZTI4N2ZkOWViZTdmMGRkMWJhZDQ0OWY5NTFhMTg5Y2M3NTM3ODJmNzhmMyJ9fX0=");
        ItemMeta sheepMeta = sheep.getItemMeta();
        sheepMeta.setDisplayName("§9Sheep Minion I");
        List<String> sheepLore = new ArrayList<>();
        sheepLore.add("§7Price: $10,000");
        sheepLore.add(" ");
        sheepLore.add("§7Place this minion and it will");
        sheepLore.add("§7start spawning");
        sheepLore.add("§7sheeps! Requires an open area to");
        sheepLore.add("§7spawn sheeps.");
        sheepLore.add(" ");
        sheepLore.add("§9§lRARE");
        sheepMeta.setLore(sheepLore);
        sheep.setItemMeta(sheepMeta);

        ItemStack slayer = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgyM2U1YTI4YzgzZGI1YjRkMjVkYmE3NjMxZjFiYTEzOTc1Y2I0ZTBiOGE1NzIwMDI1NDk1OTllZmExZGRkNSJ9fX0=");
        ItemMeta slayerMeta = slayer.getItemMeta();
        slayerMeta.setDisplayName("§9Slayer Minion I");
        List<String> slayerLore = new ArrayList<>();
        slayerLore.add("§7Price: $15,000");
        slayerLore.add(" ");
        slayerLore.add("§7Place this minion and it will");
        slayerLore.add("§7start generating and mining");
        slayerLore.add("§7red mushrooms! Requires an open area to");
        slayerLore.add("§7place red mushrooms.");
        slayerLore.add(" ");
        slayerLore.add("§9§lRARE");
        slayerMeta.setLore(slayerLore);
        slayer.setItemMeta(slayerMeta);

        ItemStack gold = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTkwODIzNWExY2JlY2MwM2E3MmNkZjcxMGY0ZWQ1MTlkNjViNGRhNjJiNTRhNGVmOThhNzQwOGZjZjUxYjgzYiJ9fX0=");
        ItemMeta goldMeta = gold.getItemMeta();
        goldMeta.setDisplayName("§5Gold Minion I");
        List<String> goldLore = new ArrayList<>();
        goldLore.add("§7Price: $25,000");
        goldLore.add(" ");
        goldLore.add("§7Place this minion and it will");
        goldLore.add("§7start generating and mining");
        goldLore.add("§7gold! Requires an open area to");
        goldLore.add("§7place gold.");
        goldLore.add(" ");
        goldLore.add("§5§lEPIC");
        goldMeta.setLore(goldLore);
        gold.setItemMeta(goldMeta);

        ItemStack netherwart = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI1OGUxYzhmN2U2ZWJiMzYyMmM2NmE1MjFiN2Y4MzhjY2VlOGFhOTI0MjU4YmIzMjYzMzU2Zjk5NzI1YWM1MyJ9fX0=");
        ItemMeta netherwartMeta = netherwart.getItemMeta();
        netherwartMeta.setDisplayName("§5Netherwart Minion I");
        List<String> netherwartLore = new ArrayList<>();
        netherwartLore.add("§7Price: $25,000");
        netherwartLore.add(" ");
        netherwartLore.add("§7Place this minion and it will");
        netherwartLore.add("§7start generating and mining");
        netherwartLore.add("§7netherwarts! Requires an open area to");
        netherwartLore.add("§7place netherwarts.");
        netherwartLore.add(" ");
        netherwartLore.add("§5§lEPIC");
        netherwartMeta.setLore(netherwartLore);
        netherwart.setItemMeta(netherwartMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        gui.setItem(8, balance);
        gui.setItem(35, back);

        gui.setItem(10, oak);
        gui.setItem(11, cobblestone);
        gui.setItem(12, coal);
        gui.setItem(13, iron);
        gui.setItem(14, clay);
        gui.setItem(15, quartz);
        gui.setItem(19, potato);
        gui.setItem(20, rabbit);
        gui.setItem(21, sheep);
        gui.setItem(22, slayer);
        gui.setItem(23, gold);
        gui.setItem(24, netherwart);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        try {
            if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Minions")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.closeInventory();
                    switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                        case "§aOak Minion I":
                            if(purchaseMinion(clymePlayer, 5000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 11");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the oak minion!");
                            }

                            break;
                        case "§aCobblestone Minion I":
                            if(purchaseMinion(clymePlayer, 7500)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 12");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the cobblestone minion!");
                            }

                            break;
                        case "§aCoal Minion I":
                            if(purchaseMinion(clymePlayer, 10000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 13");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the coal minion!");
                            }

                            break;
                        case "§aIron Minion I":
                            if(purchaseMinion(clymePlayer, 15000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 14");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the iron minion!");
                            }

                            break;
                        case "§9Clay Minion I":
                            if(purchaseMinion(clymePlayer, 20000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 15");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the clay minion!");
                            }

                            break;
                        case "§9Quartz Minion I":
                            if(purchaseMinion(clymePlayer, 25000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 16");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the quartz minion!");
                            }

                            break;
                        case "§aPotato Minion I":
                            if(purchaseMinion(clymePlayer, 5000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " Farmer 11");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the potato minion!");
                            }

                            break;
                        case "§aRabbit Minion I":
                            if(purchaseMinion(clymePlayer, 7500)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " Spawner 11");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the rabbit minion!");
                            }

                            break;
                        case "§9Sheep Minion I":
                            if(purchaseMinion(clymePlayer, 10000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 12");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the sheep minion!");
                            }

                            break;
                        case "§9Slayer Minion I":
                            if(purchaseMinion(clymePlayer, 15000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " Slayer 11");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the slayer minion!");
                            }

                            break;
                        case "§5Gold Minion I":
                            if(purchaseMinion(clymePlayer, 25000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " MinerSpawner 17");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the gold minion!");
                            }

                            break;
                        case "§5Netherwart Minion I":
                            if(purchaseMinion(clymePlayer, 25000)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jetsminions give " + player.getName() + " Farmer 12");
                                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "You have successfully purchased the netherwart minion!");
                            }

                            break;
                        case "§f➢ §cGo Back":
                            player.performCommand("shop");
                            break;
                        default:
                            break;
                }
            }
        } catch(NullPointerException ex) {}
    }

    public boolean purchaseMinion(ClymePlayer player, double price) {
        BigDecimal playerBalance = ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().getBalance(player.getPlayer());

        if(playerBalance.doubleValue() >= price) {
            BigDecimal updatedBalance = playerBalance.subtract(new BigDecimal(price));
            ClymeSkyblockCore.getInstance().getDataManager().getEconomyStorage().setBalance(player.getPlayer(), updatedBalance);
            return true;
        } else {
            player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You don't have enough money to buy this minion!");
            return false;
        }
    }
}
