package solutions.misi.clymeskyblockcore.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClymeChat {

    private static final Pattern pattern = Pattern.compile("#[a-fA-f0-9]{6}");

    public static String format(String message) {
        if(Bukkit.getVersion().contains("1.16")) {
            Matcher matcher = pattern.matcher(message);
            while(matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String SUCCESS() {
        return "#17ff79";
    }

    public static String INFO() {
        return "#0984e3";
    }

    public static String ERROR() {
        return "#d63031";
    }
}
