package solutions.misi.clymeskyblockcore.guis;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.savagelabs.skyblockx.core.IPlayer;
import net.savagelabs.skyblockx.core.IPlayerKt;
import net.savagelabs.skyblockx.core.Island;
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

import java.util.ArrayList;
import java.util.List;

public class IslandSettingsGUI implements Listener {

    public void open(Player player) {
        IPlayer iPlayer = IPlayerKt.getIPlayer(player);
        Island island = iPlayer.getIsland();

        //> Only continue when player is owner of the Island
        if(iPlayer.getIsland().getOwnerIPlayer() != iPlayer) {
            player.sendMessage(ClymeSkyblockCore.getInstance().getMessages().getNoPermission());
            return;
        }

        Inventory gui = Bukkit.createInventory(null, 27, ClymeSkyblockCore.getInstance().getMessages().getPrefix() + "§eIsland Settings");
        refresh(gui, island);
        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(!event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getMessages().getPrefix() + "§eIsland Settings")) return;
        event.setCancelled(true);
        try { if(event.getCurrentItem().getItemMeta().getDisplayName().equals(" ")) return; } catch(NullPointerException ex) { return; }

        Island island = IPlayerKt.getIPlayer(player).getIsland();

        switch(event.getCurrentItem().getType()) {
            //> Toggle animal spawning
            case TROPICAL_FISH:
                toggleFlag(island, ClymeSkyblockCore.getInstance().getFlags().getAnimalsSpawningFlag());
                break;
            //> Toggle monster spawning
            case ROTTEN_FLESH:
                toggleFlag(island, ClymeSkyblockCore.getInstance().getFlags().getMonstersSpawningFlag());
                break;
            //> Toggle PvP
            case GOLDEN_SWORD:
                toggleFlag(island, Flags.PVP);
                break;
            //> Toggle Fire
            case FLINT_AND_STEEL:
                toggleFlag(island, Flags.FIRE_SPREAD);
                break;
            //> Toggle Leaf Decay
            case ACACIA_LEAVES:
                toggleFlag(island, Flags.LEAF_DECAY);
                break;
            //> Toggle Block damage
            case TNT:
               toggleFlag(island, Flags.TNT);
                break;
            //> Toggle visitors
            case PLAYER_HEAD:
                if(island.getAllowVisitors()) {
                    island.setAllowVisitors(false);
                    break;
                }

                island.setAllowVisitors(true);
                break;
        }

        refresh(event.getClickedInventory(), island);
    }

    private void toggleFlag(Island island, Flag flag) {
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(island.getMaxLocation().getLocation().getWorld()));

        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(flag) == StateFlag.State.ALLOW) {
            regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).setFlag(flag, StateFlag.State.DENY);
        }

        regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).setFlag(flag, StateFlag.State.ALLOW);
    }

    private void refresh(Inventory gui, Island island) {
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(island.getMaxLocation().getLocation().getWorld()));

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack animalSpawningDisabled = new ItemStack(Material.TROPICAL_FISH);
        ItemMeta animalSpawningDisabledMeta = animalSpawningDisabled.getItemMeta();
        animalSpawningDisabledMeta.setDisplayName("§6Animal Spawning");
        List<String> animalSpawningDisabledLore = new ArrayList<>();
        animalSpawningDisabledLore.add(" ");
        animalSpawningDisabledLore.add("§7Currently §c§lDISABLED§7!");
        animalSpawningDisabledLore.add(" ");
        animalSpawningDisabledLore.add("§7Click here to change your");
        animalSpawningDisabledLore.add("§7Island's Animal Spawning");
        animalSpawningDisabledLore.add(" ");
        animalSpawningDisabledMeta.setLore(animalSpawningDisabledLore);
        animalSpawningDisabled.setItemMeta(animalSpawningDisabledMeta);

        ItemStack animalSpawningEnabled = new ItemStack(Material.TROPICAL_FISH);
        ItemMeta animalSpawningEnabledMeta = animalSpawningEnabled.getItemMeta();
        animalSpawningEnabledMeta.setDisplayName("§6Animal Spawning");
        List<String> animalSpawningEnabledLore = new ArrayList<>();
        animalSpawningEnabledLore.add(" ");
        animalSpawningEnabledLore.add("§7Currently §a§lENABLED§7!");
        animalSpawningEnabledLore.add(" ");
        animalSpawningEnabledLore.add("§7Click here to disable your");
        animalSpawningEnabledLore.add("§7Island's Animal Spawning");
        animalSpawningEnabledLore.add(" ");
        animalSpawningEnabledMeta.setLore(animalSpawningEnabledLore);
        animalSpawningEnabled.setItemMeta(animalSpawningEnabledMeta);

        ItemStack monsterSpawningDisabled = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta monsterSpawningDisabledMeta = monsterSpawningDisabled.getItemMeta();
        monsterSpawningDisabledMeta.setDisplayName("§5Monster Spawning");
        List<String> monsterSpawningDisabledLore = new ArrayList<>();
        monsterSpawningDisabledLore.add(" ");
        monsterSpawningDisabledLore.add("§7Currently §c§lDISABLED§7!");
        monsterSpawningDisabledLore.add(" ");
        monsterSpawningDisabledLore.add("§7Click here to change your");
        monsterSpawningDisabledLore.add("§7Island's Monster Spawning");
        monsterSpawningDisabledLore.add(" ");
        monsterSpawningDisabledMeta.setLore(monsterSpawningDisabledLore);
        monsterSpawningDisabled.setItemMeta(monsterSpawningDisabledMeta);

        ItemStack monsterSpawningEnabled = new ItemStack(Material.ROTTEN_FLESH);
        ItemMeta monsterSpawningEnabledMeta = monsterSpawningDisabled.getItemMeta();
        monsterSpawningEnabledMeta.setDisplayName("§5Monster Spawning");
        List<String> monsterSpawningEnabledLore = new ArrayList<>();
        monsterSpawningEnabledLore.add(" ");
        monsterSpawningEnabledLore.add("§7Currently §a§lENABLED§7!");
        monsterSpawningEnabledLore.add(" ");
        monsterSpawningEnabledLore.add("§7Click here to change your");
        monsterSpawningEnabledLore.add("§7Island's Monster Spawning");
        monsterSpawningEnabledLore.add(" ");
        monsterSpawningEnabledMeta.setLore(monsterSpawningEnabledLore);
        monsterSpawningEnabled.setItemMeta(monsterSpawningEnabledMeta);

        ItemStack pvpDisabled = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta pvpDisabledMeta = pvpDisabled.getItemMeta();
        pvpDisabledMeta.setDisplayName("§ePvP");
        List<String> pvpDisabledLore = new ArrayList<>();
        pvpDisabledLore.add(" ");
        pvpDisabledLore.add("§7Currently §c§lDISABLED§7!");
        pvpDisabledLore.add(" ");
        pvpDisabledLore.add("§7Click here to enable");
        pvpDisabledLore.add("§7PvP on your Island!");
        pvpDisabledLore.add(" ");
        pvpDisabledMeta.setLore(pvpDisabledLore);
        pvpDisabled.setItemMeta(pvpDisabledMeta);

        ItemStack pvpEnabled = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta pvpEnabledMeta = pvpEnabled.getItemMeta();
        pvpEnabledMeta.setDisplayName("§ePvP");
        List<String> pvpEnabledLore = new ArrayList<>();
        pvpEnabledLore.add(" ");
        pvpEnabledLore.add("§7Currently §a§lENABLED§7!");
        pvpEnabledLore.add(" ");
        pvpEnabledLore.add("§7Click here to disable");
        pvpEnabledLore.add("§7PvP on your Island!");
        pvpEnabledLore.add(" ");
        pvpEnabledMeta.setLore(pvpEnabledLore);
        pvpEnabled.setItemMeta(pvpEnabledMeta);

        ItemStack fireDisabled = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta fireDisabledMeta = fireDisabled.getItemMeta();
        fireDisabledMeta.setDisplayName("§cFire");
        List<String> fireDisabledLore = new ArrayList<>();
        fireDisabledLore.add(" ");
        fireDisabledLore.add("§7Currently §c§lDISABLED§7!");
        fireDisabledLore.add(" ");
        fireDisabledLore.add("§7Click here to enable");
        fireDisabledLore.add("§7Fire on your Island!");
        fireDisabledLore.add(" ");
        fireDisabledMeta.setLore(fireDisabledLore);
        fireDisabled.setItemMeta(fireDisabledMeta);

        ItemStack fireEnabled = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta fireEnabledMeta = fireEnabled.getItemMeta();
        fireEnabledMeta.setDisplayName("§cFire");
        List<String> fireEnabledLore = new ArrayList<>();
        fireEnabledLore.add(" ");
        fireEnabledLore.add("§7Currently §a§lENABLED§7!");
        fireEnabledLore.add(" ");
        fireEnabledLore.add("§7Click here to disable");
        fireEnabledLore.add("§7Fire on your Island!");
        fireEnabledLore.add(" ");
        fireEnabledMeta.setLore(fireEnabledLore);
        fireEnabled.setItemMeta(fireEnabledMeta);

        ItemStack leafDecayDisabled = new ItemStack(Material.ACACIA_LEAVES);
        ItemMeta leafDecayDisabledMeta = leafDecayDisabled.getItemMeta();
        leafDecayDisabledMeta.setDisplayName("§2Leaf decay");
        List<String> leafDecayDisabledLore = new ArrayList<>();
        leafDecayDisabledLore.add(" ");
        leafDecayDisabledLore.add("§7Currently §c§lDISABLED§7!");
        leafDecayDisabledLore.add(" ");
        leafDecayDisabledLore.add("§7Click here to enable");
        leafDecayDisabledLore.add("§7Leaf decay on your Island!");
        leafDecayDisabledLore.add(" ");
        leafDecayDisabledMeta.setLore(leafDecayDisabledLore);
        leafDecayDisabled.setItemMeta(leafDecayDisabledMeta);

        ItemStack leafDecayEnabled = new ItemStack(Material.ACACIA_LEAVES);
        ItemMeta leafDecayEnabledMeta = leafDecayEnabled.getItemMeta();
        leafDecayEnabledMeta.setDisplayName("§2Leaf decay");
        List<String> leafDecayEnabledLore = new ArrayList<>();
        leafDecayEnabledLore.add(" ");
        leafDecayEnabledLore.add("§7Currently §a§lENABLED§7!");
        leafDecayEnabledLore.add(" ");
        leafDecayEnabledLore.add("§7Click here to disable");
        leafDecayEnabledLore.add("§7Leaf decay on your Island!");
        leafDecayEnabledLore.add(" ");
        leafDecayEnabledMeta.setLore(leafDecayEnabledLore);
        leafDecayEnabled.setItemMeta(leafDecayEnabledMeta);

        ItemStack blockDamageDisabled = new ItemStack(Material.TNT);
        ItemMeta blockDamageDisabledMeta = blockDamageDisabled.getItemMeta();
        blockDamageDisabledMeta.setDisplayName("§4Block Damage");
        List<String> blockDamageDisabledLore = new ArrayList<>();
        blockDamageDisabledLore.add(" ");
        blockDamageDisabledLore.add("§7Currently §c§lDISABLED§7!");
        blockDamageDisabledLore.add(" ");
        blockDamageDisabledLore.add("§7Click here to change your");
        blockDamageDisabledLore.add("§7Island's block damage!");
        blockDamageDisabledLore.add(" ");
        blockDamageDisabledMeta.setLore(blockDamageDisabledLore);
        blockDamageDisabled.setItemMeta(blockDamageDisabledMeta);

        ItemStack blockDamageEnabled = new ItemStack(Material.TNT);
        ItemMeta blockDamageEnabledMeta = blockDamageEnabled.getItemMeta();
        blockDamageEnabledMeta.setDisplayName("§4Block Damage");
        List<String> blockDamageEnabledLore = new ArrayList<>();
        blockDamageEnabledLore.add(" ");
        blockDamageEnabledLore.add("§7Currently §a§lENABLED§7!");
        blockDamageEnabledLore.add(" ");
        blockDamageEnabledLore.add("§7Click here to change your");
        blockDamageEnabledLore.add("§7Island's block damage!");
        blockDamageEnabledLore.add(" ");
        blockDamageEnabledMeta.setLore(blockDamageEnabledLore);
        blockDamageEnabled.setItemMeta(blockDamageEnabledMeta);

        ItemStack allowVisitors = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta allowVisitorsMeta = allowVisitors.getItemMeta();
        allowVisitorsMeta.setDisplayName("§bVisitors");
        List<String> allowVisitorsLore = new ArrayList<>();
        allowVisitorsLore.add(" ");
        allowVisitorsLore.add("§7Currently §a§lALLOWING §7Visitors!");
        allowVisitorsLore.add(" ");
        allowVisitorsLore.add("§7Click here to deny");
        allowVisitorsLore.add("§7Visitors on your Island!");
        allowVisitorsLore.add(" ");
        allowVisitorsMeta.setLore(allowVisitorsLore);
        allowVisitors.setItemMeta(allowVisitorsMeta);

        ItemStack denyVisitors = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta denyVisitorsMeta = denyVisitors.getItemMeta();
        denyVisitorsMeta.setDisplayName("§bVisitors");
        List<String> denyVisitorsLore = new ArrayList<>();
        denyVisitorsLore.add(" ");
        denyVisitorsLore.add("§7Currently §c§lNOT ALLOWING §7Visitors!");
        denyVisitorsLore.add(" ");
        denyVisitorsLore.add("§7Click here to allow");
        denyVisitorsLore.add("§7Visitors on your Island!");
        denyVisitorsLore.add(" ");
        denyVisitorsMeta.setLore(denyVisitorsLore);
        denyVisitors.setItemMeta(denyVisitorsMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);

        //> Animal Spawning Setting
        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(ClymeSkyblockCore.getInstance().getFlags().getAnimalsSpawningFlag()) == StateFlag.State.ALLOW) {
            gui.setItem(10, animalSpawningEnabled);
        } else { gui.setItem(10, animalSpawningDisabled); }

        //> Monster Spawning Setting
        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(ClymeSkyblockCore.getInstance().getFlags().getMonstersSpawningFlag()) == StateFlag.State.ALLOW) {
            gui.setItem(11, monsterSpawningEnabled);
        } else { gui.setItem(11, monsterSpawningDisabled); }

        //> PvP Setting
        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(Flags.PVP) == StateFlag.State.ALLOW) {
            gui.setItem(12, pvpEnabled);
        } else { gui.setItem(12, pvpDisabled); }

        //> Fire Setting
        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(Flags.FIRE_SPREAD) == StateFlag.State.ALLOW) {
            gui.setItem(13, fireEnabled);
        } else { gui.setItem(13, fireDisabled); }

        //> Leaf Decay Setting
        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(Flags.LEAF_DECAY) == StateFlag.State.ALLOW) {
            gui.setItem(14, leafDecayEnabled);
        } else { gui.setItem(14, leafDecayDisabled); }

        //> Block Damage Setting
        if(regionManager.getRegion(ClymeSkyblockCore.getInstance().getClymeIslandManager().getIslandId(island)).getFlag(Flags.TNT) == StateFlag.State.ALLOW) {
            gui.setItem(15, blockDamageEnabled);
        } else { gui.setItem(15, blockDamageDisabled); }

        //> Visitors Setting
        if(island.getAllowVisitors()) {
            gui.setItem(16, allowVisitors);
        } else { gui.setItem(16, denyVisitors); }
    }
}