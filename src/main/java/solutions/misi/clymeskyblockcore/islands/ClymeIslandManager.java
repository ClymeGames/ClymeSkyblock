package solutions.misi.clymeskyblockcore.islands;

import com.bgsoftware.superiorskyblock.api.island.Island;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.util.HashMap;
import java.util.Map;

public class ClymeIslandManager {

    public String getIslandId(Island island) {
        return "CSB/" + island.getCenter(World.Environment.NORMAL).getBlockX() + "/" + island.getCenter(World.Environment.NORMAL).getBlockY() + "/" + island.getCenter(World.Environment.NORMAL).getBlockZ();
    }

    //> Used after creation of new Islands or upgrading the Size of an Island
    public void redefineIslandRegion(Island island) {
        BlockVector3 islandMinPosition = BlockVector3.at(island.getMinimumProtected().getBlockX(),0, island.getMinimumProtected().getBlockZ());
        BlockVector3 islandMaxPosition = BlockVector3.at(island.getMaximumProtected().getBlockX(), 256, island.getMaximumProtected().getBlockZ());
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(island.getMaximum().getWorld()));

        if(regionManager == null) {
            Bukkit.getConsoleSender().sendMessage("[ClymeGames] §cIsland at (" + island.getCenter(World.Environment.NORMAL).getBlockX() + ", " + island.getCenter(World.Environment.NORMAL).getBlockY() + ", " + island.getCenter(World.Environment.NORMAL).getBlockZ() + ") could not redefine Region.");
            return;
        }

        ApplicableRegionSet regions = regionManager.getApplicableRegions(islandMinPosition);

        if(regions.size() > 1) {
            Bukkit.getConsoleSender().sendMessage("[ClymeGames] §cIsland at (" + island.getCenter(World.Environment.NORMAL).getBlockX() + ", " + island.getCenter(World.Environment.NORMAL).getBlockY() + ", " + island.getCenter(World.Environment.NORMAL).getBlockZ() + ") could not redefine Region.");
            return;
        }

        //> Delete existing island region & save Island Settings
        Map<Flag, StateFlag.State> oldFlags = deleteIslandRegion(island);

        //> Create new island region
        ProtectedRegion islandRegion = new ProtectedCuboidRegion(getIslandId(island), islandMinPosition, islandMaxPosition);
        DefaultDomain ownerDomain = new DefaultDomain();
        ownerDomain.addPlayer(island.getOwner().getUniqueId());
        islandRegion.setOwners(ownerDomain);
        regionManager.addRegion(islandRegion);
        Bukkit.getConsoleSender().sendMessage("[ClymeGames] §aIsland Region at (" + island.getCenter(World.Environment.NORMAL).getBlockX() + ", " + island.getCenter(World.Environment.NORMAL).getBlockY() + ", " + island.getCenter(World.Environment.NORMAL).getBlockZ() + ") has been created.");

        //> Set default flags
        if(oldFlags.isEmpty()) {
            ClymeSkyblockCore.getInstance().getIslandSettings().reset(islandRegion);
            return;
        }

        //> Set old flags
        for(Map.Entry<Flag, StateFlag.State> flag : oldFlags.entrySet()) {
            islandRegion.setFlag(flag.getKey(), flag.getValue());
        }
    }

    //> Deletes the entire region of an island
    public Map<Flag, StateFlag.State> deleteIslandRegion(Island island) {
        BlockVector3 islandMinPosition = BlockVector3.at(island.getMinimumProtected().getBlockX(), 0, island.getMinimumProtected().getBlockZ());
        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(island.getMaximumProtected().getWorld()));

        if(regionManager == null) {
            Bukkit.getConsoleSender().sendMessage("[ClymeGames] §cIsland at (" + island.getCenter(World.Environment.NORMAL).getBlockX() + ", " + island.getCenter(World.Environment.NORMAL).getBlockY() + ", " + island.getCenter(World.Environment.NORMAL).getBlockZ() + ") could not delete Region.");
            return null;
        }

        ApplicableRegionSet regions = regionManager.getApplicableRegions(islandMinPosition);
        Map<Flag, StateFlag.State> oldFlags = new HashMap<>();

        if(regions.size() > 1) {
            Bukkit.getConsoleSender().sendMessage("[ClymeGames] §cIsland at (" + island.getCenter(World.Environment.NORMAL).getBlockX() + ", " + island.getCenter(World.Environment.NORMAL).getBlockY() + ", " + island.getCenter(World.Environment.NORMAL).getBlockZ() + ") could not delete Region.");
            return null;
        }

        //> Delete existing island region
        if(regions.size() == 1) {
            ProtectedRegion islandRegion = regions.getRegions().iterator().next();

            oldFlags.put(Flags.PVP, islandRegion.getFlag(Flags.PVP));
            oldFlags.put(Flags.FIRE_SPREAD, islandRegion.getFlag(Flags.FIRE_SPREAD));
            oldFlags.put(Flags.LEAF_DECAY, islandRegion.getFlag(Flags.LEAF_DECAY));
            oldFlags.put(Flags.TNT, islandRegion.getFlag(Flags.TNT));
            oldFlags.put(ClymeSkyblockCore.getInstance().getFlags().getAnimalsSpawningFlag(), islandRegion.getFlag(ClymeSkyblockCore.getInstance().getFlags().getAnimalsSpawningFlag()));
            oldFlags.put(ClymeSkyblockCore.getInstance().getFlags().getMonstersSpawningFlag(), islandRegion.getFlag(ClymeSkyblockCore.getInstance().getFlags().getMonstersSpawningFlag()));

            regionManager.removeRegion(islandRegion.getId());
            Bukkit.getConsoleSender().sendMessage("[ClymeGames] §aIsland at (" + island.getCenter(World.Environment.NORMAL).getBlockX() + ", " + island.getCenter(World.Environment.NORMAL).getBlockY() + ", " + island.getCenter(World.Environment.NORMAL).getBlockZ() + ") has been deleted.");
        }

        return oldFlags;
    }
}