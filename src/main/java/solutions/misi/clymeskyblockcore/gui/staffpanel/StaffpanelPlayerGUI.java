package solutions.misi.clymeskyblockcore.gui.staffpanel;

import lombok.Getter;
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
import java.util.*;

public class StaffpanelPlayerGUI implements Listener {

    @Getter private final Map<Player, Player> playerKicking = new HashMap<>();
    @Getter private final Map<Player, OfflinePlayer> playerBanning = new HashMap<>();
    @Getter private final Map<Player, String> playerTempBanning = new HashMap<>();
    @Getter private final Map<Player, OfflinePlayer> playerTempMuting = new HashMap<>();
    @Getter private final List<Player> frozen = new ArrayList<>();

    public void open(Player player, UUID targetUuid) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetUuid);
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0SP - " + target.getName());

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack locked = new ItemStack(Material.BARRIER);
        ItemMeta lockedMeta = locked.getItemMeta();
        lockedMeta.setDisplayName("§cThis feature is locked");
        locked.setItemMeta(lockedMeta);

        ItemStack kick = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta kickPlayerMeta = kick.getItemMeta();
        kickPlayerMeta.setDisplayName("§aKick Player");
        List<String> kickPlayerLore = new ArrayList<>();
        kickPlayerLore.add(" ");
        kickPlayerLore.add("§7Left-Click to kick");
        kickPlayerLore.add("§7" + target.getName() + ".");
        kickPlayerLore.add(" ");
        kickPlayerMeta.setLore(kickPlayerLore);
        kick.setItemMeta(kickPlayerMeta);

        ItemStack tempMute = new ItemStack(Material.SCUTE);
        ItemMeta tempMuteMeta = tempMute.getItemMeta();
        tempMuteMeta.setDisplayName("§aTemp-Mute Player");
        List<String> tempMuteLore = new ArrayList<>();
        tempMuteLore.add(" ");
        tempMuteLore.add("§7Left-Click to temporarily mute");
        tempMuteLore.add("§7" + target.getName() + ".");
        tempMuteLore.add(" ");
        tempMuteMeta.setLore(tempMuteLore);
        tempMute.setItemMeta(tempMuteMeta);

        ItemStack unMute = new ItemStack(Material.CLAY_BALL);
        ItemMeta unMuteMeta = unMute.getItemMeta();
        unMuteMeta.setDisplayName("§aUn-Mute Player");
        List<String> unMuteLore = new ArrayList<>();
        unMuteLore.add(" ");
        unMuteLore.add("§7Left-Click to delete the mute of");
        unMuteLore.add("§7" + target.getName() + ".");
        unMuteLore.add(" ");
        unMuteMeta.setLore(unMuteLore);
        unMute.setItemMeta(unMuteMeta);

        ItemStack clearChat = new ItemStack(Material.PAPER);
        ItemMeta clearChatMeta = clearChat.getItemMeta();
        clearChatMeta.setDisplayName("§aClear chat for Player");
        List<String> clearChatLore = new ArrayList<>();
        clearChatLore.add(" ");
        clearChatLore.add("§7Left-Click to clear the chat for");
        clearChatLore.add("§7" + target.getName() + ".");
        clearChatLore.add(" ");
        clearChatMeta.setLore(clearChatLore);
        clearChat.setItemMeta(clearChatMeta);

        ItemStack sendWarning = new ItemStack(Material.BELL);
        ItemMeta sendWarningMeta = sendWarning.getItemMeta();
        sendWarningMeta.setDisplayName("§aSend warning to Player");
        List<String> sendWarningLore = new ArrayList<>();
        sendWarningLore.add(" ");
        sendWarningLore.add("§7Left-Click to send a warning to");
        sendWarningLore.add("§7" + target.getName() + ".");
        sendWarningLore.add(" ");
        sendWarningMeta.setLore(sendWarningLore);
        sendWarning.setItemMeta(sendWarningMeta);

        ItemStack tempBan = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta tempBanMeta = tempBan.getItemMeta();
        tempBanMeta.setDisplayName("§bTemp-Ban a Player");
        List<String> tempBanLore = new ArrayList<>();
        tempBanLore.add(" ");
        tempBanLore.add("§7Left-Click to temporarily ban");
        tempBanLore.add("§7" + target.getName() + ".");
        tempBanLore.add(" ");
        tempBanMeta.setLore(tempBanLore);
        tempBan.setItemMeta(tempBanMeta);

        ItemStack screenShare = new ItemStack(Material.PAINTING);
        ItemMeta screenShareMeta = screenShare.getItemMeta();
        screenShareMeta.setDisplayName("§bScreenshare");
        List<String> screenShareLore = new ArrayList<>();
        screenShareLore.add(" ");
        screenShareLore.add("§7Left-Click to start screensharing with");
        screenShareLore.add("§7" + target.getName() + ".");
        screenShareLore.add(" ");
        screenShareMeta.setLore(screenShareLore);
        screenShare.setItemMeta(screenShareMeta);

        ItemStack spectate = new ItemStack(Material.ENDER_EYE);
        ItemMeta spectatePlayerMeta = spectate.getItemMeta();
        spectatePlayerMeta.setDisplayName("§bSpectate Player");
        List<String> spectatePlayerLore = new ArrayList<>();
        spectatePlayerLore.add(" ");
        spectatePlayerLore.add("§7Left-Click to start spectating");
        spectatePlayerLore.add("§7" + target.getName() + ".");
        spectatePlayerLore.add(" ");
        spectatePlayerMeta.setLore(spectatePlayerLore);
        spectate.setItemMeta(spectatePlayerMeta);

        ItemStack mute = new ItemStack(Material.SLIME_BALL);
        ItemMeta mutePlayerMeta = mute.getItemMeta();
        mutePlayerMeta.setDisplayName("§bMute Player");
        List<String> mutePlayerLore = new ArrayList<>();
        mutePlayerLore.add(" ");
        mutePlayerLore.add("§7Left-Click to permanently mute");
        mutePlayerLore.add("§7" + target.getName() + ".");
        mutePlayerLore.add(" ");
        mutePlayerMeta.setLore(mutePlayerLore);
        mute.setItemMeta(mutePlayerMeta);

        ItemStack unBan = new ItemStack(Material.BLAZE_ROD);
        ItemMeta unBanMeta = unBan.getItemMeta();
        unBanMeta.setDisplayName("§cUn-Ban Player");
        List<String> unBanLore = new ArrayList<>();
        unBanLore.add(" ");
        unBanLore.add("§7Left-Click to delete the ban of");
        unBanLore.add("§7" + target.getName() + ".");
        unBanLore.add(" ");
        unBanMeta.setLore(unBanLore);
        unBan.setItemMeta(unBanMeta);

        ItemStack ban = new ItemStack(Material.TRIDENT);
        ItemMeta banMeta = ban.getItemMeta();
        banMeta.setDisplayName("§cBan Player");
        List<String> banLore = new ArrayList<>();
        banLore.add(" ");
        banLore.add("§7Left-Click to permanently ban");
        banLore.add("§7" + target.getName() + ".");
        banLore.add(" ");
        banMeta.setLore(banLore);
        ban.setItemMeta(banMeta);

        ItemStack freeze = new ItemStack(Material.SNOWBALL);
        ItemMeta freezeMeta = freeze.getItemMeta();
        freezeMeta.setDisplayName("§cFreeze Player");
        List<String> freezeLore = new ArrayList<>();
        freezeLore.add(" ");
        freezeLore.add("§7Left-Click to freeze");
        freezeLore.add("§7" + target.getName() + ".");
        freezeLore.add(" ");
        freezeMeta.setLore(freezeLore);
        freeze.setItemMeta(freezeMeta);

        ItemStack inspectPlayerInventory = new ItemStack(Material.CHEST);
        ItemMeta inspectPlayerInventoryMeta = inspectPlayerInventory.getItemMeta();
        inspectPlayerInventoryMeta.setDisplayName("§cInspect Player Inventory");
        List<String> inspectPlayerInventoryLore = new ArrayList<>();
        inspectPlayerInventoryLore.add(" ");
        inspectPlayerInventoryLore.add("§7Left-Click to inspect the Inventory of");
        inspectPlayerInventoryLore.add("§7" + target.getName() + ".");
        inspectPlayerInventoryLore.add(" ");
        inspectPlayerInventoryMeta.setLore(inspectPlayerInventoryLore);
        inspectPlayerInventory.setItemMeta(inspectPlayerInventoryMeta);

        ItemStack gotoPlayerIsland = new ItemStack(Material.CAMPFIRE);
        ItemMeta gotoPlayerIslandMeta = gotoPlayerIsland.getItemMeta();
        gotoPlayerIslandMeta.setDisplayName("§cGo to Player Island");
        List<String> gotoPlayerIslandLore = new ArrayList<>();
        gotoPlayerIslandLore.add(" ");
        gotoPlayerIslandLore.add("§7Left-Click to teleport to");
        gotoPlayerIslandLore.add("§7" + target.getName() + "'s Island.");
        gotoPlayerIslandLore.add(" ");
        gotoPlayerIslandMeta.setLore(gotoPlayerIslandLore);
        gotoPlayerIsland.setItemMeta(gotoPlayerIslandMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        String playerRank = ClymeSkyblockCore.getInstance().getPermission().getPrimaryGroup(player);
        switch(playerRank) {
            case "helper":
                //> helper staffpanel
                gui.setItem(10, kick);
                gui.setItem(11, tempMute);
                gui.setItem(12, unMute);
                gui.setItem(13, clearChat);
                gui.setItem(14, sendWarning);
                gui.setItem(15, locked);
                gui.setItem(16, locked);
                gui.setItem(19, locked);
                gui.setItem(20, locked);
                gui.setItem(21, locked);
                gui.setItem(22, locked);
                gui.setItem(23, locked);
                gui.setItem(24, locked);
                gui.setItem(25, locked);
                break;
            case "mod":
            case "seniormod":
                //> mod staffpanel
                gui.setItem(10, kick);
                gui.setItem(11, tempMute);
                gui.setItem(12, unMute);
                gui.setItem(13, clearChat);
                gui.setItem(14, sendWarning);
                gui.setItem(15, tempBan);
                gui.setItem(16, screenShare);
                gui.setItem(19, spectate);
                gui.setItem(20, mute);
                gui.setItem(21, locked);
                gui.setItem(22, locked);
                gui.setItem(23, locked);
                gui.setItem(24, locked);
                gui.setItem(25, locked);
                break;
            case "admin":
            case "headmod":
            case "headadmin":
            case "owner":
                //> admin staffpanel
                gui.setItem(10, kick);
                gui.setItem(11, tempMute);
                gui.setItem(12, unMute);
                gui.setItem(13, clearChat);
                gui.setItem(14, sendWarning);
                gui.setItem(15, tempBan);
                gui.setItem(16, screenShare);
                gui.setItem(19, spectate);
                gui.setItem(20, mute);
                gui.setItem(21, unBan);
                gui.setItem(22, ban);
                gui.setItem(23, freeze);
                gui.setItem(24, inspectPlayerInventory);
                gui.setItem(25, gotoPlayerIsland);
                break;
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!event.getView().getTitle().startsWith(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0SP -")) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
        String targetName = ChatColor.stripColor(event.getInventory().getItem(10).getLore().get(2));
        targetName = targetName.substring(0, targetName.length() - 1);
        OfflinePlayer target = Bukkit.getOfflinePlayer(ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getUuidFromName(targetName));

        try {
            switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§aKick Player":
                    player.closeInventory();
                    if(target.isOnline()) {
                        ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerKicking().put(player, target.getPlayer());
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Please enter the reason for kicking " + ClymeChatColor.SECONDARY() + targetName + ClymeChatColor.INFO() + "!");
                    } else {
                        clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "The player " + ClymeChatColor.SECONDARY() + targetName + ClymeChatColor.ERROR() + " is not online!");
                    }

                    break;
                case "§cBan Player":
                    ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerBanning().put(player, target);
                    player.closeInventory();
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "Please enter the reason for banning " + ClymeChatColor.SECONDARY() + targetName + ClymeChatColor.INFO() + " permanently!");
                    break;
                case "§cUn-Ban Player":
                    ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().unbanPlayer(target);
                    player.closeInventory();
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully un-banned " + ClymeChatColor.SECONDARY() + targetName + ClymeChatColor.SUCCESS() + " from this server!");
                    break;
                case "§bTemp-Ban a Player":
                    ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempBanning().put(player, target.getUniqueId().toString() + "/NULL");
                    ClymeSkyblockCore.getInstance().getStaffpanelDurationGUI().open(player, target, "§0Tempban");
                    break;
                case "§bMute Player":
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, 99);
                    Timestamp muted = new Timestamp(calendar.getTime().getTime());
                    ClymeSkyblockCore.getInstance().getPlayersHandler().mutePlayer(target, muted);
                    player.closeInventory();
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been muted!");
                    break;
                case "§aTemp-Mute Player":
                    ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerTempMuting().put(player, target);
                    ClymeSkyblockCore.getInstance().getStaffpanelDurationGUI().open(player, target, "§0Tempmute");
                    break;
                case "§aUn-Mute Player":
                    ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().unmutePlayer(target);
                    player.closeInventory();
                    clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SUCCESS() + "Successfully un-muted " + ClymeChatColor.SECONDARY() + targetName + ClymeChatColor.SUCCESS() + "!");

                    if(target.isOnline()) {
                        ClymePlayer clymeTarget = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(target.getPlayer());
                        clymeTarget.setMuted(new Timestamp(new Date().getTime()-100));
                        clymeTarget.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.INFO() + "You got un-muted!");
                    }

                    break;
            }
        } catch(NullPointerException exception) { }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        //> Kick
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerKicking().containsKey(player)) {
            event.setCancelled(true);

            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
            Player target = ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerKicking().get(player);

            Bukkit.getScheduler().runTask(ClymeSkyblockCore.getInstance(), () -> {
                target.kickPlayer(" \n" +
                        ClymeSkyblockCore.getInstance().getClymeMessage().getRawPrefix() + "\n" +
                        " \n" +
                        " \n" +
                        "§cYou got kicked from the Server!\n" +
                        " \n" +
                        "§f§lReason: §7" + event.getMessage() + "\n" +
                        "\n" +
                        "");
            });

            ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerKicking().remove(player);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been kicked from the server!");
            return;
        }

        //> Ban
        if(ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerBanning().containsKey(player)) {
            event.setCancelled(true);

            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
            OfflinePlayer target = ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerBanning().get(player);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 99);
            Timestamp banned = new Timestamp(calendar.getTime().getTime());
            ClymeSkyblockCore.getInstance().getPlayersHandler().banPlayer(target, banned, event.getMessage());
            ClymeSkyblockCore.getInstance().getStaffpanelPlayerGUI().getPlayerBanning().remove(player);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.SECONDARY() + target.getName() + ClymeChatColor.SUCCESS() + " has been banned from the server!");
            return;
        }
    }
}
