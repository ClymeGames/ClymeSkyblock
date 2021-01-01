package solutions.misi.clymeskyblockcore.utils;

import org.bukkit.OfflinePlayer;

import java.util.*;

public class SortingUtils {

    public static HashMap<OfflinePlayer, Integer> sortByValue(HashMap<OfflinePlayer, Integer> hm) {
        List<Map.Entry<OfflinePlayer, Integer>> list = new LinkedList<>(hm.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);
        HashMap<OfflinePlayer, Integer> temp = new LinkedHashMap<>();
        for(Map.Entry<OfflinePlayer, Integer> aa : list) temp.put(aa.getKey(), aa.getValue());
        return temp;
    }
}