package solutions.misi.clymeskyblockcore.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClymeMessage {

    private final FileConfiguration MESSAGES_FILE_CFG;
    private final Pattern PATTERN = Pattern.compile("#[a-fA-f0-9]{6}");

    @Getter private final String prefix;
    @Getter private final String commandSpam;
    @Getter private final String noPermission;

    @SneakyThrows
    public ClymeMessage() {
        File messagesFile = new File(ClymeSkyblockCore.getInstance().getDataFolder(), "messages.yml");
        MESSAGES_FILE_CFG = new YamlConfiguration();
        if(!messagesFile.exists()) ClymeSkyblockCore.getInstance().saveResource("messages.yml", false);
        MESSAGES_FILE_CFG.load(messagesFile);

        //> Messages
        prefix = getFormattedMessage("prefix");
        commandSpam = prefix + getFormattedMessage("command-spam");
        noPermission = prefix + getFormattedMessage("no-permission");
    }

    private String getFormattedMessage(String path) {
        return format(MESSAGES_FILE_CFG.getString(path)
                .replace("%success%", ClymeChatColor.SUCCESS())
                .replace("%info%", ClymeChatColor.INFO())
                .replace("%error%", ClymeChatColor.ERROR()));
    }

    public String format(String message) {
        if(Bukkit.getVersion().contains("1.16")) {
            Matcher matcher = PATTERN.matcher(message);
            while(matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, ChatColor.of(color) + "");
                matcher = PATTERN.matcher(message);
            }
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void clearChat(Player player) {
        for(int i = 0; i < 255; i++) player.sendMessage(" ");
    }
}