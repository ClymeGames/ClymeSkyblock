package solutions.misi.clymeskyblockcore.guis;

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

public class SpawnerValuesGUI implements Listener {

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 54, ClymeSkyblockCore.getInstance().getMessages().getPrefix() + "§7Spawner Values");

        ItemStack placeholder = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta placeholderMeta = placeholder.getItemMeta();
        placeholderMeta.setDisplayName(" ");
        placeholder.setItemMeta(placeholderMeta);

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName("§cGo Back");
        back.setItemMeta(backMeta);

        ItemStack sheepSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta sheepSpawnerMeta = sheepSpawner.getItemMeta();
        sheepSpawnerMeta.setDisplayName("§aSheep Spawner");
        List<String> sheepSpawnerLore = new ArrayList<>();
        sheepSpawnerLore.add(" ");
        sheepSpawnerLore.add("§aTier I Spawner");
        sheepSpawnerLore.add("§7Worth: §e$100");
        sheepSpawnerLore.add(" ");
        sheepSpawnerMeta.setLore(sheepSpawnerLore);
        sheepSpawner.setItemMeta(sheepSpawnerMeta);

        ItemStack cowSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta cowSpawnerMeta = cowSpawner.getItemMeta();
        cowSpawnerMeta.setDisplayName("§aCow Spawner");
        List<String> cowSpawnerLore = new ArrayList<>();
        cowSpawnerLore.add(" ");
        cowSpawnerLore.add("§aTier I Spawner");
        cowSpawnerLore.add("§7Worth: §e$100");
        cowSpawnerLore.add(" ");
        cowSpawnerMeta.setLore(cowSpawnerLore);
        cowSpawner.setItemMeta(cowSpawnerMeta);

        ItemStack pigSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta pigSpawnerMeta = pigSpawner.getItemMeta();
        pigSpawnerMeta.setDisplayName("§aPig Spawner");
        List<String> pigSpawnerLore = new ArrayList<>();
        pigSpawnerLore.add(" ");
        pigSpawnerLore.add("§aTier I Spawner");
        pigSpawnerLore.add("§7Worth: §e$100");
        pigSpawnerLore.add(" ");
        pigSpawnerMeta.setLore(pigSpawnerLore);
        pigSpawner.setItemMeta(pigSpawnerMeta);

        ItemStack zombieSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta zombieSpawnerMeta = zombieSpawner.getItemMeta();
        zombieSpawnerMeta.setDisplayName("§bZombie Spawner");
        List<String> zombieSpawnerLore = new ArrayList<>();
        zombieSpawnerLore.add(" ");
        zombieSpawnerLore.add("§bTier II Spawner");
        zombieSpawnerLore.add("§7Worth: §e$200");
        zombieSpawnerLore.add(" ");
        zombieSpawnerMeta.setLore(zombieSpawnerLore);
        zombieSpawner.setItemMeta(zombieSpawnerMeta);

        ItemStack skeletonSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta skeletonSpawnerMeta = skeletonSpawner.getItemMeta();
        skeletonSpawnerMeta.setDisplayName("§bSkeleton Spawner");
        List<String> skeletonSpawnerLore = new ArrayList<>();
        skeletonSpawnerLore.add(" ");
        skeletonSpawnerLore.add("§bTier II Spawner");
        skeletonSpawnerLore.add("§7Worth: §e$200");
        skeletonSpawnerLore.add(" ");
        skeletonSpawnerMeta.setLore(skeletonSpawnerLore);
        skeletonSpawner.setItemMeta(skeletonSpawnerMeta);

        ItemStack pandaSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta pandaSpawnerMeta = pandaSpawner.getItemMeta();
        pandaSpawnerMeta.setDisplayName("§dPanda Spawner");
        List<String> pandaSpawnerLore = new ArrayList<>();
        pandaSpawnerLore.add(" ");
        pandaSpawnerLore.add("§dTier III Spawner");
        pandaSpawnerLore.add("§7Worth: §e$300");
        pandaSpawnerLore.add(" ");
        pandaSpawnerMeta.setLore(pandaSpawnerLore);
        pandaSpawner.setItemMeta(pandaSpawnerMeta);

        ItemStack wolfSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta wolfSpawnerMeta = wolfSpawner.getItemMeta();
        wolfSpawnerMeta.setDisplayName("§dWolf Spawner");
        List<String> wolfSpawnerLore = new ArrayList<>();
        wolfSpawnerLore.add(" ");
        wolfSpawnerLore.add("§dTier III Spawner");
        wolfSpawnerLore.add("§7Worth: §e$300");
        wolfSpawnerLore.add(" ");
        wolfSpawnerMeta.setLore(wolfSpawnerLore);
        wolfSpawner.setItemMeta(wolfSpawnerMeta);

        ItemStack spiderSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta spiderSpawnerMeta = spiderSpawner.getItemMeta();
        spiderSpawnerMeta.setDisplayName("§dSpider Spawner");
        List<String> spiderSpawnerLore = new ArrayList<>();
        spiderSpawnerLore.add(" ");
        spiderSpawnerLore.add("§dTier III Spawner");
        spiderSpawnerLore.add("§7Worth: §e$300");
        spiderSpawnerLore.add(" ");
        wolfSpawnerMeta.setLore(spiderSpawnerLore);
        spiderSpawner.setItemMeta(spiderSpawnerMeta);

        ItemStack caveSpiderSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta caveSpiderSpawnerMeta = caveSpiderSpawner.getItemMeta();
        caveSpiderSpawnerMeta.setDisplayName("§cCave Spider Spawner");
        List<String> caveSpiderSpawnerLore = new ArrayList<>();
        caveSpiderSpawnerLore.add(" ");
        caveSpiderSpawnerLore.add("§cTier IV Spawner");
        caveSpiderSpawnerLore.add("§7Worth: §e$400");
        caveSpiderSpawnerLore.add(" ");
        caveSpiderSpawnerMeta.setLore(caveSpiderSpawnerLore);
        caveSpiderSpawner.setItemMeta(caveSpiderSpawnerMeta);

        ItemStack creeperSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta creeperSpawnerMeta = creeperSpawner.getItemMeta();
        creeperSpawnerMeta.setDisplayName("§cCreeper Spawner");
        List<String> creeperSpawnerLore = new ArrayList<>();
        creeperSpawnerLore.add(" ");
        creeperSpawnerLore.add("§cTier IV Spawner");
        creeperSpawnerLore.add("§7Worth: §e$400");
        creeperSpawnerLore.add(" ");
        creeperSpawnerMeta.setLore(creeperSpawnerLore);
        creeperSpawner.setItemMeta(creeperSpawnerMeta);

        ItemStack witchSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta witchSpawnerMeta = witchSpawner.getItemMeta();
        witchSpawnerMeta.setDisplayName("§3Witch Spawner");
        List<String> witchSpawnerLore = new ArrayList<>();
        witchSpawnerLore.add(" ");
        witchSpawnerLore.add("§3Tier V Spawner");
        witchSpawnerLore.add("§7Worth: §e$600");
        witchSpawnerLore.add(" ");
        witchSpawnerMeta.setLore(witchSpawnerLore);
        witchSpawner.setItemMeta(witchSpawnerMeta);

        ItemStack zombieVillagerSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta zombieVillagerSpawnerMeta = zombieVillagerSpawner.getItemMeta();
        zombieVillagerSpawnerMeta.setDisplayName("§3Zombie Villager Spawner");
        List<String> zombieVillagerSpawnerLore = new ArrayList<>();
        zombieVillagerSpawnerLore.add(" ");
        zombieVillagerSpawnerLore.add("§3Tier V Spawner");
        zombieVillagerSpawnerLore.add("§7Worth: §e$600");
        zombieVillagerSpawnerLore.add(" ");
        zombieVillagerSpawnerMeta.setLore(zombieVillagerSpawnerLore);
        zombieVillagerSpawner.setItemMeta(zombieVillagerSpawnerMeta);

        ItemStack mushroomCowSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta mushroomCowSpawnerMeta = mushroomCowSpawner.getItemMeta();
        mushroomCowSpawnerMeta.setDisplayName("§3Mushroom Cow Spawner");
        List<String> mushroomCowSpawnerLore = new ArrayList<>();
        mushroomCowSpawnerLore.add(" ");
        mushroomCowSpawnerLore.add("§3Tier V Spawner");
        mushroomCowSpawnerLore.add("§7Worth: §e$600");
        mushroomCowSpawnerLore.add(" ");
        mushroomCowSpawnerMeta.setLore(mushroomCowSpawnerLore);
        mushroomCowSpawner.setItemMeta(mushroomCowSpawnerMeta);

        ItemStack zombiePigmanSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta zombiePigmanSpawnerMeta = zombiePigmanSpawner.getItemMeta();
        zombiePigmanSpawnerMeta.setDisplayName("§5Zombie Pigman Spawner");
        List<String> zombiePigmanSpawnerLore = new ArrayList<>();
        zombiePigmanSpawnerLore.add(" ");
        zombiePigmanSpawnerLore.add("§3Tier VI Spawner");
        zombiePigmanSpawnerLore.add("§7Worth: §e$700");
        zombiePigmanSpawnerLore.add(" ");
        zombiePigmanSpawnerMeta.setLore(zombiePigmanSpawnerLore);
        zombiePigmanSpawner.setItemMeta(zombiePigmanSpawnerMeta);

        ItemStack ironGolemSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta ironGolemSpawnerMeta = ironGolemSpawner.getItemMeta();
        ironGolemSpawnerMeta.setDisplayName("§f§lIron Golem Spawner");
        List<String> ironGolemSpawnerLore = new ArrayList<>();
        ironGolemSpawnerLore.add(" ");
        ironGolemSpawnerLore.add("§3Tier VII Spawner");
        ironGolemSpawnerLore.add("§7Worth: §e$1,000");
        ironGolemSpawnerLore.add(" ");
        ironGolemSpawnerMeta.setLore(ironGolemSpawnerLore);
        ironGolemSpawner.setItemMeta(ironGolemSpawnerMeta);

        ItemStack witherSkeletonSpawner = new ItemStack(Material.SPAWNER);
        ItemMeta witherSkeletonSpawnerMeta = witherSkeletonSpawner.getItemMeta();
        witherSkeletonSpawnerMeta.setDisplayName("§d§lWither Skeleton Spawner");
        List<String> witherSkeletonSpawnerLore = new ArrayList<>();
        witherSkeletonSpawnerLore.add(" ");
        witherSkeletonSpawnerLore.add("§3Tier VIII Spawner");
        witherSkeletonSpawnerLore.add("§7Worth: §e$1,500");
        witherSkeletonSpawnerLore.add(" ");
        witherSkeletonSpawnerMeta.setLore(witherSkeletonSpawnerLore);
        witherSkeletonSpawner.setItemMeta(witherSkeletonSpawnerMeta);

        for(int i = 0; i < gui.getSize(); i++) gui.setItem(i, placeholder);
        gui.setItem(10, sheepSpawner);
        gui.setItem(11, cowSpawner);
        gui.setItem(12, pigSpawner);
        gui.setItem(13, zombieSpawner);
        gui.setItem(14, skeletonSpawner);
        gui.setItem(15, pandaSpawner);
        gui.setItem(16, wolfSpawner);
        gui.setItem(19, spiderSpawner);
        gui.setItem(20, caveSpiderSpawner);
        gui.setItem(21, creeperSpawner);
        gui.setItem(22, witchSpawner);
        gui.setItem(23, zombieVillagerSpawner);
        gui.setItem(24, mushroomCowSpawner);
        gui.setItem(25, zombiePigmanSpawner);
        gui.setItem(29, ironGolemSpawner);
        gui.setItem(33, witherSkeletonSpawner);
        gui.setItem(45, back);

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        try {
            if(event.getView().getTitle().equals(ClymeSkyblockCore.getInstance().getMessages().getPrefix() + "§7Spawner Values")) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType() == Material.BARRIER) {
                    player.closeInventory();
                    ClymeSkyblockCore.getInstance().getIslandGUI().open(player);
                }
            }
        } catch(NullPointerException ex) { }
    }
}
