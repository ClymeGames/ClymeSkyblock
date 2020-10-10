package solutions.misi.clymeskyblockcore.gui.staffpanel;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class StaffpanelDurationGUI implements Listener {

    public void open(Player player, OfflinePlayer target, String title) {
        Inventory gui = Bukkit.createInventory(null, 27, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + title);

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack thirtyMinutes = new ItemStack(Material.CLOCK);
        ItemMeta thirtyMinutesMeta = thirtyMinutes.getItemMeta();
        thirtyMinutesMeta.setDisplayName("§a30 minutes");
        List<String> thirtyMinutesLore = new ArrayList<>();
        thirtyMinutesLore.add(" ");
        thirtyMinutesLore.add("§7Left-Click here to punish");
        thirtyMinutesLore.add("§7" + target.getName() + " for 30 minutes.");
        thirtyMinutesLore.add(" ");
        thirtyMinutesMeta.setLore(thirtyMinutesLore);
        thirtyMinutes.setItemMeta(thirtyMinutesMeta);

        ItemStack twoHours = new ItemStack(Material.CLOCK);
        ItemMeta twoHoursMeta = twoHours.getItemMeta();
        twoHoursMeta.setDisplayName("§a2 hours");
        List<String> twoHoursLore = new ArrayList<>();
        twoHoursLore.add(" ");
        twoHoursLore.add("§7Left-Click here to punish");
        twoHoursLore.add("§7" + target.getName() + " for 2 hours.");
        twoHoursLore.add(" ");
        twoHoursMeta.setLore(twoHoursLore);
        twoHours.setItemMeta(twoHoursMeta);

        ItemStack twelveHours = new ItemStack(Material.CLOCK);
        ItemMeta twelveHoursMeta = twelveHours.getItemMeta();
        twelveHoursMeta.setDisplayName("§a12 hours");
        List<String> twelveHoursLore = new ArrayList<>();
        twelveHoursLore.add(" ");
        twelveHoursLore.add("§7Left-Click here to punish");
        twelveHoursLore.add("§7" + target.getName() + " for 12 hours.");
        twelveHoursLore.add(" ");
        twelveHoursMeta.setLore(twelveHoursLore);
        twelveHours.setItemMeta(twelveHoursMeta);

        ItemStack oneDay = new ItemStack(Material.CLOCK);
        ItemMeta oneDayMeta = oneDay.getItemMeta();
        oneDayMeta.setDisplayName("§a1 day");
        List<String> oneDayLore = new ArrayList<>();
        oneDayLore.add(" ");
        oneDayLore.add("§7Left-Click here to punish");
        oneDayLore.add("§7" + target.getName() + " for 1 day.");
        oneDayLore.add(" ");
        oneDayMeta.setLore(oneDayLore);
        oneDay.setItemMeta(oneDayMeta);

        ItemStack sevenDays = new ItemStack(Material.CLOCK);
        ItemMeta sevenDaysMeta = sevenDays.getItemMeta();
        sevenDaysMeta.setDisplayName("§a7 days");
        List<String> sevenDaysLore = new ArrayList<>();
        sevenDaysLore.add(" ");
        sevenDaysLore.add("§7Left-Click here to punish");
        sevenDaysLore.add("§7" + target.getName() + " for 7 days.");
        sevenDaysLore.add(" ");
        sevenDaysMeta.setLore(sevenDaysLore);
        sevenDays.setItemMeta(sevenDaysMeta);

        ItemStack thirtyDays = new ItemStack(Material.CLOCK);
        ItemMeta thirtyDaysMeta = thirtyDays.getItemMeta();
        thirtyDaysMeta.setDisplayName("§a30 days");
        List<String> thirtyDaysLore = new ArrayList<>();
        thirtyDaysLore.add(" ");
        thirtyDaysLore.add("§7Left-Click here to punish");
        thirtyDaysLore.add("§7" + target.getName() + " for 30 days.");
        thirtyDaysLore.add(" ");
        thirtyDaysMeta.setLore(thirtyDaysLore);
        thirtyDays.setItemMeta(thirtyDaysMeta);

        ItemStack ninetyDays = new ItemStack(Material.CLOCK);
        ItemMeta ninetyDaysMeta = ninetyDays.getItemMeta();
        ninetyDaysMeta.setDisplayName("§a90 days");
        List<String> ninetyDaysLore = new ArrayList<>();
        ninetyDaysLore.add(" ");
        ninetyDaysLore.add("§7Left-Click here to punish");
        ninetyDaysLore.add("§7" + target.getName() + " for 90 days.");
        ninetyDaysLore.add(" ");
        ninetyDaysMeta.setLore(ninetyDaysLore);
        ninetyDays.setItemMeta(ninetyDaysMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        gui.setItem(10, thirtyMinutes);
        gui.setItem(11, twoHours);
        gui.setItem(12, twelveHours);
        gui.setItem(13, oneDay);
        gui.setItem(14, sevenDays);
        gui.setItem(15, thirtyDays);
        gui.setItem(16, ninetyDays);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Tempban")) {
            event.setCancelled(true);

            try {
                if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a") && event.getCurrentItem().getType() == Material.CLOCK) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().get(player).split("/")[0]));
                    String tempbanData = target.getUniqueId().toString() + "/" + ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().put(player, tempbanData);

                    player.closeInventory();
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Please enter the reason for banning " + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.INFO() + " temporarily!");
                }
            } catch(NullPointerException exception) { }
        } else if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Tempmute")) {
            event.setCancelled(true);

            try {
                if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a") && event.getCurrentItem().getType() == Material.CLOCK) {
                    OfflinePlayer target = ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempMuting().get(player);

                    Calendar calendar = Calendar.getInstance();

                    switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                        case "§a30 minutes":
                            calendar.add(Calendar.MINUTE, 30);
                            break;
                        case "§a2 hours":
                            calendar.add(Calendar.HOUR, 2);
                            break;
                        case "§a12 hours":
                            calendar.add(Calendar.HOUR, 12);
                            break;
                        case "§a1 day":
                            calendar.add(Calendar.DAY_OF_WEEK, 1);
                            break;
                        case "§a7 days":
                            calendar.add(Calendar.DAY_OF_WEEK, 7);
                            break;
                        case "§a30 days":
                            calendar.add(Calendar.MONTH, 1);
                            break;
                        case "§a90 days":
                            calendar.add(Calendar.MONTH, 3);
                            break;
                    }

                    Timestamp muted = new Timestamp(calendar.getTime().getTime());
                    ClymeSkyblockCore.getInstance().getPlayersHandler().mutePlayer(target, muted);
                    ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempMuting().remove(player);
                    player.closeInventory();
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been muted!");
                }
            } catch(NullPointerException exception) { }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if(!ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().containsKey(player)) return;
        String[] tempbanData = ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().get(player).split("/");
        String tempbanDuration = tempbanData[1];
        if(tempbanDuration.equals("NULL")) return;

        event.setCancelled(true);

        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(tempbanData[0]));
        String duration = ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().get(player);

        Calendar calendar = Calendar.getInstance();

        switch(duration) {
            case "30 minutes":
                calendar.add(Calendar.MINUTE, 30);
                break;
            case "2 hours":
                calendar.add(Calendar.HOUR, 2);
                break;
            case "12 hours":
                calendar.add(Calendar.HOUR, 12);
                break;
            case "1 day":
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                break;
            case "7 days":
                calendar.add(Calendar.DAY_OF_WEEK, 7);
                break;
            case "30 days":
                calendar.add(Calendar.MONTH, 1);
                break;
            case "90 days":
                calendar.add(Calendar.MONTH, 3);
                break;
        }

        Timestamp banned = new Timestamp(calendar.getTime().getTime());
        ClymeSkyblockCore.getInstance().getPlayersHandler().banPlayer(target, banned, event.getMessage());
        ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().remove(player);
        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been banned from the server!");
    }
}
