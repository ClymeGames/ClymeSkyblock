package solutions.misi.clymeskyblockcore.utils;

import org.bukkit.OfflinePlayer;

import java.util.*;

public class SortingUtils {

    public static HashMap<OfflinePlayer, Long> sortByValue(HashMap<OfflinePlayer, Long> hm) {
        List<Map.Entry<OfflinePlayer, Long>> list = new LinkedList<>(hm.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        HashMap<OfflinePlayer, Long> temp = new LinkedHashMap<>();
        for(Map.Entry<OfflinePlayer, Long> aa : list) temp.put(aa.getKey(), aa.getValue());
        return temp;
    }
}