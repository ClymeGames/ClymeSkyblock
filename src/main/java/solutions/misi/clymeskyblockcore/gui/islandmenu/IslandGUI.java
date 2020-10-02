package solutions.misi.clymeskyblockcore.gui.islandmenu;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
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

public class IslandGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 36, ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Island Menu");

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack tpToIsland = new ItemStack(Material.ENDER_PEARL);
        ItemMeta tpToIslandMeta = tpToIsland.getItemMeta();
        tpToIslandMeta.setDisplayName("§6Teleport to your Island");
        List<String> tpToIslandLore = new ArrayList<>();
        tpToIslandLore.add(" ");
        tpToIslandLore.add("§7Click here to teleport to your island.");
        tpToIslandLore.add(" ");
        tpToIslandMeta.setLore(tpToIslandLore);
        tpToIsland.setItemMeta(tpToIslandMeta);

        ItemStack setIslandHome = new ItemStack(Material.RED_BED);
        ItemMeta setIslandHomeMeta = setIslandHome.getItemMeta();
        setIslandHomeMeta.setDisplayName("§4Set Island Home");
        List<String> setIslandHomeLore = new ArrayList<>();
        setIslandHomeLore.add(" ");
        setIslandHomeLore.add("§7Click here to set your Island Home.");
        setIslandHomeLore.add("§7This is the spawn of your Island.");
        setIslandHomeLore.add(" ");
        setIslandHomeMeta.setLore(setIslandHomeLore);
        setIslandHome.setItemMeta(setIslandHomeMeta);

        ItemStack tpToSpawn = new ItemStack(Material.COMPASS);
        ItemMeta tpToSpawnMeta = tpToSpawn.getItemMeta();
        tpToSpawnMeta.setDisplayName("§9Teleport to the Spawn");
        List<String> tpToSpawnLore = new ArrayList<>();
        tpToSpawnLore.add(" ");
        tpToSpawnLore.add("§7Click here to teleport to the Spawn.");
        tpToSpawnLore.add(" ");
        tpToSpawnMeta.setLore(tpToSpawnLore);
        tpToSpawn.setItemMeta(tpToSpawnMeta);

        SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
        Island island = superiorPlayer.getIsland();
        ItemStack islandLevel = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta islandLevelMeta = islandLevel.getItemMeta();
        islandLevelMeta.setDisplayName("§dYour Island Level");
        List<String> islandLevelLore = new ArrayList<>();
        islandLevelLore.add(" ");
        islandLevelLore.add("§7Your Island level determines your current");
        islandLevelLore.add("§7ranking against other Islands.");
        islandLevelLore.add(" ");
        islandLevelLore.add("§7Increase your level by placing spawners on your island.");
        islandLevelLore.add(" ");
        islandLevelLore.add("§7Current level: §e" + island.getIslandLevel());
        islandLevelLore.add(" ");
        islandLevelLore.add("§7Left-Click to refresh your current level.");
        islandLevelLore.add("§7Right-Click to view the spawner values.");
        islandLevelLore.add(" ");
        islandLevelMeta.setLore(islandLevelLore);
        islandLevel.setItemMeta(islandLevelMeta);

        ItemStack top10Islands = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta top10IslandsMeta = top10Islands.getItemMeta();
        top10IslandsMeta.setDisplayName("§bTop 10 Islands");
        List<String> top10IslandsLore = new ArrayList<>();
        top10IslandsLore.add(" ");
        top10IslandsLore.add("§7Click here to view the top 10 best");
        top10IslandsLore.add("§7Islands ranked by their level.");
        top10IslandsLore.add(" ");
        top10IslandsMeta.setLore(top10IslandsLore);
        top10Islands.setItemMeta(top10IslandsMeta);

        ItemStack islandSettings = new ItemStack(Material.BONE_MEAL);
        ItemMeta islandSettingsMeta = islandSettings.getItemMeta();
        islandSettingsMeta.setDisplayName("§fIsland Settings");
        List<String> islandSettingsLore = new ArrayList<>();
        islandSettingsLore.add(" ");
        islandSettingsLore.add("§7Click here to edit your Island Settings.");
        islandSettingsLore.add(" ");
        islandSettingsMeta.setLore(islandSettingsLore);
        islandSettings.setItemMeta(islandSettingsMeta);

        ItemStack islandTeam = new ItemStack(Material.OAK_SIGN);
        ItemMeta islandTeamMeta = islandTeam.getItemMeta();
        islandTeamMeta.setDisplayName("§cIsland Team");
        List<String> islandTeamLore = new ArrayList<>();
        islandTeamLore.add(" ");
        islandTeamLore.add("§7Click here to manage your Island Members.");
        islandTeamLore.add(" ");
        islandTeamMeta.setLore(islandTeamLore);
        islandTeam.setItemMeta(islandTeamMeta);

        ItemStack howToPlay = new ItemStack(Material.BOOK);
        ItemMeta howToPlayMeta = howToPlay.getItemMeta();
        howToPlayMeta.setDisplayName("§aHow to play SkyBlock");
        List<String> howToPlayLore = new ArrayList<>();
        howToPlayLore.add(" ");
        howToPlayLore.add("§7Click here to open an instruction");
        howToPlayLore.add("§7on how to play SkyBlock.");
        howToPlayLore.add(" ");
        howToPlayMeta.setLore(howToPlayLore);
        howToPlay.setItemMeta(howToPlayMeta);

        ItemStack islandBorder = new ItemStack(Material.BARRIER);
        ItemMeta islandBorderMeta = islandBorder.getItemMeta();
        islandBorderMeta.setDisplayName("§cIsland Border");
        List<String> islandBorderLore = new ArrayList<>();
        islandBorderLore.add(" ");
        islandBorderLore.add("§7Click here to change your");
        islandBorderLore.add("§7Island Border color.");
        islandBorderLore.add(" ");
        islandBorderMeta.setLore(islandBorderLore);
        islandBorder.setItemMeta(islandBorderMeta);

        ItemStack islandGenerator = new ItemStack(Material.COBBLESTONE);
        ItemMeta islandGeneratorMeta = islandGenerator.getItemMeta();
        islandGeneratorMeta.setDisplayName("§3Island Generator");
        List<String> islandGeneratorLore = new ArrayList<>();
        islandGeneratorLore.add(" ");
        islandGeneratorLore.add("§7Click here to view & upgrade");
        islandGeneratorLore.add("§7your Island Generator");
        islandGeneratorLore.add(" ");
        islandGeneratorMeta.setLore(islandGeneratorLore);
        islandGenerator.setItemMeta(islandGeneratorMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);
        gui.setItem(11, tpToIsland);
        gui.setItem(12, tpToSpawn);
        gui.setItem(13, islandLevel);
        gui.setItem(14, top10Islands);
        gui.setItem(15, islandSettings);
        gui.setItem(20, setIslandHome);
        gui.setItem(21, islandTeam);
        gui.setItem(22, islandBorder);
        gui.setItem(23, islandGenerator);
        gui.setItem(24, howToPlay);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);

        try {
            if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + "§0Island Menu")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) player.closeInventory();

                switch(event.getCurrentItem().getItemMeta().getDisplayName()) {
                    case "§6Teleport to your Island":
                        player.performCommand("is go");
                        break;
                    case "§4Set Island Home":
                        player.performCommand("is set-go");
                        break;
                    case "§9Teleport to the Spawn":
                        player.performCommand("spawn");
                        break;
                    case "§dYour Island Level":
                        if(event.getClick() == ClickType.LEFT) {
                            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(player);
                            Island island = superiorPlayer.getIsland();

                            Bukkit.getScheduler().runTaskAsynchronously(ClymeSkyblockCore.getInstance(), () -> island.calcIslandWorth(superiorPlayer));

                            player.closeInventory();
                            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + (ClymeChatColor.SUCCESS() + "Successfully calculated the worth of your island.."));
                        } else if(event.getClick() == ClickType.RIGHT) {
                            ClymeSkyblockCore.getInstance().getSpawnerValuesGUI().open(player);
                        }

                        break;
                    case "§bTop 10 Islands":
                        player.performCommand("is top");
                        break;
                    case "§fIsland Settings":
                        ClymeSkyblockCore.getInstance().getIslandSettingsGUI().open(player);
                        break;
                    case "§cIsland Team":
                        ClymeSkyblockCore.getInstance().getIslandMembersGUI().open(player);
                        break;
                    case "§aHow to play SkyBlock":
                        //> TODO: How to play Skyblock GUI
                        break;
                    case "§cIsland Border":
                        player.performCommand("is border");
                        break;
                    case "§3Island Generator":
                        player.performCommand("is upgrades");
                        break;
                }
            }
        } catch(NullPointerException ex) {}
    }
}
