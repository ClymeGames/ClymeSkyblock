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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClymeMessage {

    private final FileConfiguration MESSAGES_FILE_CFG;
    private final Pattern PATTERN = Pattern.compile("#[a-fA-f0-9]{6}");

    @Getter private final String rawPrefix;
    @Getter private final String prefix;

    @Getter private final String commandSpam;
    @Getter private final String noPermission;
    @Getter private final String noCommand;

    private int currentAnnouncement;

    @SneakyThrows
    public ClymeMessage() {
        File messagesFile = new File(ClymeSkyblockCore.getInstance().getDataFolder(), "messages.yml");
        MESSAGES_FILE_CFG = new YamlConfiguration();
        if(!messagesFile.exists()) ClymeSkyblockCore.getInstance().saveResource("messages.yml", false);
        MESSAGES_FILE_CFG.load(messagesFile);

        //> Messages
        rawPrefix = getFormattedMessage("prefix");
        prefix = rawPrefix + " §f➢ ";
        commandSpam = prefix + getFormattedMessage("command-spam");
        noPermission = prefix + getFormattedMessage("no-permission");
        noCommand = prefix + getFormattedMessage("no-command");

        currentAnnouncement = 0;
    }

    private String getFormattedMessage(String path) {
        return format(MESSAGES_FILE_CFG.getString(path)
                .replace("%success%", ClymeChatColor.SUCCESS())
                .replace("%info%", ClymeChatColor.INFO())
                .replace("%error%", ClymeChatColor.ERROR()));
    }

    public String format(String message) {
       Matcher matcher = PATTERN.matcher(message);
        while(matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
            matcher = PATTERN.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void clearChat(Player player) {
        for(int i = 0; i < 255; i++) player.sendMessage(" ");
    }

    public void broadcastMessage(boolean addPrefix, String message) {
        String prefix = "";
        if(addPrefix) prefix = getPrefix();

        Bukkit.broadcastMessage(format(prefix + ClymeChatColor.INFO() + message));
    }

    public void startAnnouncements() {
        List<String> announcements = ClymeSkyblockCore.getInstance().getConfig().getStringList("announcements.content");
        final int timer = ClymeSkyblockCore.getInstance().getConfig().getInt("announcements.timer");

        Bukkit.getScheduler().runTaskTimer(ClymeSkyblockCore.getInstance(), () -> {
            if(currentAnnouncement == announcements.size()) currentAnnouncement = 0;
            broadcastMessage(true, announcements.get(currentAnnouncement));
            currentAnnouncement++;
        }, 20L*60, 20L*60*timer);
    }

    public String convertLegacyToAdventure(String text) {
        return text.replaceAll("#", "&#");
    }
}