package solutions.misi.clymeskyblockcore.islands.settings;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

public class IslandSettings {

    public void reset(ProtectedRegion islandRegion) {
        //> Default WorldGuard Flags
        islandRegion.setFlag(Flags.PVP, StateFlag.State.DENY);
        islandRegion.setFlag(Flags.FIRE_SPREAD, StateFlag.State.DENY);
        islandRegion.setFlag(Flags.LEAF_DECAY, StateFlag.State.DENY);
        islandRegion.setFlag(Flags.TNT, StateFlag.State.DENY);
        islandRegion.setFlag(ClymeSkyblockCore.getInstance().getAnimalsSpawningFlag(), StateFlag.State.ALLOW);
        islandRegion.setFlag(ClymeSkyblockCore.getInstance().getMonstersSpawningFlag(), StateFlag.State.ALLOW);
        islandRegion.setFlag(ClymeSkyblockCore.getInstance().getVisitorsFlag(), StateFlag.State.ALLOW);
    }
}
