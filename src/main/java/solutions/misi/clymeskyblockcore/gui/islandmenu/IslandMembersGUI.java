package solutions.misi.clymeskyblockcore.gui.islandmenu;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

import java.util.ArrayList;
import java.util.List;

public class IslandMembersGUI implements Listener {

    public void open(Player player) {
        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        Island island = superiorPlayer.getIsland();

        //> Only continue when player is owner of the Island
        if(island.getOwner() != superiorPlayer) {
            player.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return;
        }

        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Island Members");

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack noMembers = new ItemStack(Material.BARRIER);
        ItemMeta noMembersMeta = noMembers.getItemMeta();
        noMembersMeta.setDisplayName("§cYou have no Island Members!");
        noMembers.setItemMeta(noMembersMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        //> Create Skulls for each member
        for(SuperiorPlayer superiorMember : island.getIslandMembers(true)) {
            OfflinePlayer member = Bukkit.getOfflinePlayer(superiorMember.getUniqueId());
            String onlineStatus = "§c§lOFFLINE";
            String islandRank = "Member";

            if(member.isOnline()) onlineStatus = "§a§lONLINE";
            if(island.getOwner().getUniqueId().equals(superiorMember.getUniqueId())) islandRank = "Island Leader";

            ItemStack memberSkull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta memberSkullMeta = (SkullMeta) memberSkull.getItemMeta();
            memberSkullMeta.setDisplayName("§6" + member.getName());
            memberSkullMeta.setOwningPlayer(member);
            List<String> memberSkullLore = new ArrayList<>();
            memberSkullLore.add(" ");
            memberSkullLore.add("§7Currently " + onlineStatus + "§7!");
            memberSkullLore.add(" ");
            memberSkullLore.add("§f➢ §7Rank: " + islandRank);
            memberSkullLore.add("§f➢ §7Joined: " + ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getFirstJoin(member.getPlayer()));
            memberSkullLore.add("§f➢ §7Last online: " + ClymeSkyblockCore.getInstance().getDataManager().getClymePlayersTable().getLastJoin(member.getPlayer()));
            memberSkullLore.add(" ");
            memberSkullLore.add("§7Left-Click to kick member from Island");
            memberSkullLore.add("§7Right-Click to promote member to Island Leader");
            memberSkullLore.add(" ");
            memberSkullMeta.setLore(memberSkullLore);
            memberSkull.setItemMeta(memberSkullMeta);

            int memberIndex = findMemberIndex(gui);
            if(memberIndex != -1) gui.setItem(memberIndex, memberSkull);
        }

        if(!gui.contains(Material.PLAYER_HEAD)) {
            for(int i = 0; i < gui.getSize(); i++) {
                int memberIndex = findMemberIndex(gui);
                if(memberIndex == -1) break;
                gui.setItem(memberIndex, noMembers);
            }
        }

        player.openInventory(gui);
    }

    private int findMemberIndex(Inventory gui) {
        int memberIndex = -1;

        for(int i = 10; i < gui.getSize(); i++) {
            if(i == 17 || i == 18 || i > 25) continue;
            if(gui.getItem(i).getType() != Material.BLACK_STAINED_GLASS_PANE) continue;
            memberIndex = i;
            break;
        }

        return memberIndex;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        if(!event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Island Members")) return;
        event.setCancelled(true);
        try { if(event.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) return; } catch(NullPointerException ex) { return; }
        if(event.getCurrentItem().getItemMeta().getDisplayName().equals("§cYou have no Island Members!")) return;

        if(event.getClick() == ClickType.LEFT) {
            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));

            if(superiorPlayer.getName().equals(player.getName())) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You can't kick yourself from your Island!");
                player.closeInventory();
                return;
            }

            superiorPlayer.getIsland().kickMember(superiorPlayer);

            player.closeInventory();
            Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), (Runnable) player::closeInventory, 2);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§e" + superiorPlayer.getName() + " has been kicked from your Island!");
        } else if(event.getClick() == ClickType.RIGHT) {
            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            superiorPlayer.getIsland().transferIsland(superiorPlayer);

            if(superiorPlayer.getName().equals(player.getName())) {
                clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "You already own this Island!");
                player.closeInventory();
                return;
            }

            player.closeInventory();
            Bukkit.getScheduler().runTaskLater(ClymeSkyblockCore.getInstance(), (Runnable) player::closeInventory, 2);
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§e" + superiorPlayer.getName() + " has been promoted to Island Leader!");
        }
    }
}
