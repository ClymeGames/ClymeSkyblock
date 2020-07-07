package solutions.misi.clymeskyblockcore.islands.settings.flags;

import com.sk89q.worldguard.protection.flags.StateFlag;
import lombok.Getter;
import lombok.Setter;

public class Flags {

    @Getter @Setter private StateFlag animalsSpawningFlag;
    @Getter @Setter private StateFlag monstersSpawningFlag;

}
