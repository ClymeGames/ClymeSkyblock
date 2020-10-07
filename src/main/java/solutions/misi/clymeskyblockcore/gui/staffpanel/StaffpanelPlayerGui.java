package solutions.misi.clymeskyblockcore.gui.staffpanel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffpanelPlayerGui implements Listener {

    public void open(Player player, UUID targetUuid) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetUuid);
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Staffpanel - " + target.getName());

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
        kickPlayerLore.add("§7Right-Click to kick");
        kickPlayerLore.add("§7" + target.getName() + ".");
        kickPlayerLore.add(" ");
        kickPlayerMeta.setLore(kickPlayerLore);
        kick.setItemMeta(kickPlayerMeta);

        ItemStack tempMute = new ItemStack(Material.SCUTE);
        ItemMeta tempMuteMeta = tempMute.getItemMeta();
        tempMuteMeta.setDisplayName("§aTemp-Mute Player");
        List<String> tempMuteLore = new ArrayList<>();
        tempMuteLore.add(" ");
        tempMuteLore.add("§7Right-Click to temporarily mute");
        tempMuteLore.add("§7" + target.getName() + ".");
        tempMuteLore.add(" ");
        tempMuteMeta.setLore(tempMuteLore);
        tempMute.setItemMeta(tempMuteMeta);

        ItemStack unMute = new ItemStack(Material.CLAY_BALL);
        ItemMeta unMuteMeta = unMute.getItemMeta();
        unMuteMeta.setDisplayName("§aUn-Mute Player");
        List<String> unMuteLore = new ArrayList<>();
        unMuteLore.add(" ");
        unMuteLore.add("§7Right-Click to delete the mute of");
        unMuteLore.add("§7" + target.getName() + ".");
        unMuteLore.add(" ");
        unMuteMeta.setLore(unMuteLore);
        unMute.setItemMeta(unMuteMeta);

        ItemStack clearChat = new ItemStack(Material.PAPER);
        ItemMeta clearChatMeta = clearChat.getItemMeta();
        clearChatMeta.setDisplayName("§aClear chat for Player");
        List<String> clearChatLore = new ArrayList<>();
        clearChatLore.add(" ");
        clearChatLore.add("§7Right-Click to clear the chat for");
        clearChatLore.add("§7" + target.getName() + ".");
        clearChatLore.add(" ");
        clearChatMeta.setLore(clearChatLore);
        clearChat.setItemMeta(clearChatMeta);

        ItemStack sendWarning = new ItemStack(Material.BELL);
        ItemMeta sendWarningMeta = sendWarning.getItemMeta();
        sendWarningMeta.setDisplayName("§aSend warning to Player");
        List<String> sendWarningLore = new ArrayList<>();
        sendWarningLore.add(" ");
        sendWarningLore.add("§7Right-Click to send a warning to");
        sendWarningLore.add("§7" + target.getName() + ".");
        sendWarningLore.add(" ");
        sendWarningMeta.setLore(sendWarningLore);
        sendWarning.setItemMeta(sendWarningMeta);

        ItemStack tempBan = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta tempBanMeta = tempBan.getItemMeta();
        tempBanMeta.setDisplayName("§bTemp-Ban a Player");
        List<String> tempBanLore = new ArrayList<>();
        tempBanLore.add(" ");
        tempBanLore.add("§7Right-Click to temporarily ban");
        tempBanLore.add("§7" + target.getName() + ".");
        tempBanLore.add(" ");
        tempBanMeta.setLore(tempBanLore);
        tempBan.setItemMeta(tempBanMeta);

        ItemStack screenShare = new ItemStack(Material.PAINTING);
        ItemMeta screenShareMeta = screenShare.getItemMeta();
        screenShareMeta.setDisplayName("§bScreenshare");
        List<String> screenShareLore = new ArrayList<>();
        screenShareLore.add(" ");
        screenShareLore.add("§7Right-Click to start screensharing with");
        screenShareLore.add("§7" + target.getName() + ".");
        screenShareLore.add(" ");
        screenShareMeta.setLore(screenShareLore);
        screenShare.setItemMeta(screenShareMeta);

        ItemStack spectate = new ItemStack(Material.ENDER_EYE);
        ItemMeta spectatePlayerMeta = spectate.getItemMeta();
        spectatePlayerMeta.setDisplayName("§bSpectate Player");
        List<String> spectatePlayerLore = new ArrayList<>();
        spectatePlayerLore.add(" ");
        spectatePlayerLore.add("§7Right-Click to start spectating");
        spectatePlayerLore.add("§7" + target.getName() + ".");
        spectatePlayerLore.add(" ");
        spectatePlayerMeta.setLore(spectatePlayerLore);
        spectate.setItemMeta(spectatePlayerMeta);

        ItemStack mute = new ItemStack(Material.SLIME_BALL);
        ItemMeta mutePlayerMeta = mute.getItemMeta();
        mutePlayerMeta.setDisplayName("§bMute Player");
        List<String> mutePlayerLore = new ArrayList<>();
        mutePlayerLore.add(" ");
        mutePlayerLore.add("§7Right-Click to permanently mute");
        mutePlayerLore.add("§7" + target.getName() + ".");
        mutePlayerLore.add(" ");
        mutePlayerMeta.setLore(mutePlayerLore);
        mute.setItemMeta(mutePlayerMeta);

        ItemStack unBan = new ItemStack(Material.BLAZE_ROD);
        ItemMeta unBanMeta = unBan.getItemMeta();
        unBanMeta.setDisplayName("§cUn-Ban Player");
        List<String> unBanLore = new ArrayList<>();
        unBanLore.add(" ");
        unBanLore.add("§7Right-Click to delete the ban of");
        unBanLore.add("§7" + target.getName() + ".");
        unBanLore.add(" ");
        unBanMeta.setLore(unBanLore);
        unBan.setItemMeta(unBanMeta);

        ItemStack ban = new ItemStack(Material.TRIDENT);
        ItemMeta banMeta = ban.getItemMeta();
        banMeta.setDisplayName("§cBan Player");
        List<String> banLore = new ArrayList<>();
        banLore.add(" ");
        banLore.add("§7Right-Click to permanently ban");
        banLore.add("§7" + target.getName() + ".");
        banLore.add(" ");
        banMeta.setLore(banLore);
        ban.setItemMeta(banMeta);

        ItemStack freeze = new ItemStack(Material.SNOWBALL);
        ItemMeta freezeMeta = freeze.getItemMeta();
        freezeMeta.setDisplayName("§cFreeze Player");
        List<String> freezeLore = new ArrayList<>();
        freezeLore.add(" ");
        freezeLore.add("§7Right-Click to freeze");
        freezeLore.add("§7" + target.getName() + ".");
        freezeLore.add(" ");
        freezeMeta.setLore(freezeLore);
        freeze.setItemMeta(freezeMeta);

        ItemStack inspectPlayerInventory = new ItemStack(Material.CHEST);
        ItemMeta inspectPlayerInventoryMeta = inspectPlayerInventory.getItemMeta();
        inspectPlayerInventoryMeta.setDisplayName("§cInspect Player Inventory");
        List<String> inspectPlayerInventoryLore = new ArrayList<>();
        inspectPlayerInventoryLore.add(" ");
        inspectPlayerInventoryLore.add("§7Right-Click to inspect the Inventory of");
        inspectPlayerInventoryLore.add("§7" + target.getName() + ".");
        inspectPlayerInventoryLore.add(" ");
        inspectPlayerInventoryMeta.setLore(inspectPlayerInventoryLore);
        inspectPlayerInventory.setItemMeta(inspectPlayerInventoryMeta);

        ItemStack gotoPlayerIsland = new ItemStack(Material.CAMPFIRE);
        ItemMeta gotoPlayerIslandMeta = gotoPlayerIsland.getItemMeta();
        gotoPlayerIslandMeta.setDisplayName("§cGo to Player Island");
        List<String> gotoPlayerIslandLore = new ArrayList<>();
        gotoPlayerIslandLore.add(" ");
        gotoPlayerIslandLore.add("§7Right-Click to teleport to");
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
        if(!event.getView().getTitle().startsWith(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Staffpanel -")) return;

        event.setCancelled(true);
        //> TODO: add features to player management gui

    }
}
